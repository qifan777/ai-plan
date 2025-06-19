package io.github.qifan777.server.notice.service;

import io.github.qifan777.server.notice.repository.NoticeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class NoticeService {
    private final NoticeRepository noticeRepository;

}