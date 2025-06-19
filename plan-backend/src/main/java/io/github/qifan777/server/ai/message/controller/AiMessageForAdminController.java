package io.github.qifan777.server.ai.message.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.github.qifan777.server.ai.message.entity.AiMessage;
import io.github.qifan777.server.ai.message.entity.dto.AiMessageInput;
import io.github.qifan777.server.ai.message.entity.dto.AiMessageSpec;
import io.github.qifan777.server.ai.message.repository.AiMessageRepository;
import io.github.qifan777.server.infrastructure.model.QueryRequest;
import io.qifan.infrastructure.common.exception.BusinessException;
import lombok.AllArgsConstructor;
import org.babyfish.jimmer.client.FetchBy;
import org.babyfish.jimmer.client.meta.DefaultFetcherOwner;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("admin/ai-message")
@AllArgsConstructor
@DefaultFetcherOwner(AiMessageRepository.class)
@SaCheckPermission("/ai-message")
@Transactional
public class AiMessageForAdminController {
    private final AiMessageRepository aiMessageRepository;


    @GetMapping("{id}")
    public @FetchBy(value = "COMPLEX_FETCHER_FOR_ADMIN") AiMessage findById(@PathVariable String id) {
        return aiMessageRepository.findById(id, AiMessageRepository.COMPLEX_FETCHER_FOR_ADMIN).orElseThrow(() -> new BusinessException("数据不存在"));
    }

    @PostMapping("query")
    public Page<@FetchBy(value = "COMPLEX_FETCHER_FOR_ADMIN") AiMessage> query(@RequestBody QueryRequest<AiMessageSpec> queryRequest) {
        return aiMessageRepository.findPage(queryRequest, AiMessageRepository.COMPLEX_FETCHER_FOR_ADMIN);
    }

    public String insert(AiMessageInput input) {
        return aiMessageRepository.insert(input.toEntity()).id();
    }

    public String update(AiMessageInput input) {
        return aiMessageRepository.save(input.toEntity()).id();
    }

    @PostMapping("save")
    public String save(@RequestBody @Validated AiMessageInput input) {
        return StringUtils.hasText(input.getId()) ? update(input) : insert(input);
    }

    @DeleteMapping
    public Boolean delete(@RequestBody List<String> ids) {
        aiMessageRepository.deleteAllById(ids);
        return true;
    }
}