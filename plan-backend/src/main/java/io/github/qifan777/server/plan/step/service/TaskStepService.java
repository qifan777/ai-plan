package io.github.qifan777.server.plan.step.service;

import io.github.qifan777.server.plan.step.repository.TaskStepRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class TaskStepService {
    private final TaskStepRepository taskStepRepository;

}