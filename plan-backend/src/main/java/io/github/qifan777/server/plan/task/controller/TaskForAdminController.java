package io.github.qifan777.server.plan.task.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.github.qifan777.server.infrastructure.model.QueryRequest;
import io.github.qifan777.server.plan.task.entity.Task;
import io.github.qifan777.server.plan.task.entity.dto.TaskInput;
import io.github.qifan777.server.plan.task.entity.dto.TaskSpec;
import io.github.qifan777.server.plan.task.repository.TaskRepository;
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
@RequestMapping("admin/task")
@AllArgsConstructor
@DefaultFetcherOwner(TaskRepository.class)
@SaCheckPermission("/task")
@Transactional
public class TaskForAdminController {
    private final TaskRepository taskRepository;

    @GetMapping("{id}")
    public @FetchBy(value = "COMPLEX_FETCHER_FOR_ADMIN") Task findById(@PathVariable String id) {
        return taskRepository.findById(id, TaskRepository.COMPLEX_FETCHER_FOR_ADMIN).orElseThrow(() -> new BusinessException("数据不存在"));
    }

    @PostMapping("query")
    public Page<@FetchBy(value = "COMPLEX_FETCHER_FOR_ADMIN") Task> query(@RequestBody QueryRequest<TaskSpec> queryRequest) {
        return taskRepository.findPage(queryRequest, TaskRepository.COMPLEX_FETCHER_FOR_ADMIN);
    }

    public String insert(TaskInput input) {
        return taskRepository.insert(input.toEntity()).id();
    }

    public String update(TaskInput input) {
        return taskRepository.save(input.toEntity()).id();
    }

    @PostMapping("save")
    public String save(@RequestBody @Validated TaskInput input) {
        return StringUtils.hasText(input.getId()) ? update(input) : insert(input);
    }

    @DeleteMapping
    public Boolean delete(@RequestBody List<String> ids) {
        taskRepository.deleteAllById(ids);
        return true;
    }
}