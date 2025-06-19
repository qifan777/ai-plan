package io.github.qifan777.server.post.comment.service;

import io.github.qifan777.server.post.comment.repository.PostCommentRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class PostCommentService {
    private final PostCommentRepository postCommentRepository;

}