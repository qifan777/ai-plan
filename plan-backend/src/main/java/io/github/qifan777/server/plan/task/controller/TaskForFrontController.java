package io.github.qifan777.server.plan.task.controller;

import cn.dev33.satoken.stp.StpUtil;
import io.github.qifan777.server.Immutables;
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
@RequestMapping("front/task")
@AllArgsConstructor
@DefaultFetcherOwner(TaskRepository.class)
@Transactional
public class TaskForFrontController {
    private final TaskRepository taskRepository;

    @GetMapping("{id}")
    public @FetchBy(value = "COMPLEX_FETCHER_FOR_FRONT") Task findById(@PathVariable String id) {
        return taskRepository.findById(id, TaskRepository.COMPLEX_FETCHER_FOR_FRONT).orElseThrow(() -> new BusinessException("数据不存在"));
    }

    @PostMapping("query")
    public Page<@FetchBy(value = "COMPLEX_FETCHER_FOR_FRONT") Task> query(@RequestBody QueryRequest<TaskSpec> queryRequest) {
        queryRequest.getQuery().setCreatorId(StpUtil.getLoginIdAsString());
        return taskRepository.findPage(queryRequest, TaskRepository.COMPLEX_FETCHER_FOR_FRONT);
    }

    public String insert(TaskInput input) {
        return taskRepository.insert(Immutables.createTask(input.toEntity(), draft -> {
            draft.setUserId(StpUtil.getLoginIdAsString());
        })).id();
    }

    public String update(TaskInput input) {
        Task task = taskRepository.findById(input.getId(), TaskRepository.COMPLEX_FETCHER_FOR_FRONT).orElseThrow(() -> new BusinessException("数据不存在"));
        if (!task.creator().id().equals(StpUtil.getLoginIdAsString())) {
            throw new BusinessException("只能修改自己的数据");
        }
        return taskRepository.save(input.toEntity()).id();
    }

    @PostMapping("save")
    public String save(@RequestBody @Validated TaskInput input) {
        return StringUtils.hasText(input.getId()) ? update(input) : insert(input);
    }

    @DeleteMapping
    public Boolean delete(@RequestBody List<String> ids) {
        taskRepository.findByIds(ids, TaskRepository.COMPLEX_FETCHER_FOR_FRONT).forEach(task -> {
            if (!task.creator().id().equals(StpUtil.getLoginIdAsString())) {
                throw new BusinessException("只能删除自己的数据");
            }
        });
        taskRepository.deleteAllById(ids);
        return true;
    }

    @PostMapping("check")
    public Boolean check(@RequestParam String id, @RequestParam Boolean checked) {
        taskRepository.checkTask(id, checked);
        return true;
    }
}
