package io.github.qifan777.server.post.like.service;

import io.github.qifan777.server.post.like.repository.PostLikeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class PostLikeService {
    private final PostLikeRepository postLikeRepository;

}