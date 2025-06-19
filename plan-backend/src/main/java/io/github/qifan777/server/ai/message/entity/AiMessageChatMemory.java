package io.github.qifan777.server.ai.message.entity;

import cn.hutool.core.collection.CollectionUtil;
import io.github.qifan777.server.ai.message.repository.AiMessageRepository;
import io.qifan.infrastructure.common.exception.BusinessException;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.babyfish.jimmer.ImmutableObjects;
import org.babyfish.jimmer.sql.EnableDtoGeneration;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.*;
import org.springframework.ai.content.Media;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@AllArgsConstructor
@EnableDtoGeneration
public class AiMessageChatMemory implements ChatMemory {
    private final AiMessageRepository messageRepository;

    @Override
    public void add(String conversationId, List<Message> messages) {
    }

    /**
     * 查询会话内的消息最新n条历史记录
     *
     * @param conversationId 会话id
     * @param lastN          最近n条
     * @return org.springframework.ai.chat.messages.Message格式的消息
     */
    @Override
    public List<Message> get(String conversationId) {
        return messageRepository
                // 查询会话内的最新n条消息
                .findBySessionId(conversationId, 10)
                .stream()
                .sorted(Comparator.comparing(AiMessage::createdTime))
                // 转成Message对象
                .map(AiMessageChatMemory::toSpringAiMessage)
                .toList();
    }

    /**
     * 清除会话内的消息
     *
     * @param conversationId 会话id
     */
    @Override
    public void clear(String conversationId) {
        messageRepository.deleteBySessionId(conversationId);
    }

    public static AiMessage toAiMessage(Message message, String sessionId) {
        return AiMessageDraft.$.produce(draft -> {
            draft.setSessionId(sessionId)
                    .setTextContent(message.getText())
                    .setType(message.getMessageType())
                    .setMedias(new ArrayList<>());
            if (message instanceof UserMessage userMessage &&
                    !CollectionUtil.isEmpty(userMessage.getMedia())) {
                List<AiMessage.Media> mediaList = ((UserMessage) message)
                        .getMedia()
                        .stream()
                        .map(media -> new AiMessage.Media()
                                .setType(media.getMimeType().getType())
                                .setData(media.getData().toString()))
                        .toList();
                draft.setMedias(mediaList);
            }
        });
    }

    public static Message toSpringAiMessage(AiMessage aiMessage) {
        List<Media> mediaList = new ArrayList<>();
        if (ImmutableObjects.isLoaded(aiMessage, AiMessageProps.MEDIAS) && !CollectionUtil.isEmpty(aiMessage.medias())) {
            mediaList = aiMessage.medias().stream().map(AiMessageChatMemory::toSpringAiMedia).toList();
        }
        if (aiMessage.type().equals(MessageType.ASSISTANT)) {
            return new AssistantMessage(aiMessage.textContent());
        }
        if (aiMessage.type().equals(MessageType.USER)) {
            return UserMessage.builder().text(aiMessage.textContent())
                    .media(mediaList)
                    .build();
        }
        if (aiMessage.type().equals(MessageType.SYSTEM)) {
            return new SystemMessage(aiMessage.textContent());
        }
        throw new BusinessException("不支持的消息类型");
    }

    @SneakyThrows
    public static Media toSpringAiMedia(AiMessage.Media media) {
        return Media.builder().mimeType(new MediaType(media.getType()))
                .data(new URL(media.getData()))
                .build();
    }
}
