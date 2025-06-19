package io.github.qifan777.server.plan.sign.service;

import io.github.qifan777.server.plan.sign.repository.SignRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class SignService {
    private final SignRepository signRepository;

}