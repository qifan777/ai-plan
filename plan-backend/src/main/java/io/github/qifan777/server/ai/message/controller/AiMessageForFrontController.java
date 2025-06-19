package io.github.qifan777.server.ai.message.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.BooleanUtil;
import io.github.qifan777.server.ai.message.entity.AiMessage;
import io.github.qifan777.server.ai.message.entity.AiMessageChatMemory;
import io.github.qifan777.server.ai.message.entity.dto.AiMessageInput;
import io.github.qifan777.server.ai.message.entity.dto.AiMessageSpec;
import io.github.qifan777.server.ai.message.repository.AiMessageRepository;
import io.github.qifan777.server.infrastructure.model.QueryRequest;
import io.github.qifan777.server.plan.listing.repository.ListingRepository;
import io.github.qifan777.server.plan.task.entity.Task;
import io.github.qifan777.server.plan.task.repository.TaskRepository;
import io.qifan.infrastructure.common.exception.BusinessException;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.babyfish.jimmer.client.FetchBy;
import org.babyfish.jimmer.client.meta.DefaultFetcherOwner;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.content.Media;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.github.qifan777.server.infrastructure.utils.JsonUtils.toJson;

@Slf4j
@RestController
@RequestMapping("front/ai-message")
@AllArgsConstructor
@DefaultFetcherOwner(AiMessageRepository.class)
@Transactional
public class AiMessageForFrontController {
    private final AiMessageRepository aiMessageRepository;
    private final ChatModel chatModel;
    private final ChatMemory chatMemory;
    private final TaskRepository taskRepository;
    private final ListingRepository listingRepository;

    @GetMapping("{id}")
    public @FetchBy(value = "COMPLEX_FETCHER_FOR_FRONT") AiMessage findById(@PathVariable String id) {
        return aiMessageRepository.findById(id, AiMessageRepository.COMPLEX_FETCHER_FOR_FRONT).orElseThrow(() -> new BusinessException("数据不存在"));
    }

    @PostMapping("query")
    public Page<@FetchBy(value = "COMPLEX_FETCHER_FOR_FRONT") AiMessage> query(@RequestBody QueryRequest<AiMessageSpec> queryRequest) {
        queryRequest.getQuery().setCreatorId(StpUtil.getLoginIdAsString());
        return aiMessageRepository.findPage(queryRequest, AiMessageRepository.COMPLEX_FETCHER_FOR_FRONT);
    }

    public String insert(AiMessageInput input) {
        return aiMessageRepository.insert(input.toEntity()).id();
    }

    public String update(AiMessageInput input) {
        AiMessage aiMessage = aiMessageRepository.findById(input.getId(), AiMessageRepository.COMPLEX_FETCHER_FOR_FRONT).orElseThrow(() -> new BusinessException("数据不存在"));
        if (!aiMessage.creator().id().equals(StpUtil.getLoginIdAsString())) {
            throw new BusinessException("只能修改自己的数据");
        }
        return aiMessageRepository.save(input.toEntity()).id();
    }

    @PostMapping("save")
    public String save(@RequestBody @Validated AiMessageInput input) {
        input.setId(null);
        return StringUtils.hasText(input.getId()) ? update(input) : insert(input);
    }


    @DeleteMapping
    public Boolean delete(@RequestBody List<String> ids) {
        aiMessageRepository.findByIds(ids, AiMessageRepository.COMPLEX_FETCHER_FOR_FRONT).forEach(aiMessage -> {
            if (!aiMessage.creator().id().equals(StpUtil.getLoginIdAsString())) {
                throw new BusinessException("只能删除自己的数据");
            }
        });
        aiMessageRepository.deleteAllById(ids);
        return true;
    }

    @DeleteMapping("history/{sessionId}")
    public void deleteHistory(@PathVariable String sessionId) {
        chatMemory.clear(sessionId);
    }


    @SneakyThrows
    @PostMapping(value = "chat", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<String>> chat(@RequestBody AiMessageInput input) {
        log.info("chat input: {}", toJson(input));
        try {
            return ChatClient.create(chatModel).prompt()
                    .system(this::useTask)
                    .user(promptUserSpec -> {
                        toPrompt(promptUserSpec, input);
                    })
                    .advisors(advisorSpec -> {
                        // 使用历史消息
                        useChatHistory(advisorSpec, input.getSessionId());
                    })
                    .stream()
                    .chatResponse()
                    .map(chatResponse -> ServerSentEvent.builder(toJson(chatResponse))
                            // 和前端监听的事件相对应
                            .event("message")
                            .build());

        } catch (Exception e) {
            return Flux.just(ServerSentEvent.builder(e.getMessage()).event("error").build());
        }
    }

    public void toPrompt(ChatClient.PromptUserSpec promptUserSpec, AiMessageInput input) {
        // AiMessageInput转成Message
        Message message = AiMessageChatMemory.toSpringAiMessage(input.toEntity());
        if (message instanceof UserMessage userMessage &&
                !CollectionUtils.isEmpty(userMessage.getMedia())) {
            // 用户发送的图片/语言
            Media[] medias = new Media[userMessage.getMedia().size()];
            promptUserSpec.media(userMessage.getMedia().toArray(medias));
        }
        // 用户发送的文本
        promptUserSpec.text(message.getText());
    }

    public void useChatHistory(ChatClient.AdvisorSpec advisorSpec, String sessionId) {
        // 1. 如果需要存储会话和消息到数据库，自己可以实现ChatMemory接口，这里使用自己实现的AiMessageChatMemory，数据库存储。
        // 2. 传入会话id，MessageChatMemoryAdvisor会根据会话id去查找消息。
        // 3. 只需要携带最近10条消息
        // MessageChatMemoryAdvisor会在消息发送给大模型之前，从ChatMemory中获取会话的历史消息，然后一起发送给大模型。
        advisorSpec.advisors(MessageChatMemoryAdvisor.builder(chatMemory).conversationId(sessionId).build());
    }

    public void useTask(ChatClient.PromptSystemSpec systemSpec) {
        List<Task> tasks = taskRepository.findUserTasks(StpUtil.getLoginIdAsString());
        String taskInfos = "以下内容是用户的任务完成情况：\n" + tasks.stream()
                .map(task -> {
                    return PromptTemplate.builder()
                            .template("""
                                    任务名称：{name}
                                    截至时间：{deadline}
                                    是否完成：{checked}
                                    任务步骤：
                                    {steps}
                                    """)
                            .variables(Map.of("name", task.name(),
                                    "deadline", task.finishTime(),
                                    "checked", BooleanUtil.isTrue(task.checked()) ? "是" : "否",
                                    "steps", task.steps().stream().map(taskStep -> {
                                        return PromptTemplate.builder()
                                                .template("""
                                                        步骤内容：{name}
                                                        是否完成：{checked}
                                                        """)
                                                .variables(Map.of("name", taskStep.name(), "checked", BooleanUtil.isTrue(taskStep.checked()) ? "是" : "否"))
                                                .build()
                                                .create()
                                                .getContents();
                                    }).collect(Collectors.joining("\n"))))
                            .build()
                            .create()
                            .getContents();
                })
                .collect(Collectors.joining("\n"));
        systemSpec.text(taskInfos);
    }

}
