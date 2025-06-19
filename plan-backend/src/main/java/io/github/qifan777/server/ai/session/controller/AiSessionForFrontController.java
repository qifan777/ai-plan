package io.github.qifan777.server.ai.session.controller;

import cn.dev33.satoken.stp.StpUtil;
import io.github.qifan777.server.ai.session.entity.AiSession;
import io.github.qifan777.server.ai.session.entity.dto.AiSessionInput;
import io.github.qifan777.server.ai.session.entity.dto.AiSessionSpec;
import io.github.qifan777.server.ai.session.repository.AiSessionRepository;
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
@RequestMapping("front/ai-session")
@AllArgsConstructor
@DefaultFetcherOwner(AiSessionRepository.class)
@Transactional
public class AiSessionForFrontController {
    private final AiSessionRepository aiSessionRepository;

    @GetMapping("{id}")
    public @FetchBy(value = "COMPLEX_FETCHER_FOR_FRONT") AiSession findById(@PathVariable String id) {
        return aiSessionRepository.findById(id, AiSessionRepository.COMPLEX_FETCHER_FOR_FRONT).orElseThrow(() -> new BusinessException("数据不存在"));
    }

    @PostMapping("query")
    public Page<@FetchBy(value = "COMPLEX_FETCHER_FOR_FRONT") AiSession> query(@RequestBody QueryRequest<AiSessionSpec> queryRequest) {
        queryRequest.getQuery().setCreatorId(StpUtil.getLoginIdAsString());
        return aiSessionRepository.findPage(queryRequest, AiSessionRepository.COMPLEX_FETCHER_FOR_FRONT);
    }

    public String insert(AiSessionInput input) {
        return aiSessionRepository.insert(input.toEntity()).id();
    }

    public String update(AiSessionInput input) {
        AiSession aiSession = aiSessionRepository.findById(input.getId(), AiSessionRepository.COMPLEX_FETCHER_FOR_FRONT).orElseThrow(() -> new BusinessException("数据不存在"));
        if (!aiSession.creator().id().equals(StpUtil.getLoginIdAsString())) {
            throw new BusinessException("只能修改自己的数据");
        }
        return aiSessionRepository.save(input.toEntity()).id();
    }

    @PostMapping("save")
    public String save(@RequestBody @Validated AiSessionInput input) {
        return StringUtils.hasText(input.getId()) ? update(input) : insert(input);
    }

    @DeleteMapping
    public Boolean delete(@RequestBody List<String> ids) {
        aiSessionRepository.findByIds(ids, AiSessionRepository.COMPLEX_FETCHER_FOR_FRONT).forEach(aiSession -> {
            if (!aiSession.creator().id().equals(StpUtil.getLoginIdAsString())) {
                throw new BusinessException("只能删除自己的数据");
            }
        });
        aiSessionRepository.deleteAllById(ids);
        return true;
    }
}
