package io.github.qifan777.server.plan.step.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
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
@RequestMapping("admin/task-step")
@AllArgsConstructor
@DefaultFetcherOwner(TaskStepRepository.class)
@SaCheckPermission("/task-step")
@Transactional
public class TaskStepForAdminController {
    private final TaskStepRepository taskStepRepository;

    @GetMapping("{id}")
    public @FetchBy(value = "COMPLEX_FETCHER_FOR_ADMIN") TaskStep findById(@PathVariable String id) {
        return taskStepRepository.findById(id, TaskStepRepository.COMPLEX_FETCHER_FOR_ADMIN).orElseThrow(() -> new BusinessException("数据不存在"));
    }

    @PostMapping("query")
    public Page<@FetchBy(value = "COMPLEX_FETCHER_FOR_ADMIN") TaskStep> query(@RequestBody QueryRequest<TaskStepSpec> queryRequest) {
        return taskStepRepository.findPage(queryRequest, TaskStepRepository.COMPLEX_FETCHER_FOR_ADMIN);
    }

    public String insert(TaskStepInput input) {
        return taskStepRepository.insert(input.toEntity()).id();
    }

    public String update(TaskStepInput input) {
        return taskStepRepository.save(input.toEntity()).id();
    }

    @PostMapping("save")
    public String save(@RequestBody @Validated TaskStepInput input) {
        return StringUtils.hasText(input.getId()) ? update(input) : insert(input);
    }

    @DeleteMapping
    public Boolean delete(@RequestBody List<String> ids) {
        taskStepRepository.deleteAllById(ids);
        return true;
    }
}