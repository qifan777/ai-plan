package io.github.qifan777.server.plan.step.controller;

import cn.dev33.satoken.stp.StpUtil;
import io.github.qifan777.server.infrastructure.model.QueryRequest;
import io.github.qifan777.server.plan.step.entity.TaskStep;
import io.github.qifan777.server.plan.step.entity.dto.TaskStepInput;
import io.github.qifan777.server.plan.step.entity.dto.TaskStepSpec;
import io.github.qifan777.server.plan.step.repository.TaskStepRepository;
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
@RequestMapping("front/task-step")
@AllArgsConstructor
@DefaultFetcherOwner(TaskStepRepository.class)
@Transactional
public class TaskStepForFrontController {
    private final TaskStepRepository taskStepRepository;

    @GetMapping("{id}")
    public @FetchBy(value = "COMPLEX_FETCHER_FOR_FRONT") TaskStep findById(@PathVariable String id) {
        return taskStepRepository.findById(id, TaskStepRepository.COMPLEX_FETCHER_FOR_FRONT).orElseThrow(() -> new BusinessException("数据不存在"));
    }

    @PostMapping("query")
    public Page<@FetchBy(value = "COMPLEX_FETCHER_FOR_FRONT") TaskStep> query(@RequestBody QueryRequest<TaskStepSpec> queryRequest) {
        queryRequest.getQuery().setCreatorId(StpUtil.getLoginIdAsString());
        return taskStepRepository.findPage(queryRequest, TaskStepRepository.COMPLEX_FETCHER_FOR_FRONT);
    }

    public String insert(TaskStepInput input) {
        return taskStepRepository.insert(input.toEntity()).id();
    }

    public String update(TaskStepInput input) {
        TaskStep taskStep = taskStepRepository.findById(input.getId(), TaskStepRepository.COMPLEX_FETCHER_FOR_FRONT).orElseThrow(() -> new BusinessException("数据不存在"));
        if (!taskStep.creator().id().equals(StpUtil.getLoginIdAsString())) {
            throw new BusinessException("只能修改自己的数据");
        }
        return taskStepRepository.save(input.toEntity()).id();
    }

    @PostMapping("save")
    public String save(@RequestBody @Validated TaskStepInput input) {
        return StringUtils.hasText(input.getId()) ? update(input) : insert(input);
    }

    @DeleteMapping
    public Boolean delete(@RequestBody List<String> ids) {
        taskStepRepository.findByIds(ids, TaskStepRepository.COMPLEX_FETCHER_FOR_FRONT).forEach(taskStep -> {
            if (!taskStep.creator().id().equals(StpUtil.getLoginIdAsString())) {
                throw new BusinessException("只能删除自己的数据");
            }
        });
        taskStepRepository.deleteAllById(ids);
        return true;
    }
}
