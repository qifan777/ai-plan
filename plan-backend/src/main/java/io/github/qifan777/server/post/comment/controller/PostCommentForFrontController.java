package io.github.qifan777.server.post.comment.controller;

import cn.dev33.satoken.stp.StpUtil;
import io.github.qifan777.server.infrastructure.model.QueryRequest;
import io.github.qifan777.server.post.comment.entity.PostComment;
import io.github.qifan777.server.post.comment.entity.dto.PostCommentInput;
import io.github.qifan777.server.post.comment.entity.dto.PostCommentSpec;
import io.github.qifan777.server.post.comment.repository.PostCommentRepository;
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
@RequestMapping("front/post-comment")
@AllArgsConstructor
@DefaultFetcherOwner(PostCommentRepository.class)
@Transactional
public class PostCommentForFrontController {
    private final PostCommentRepository postCommentRepository;

    @GetMapping("{id}")
    public @FetchBy(value = "COMPLEX_FETCHER_FOR_FRONT") PostComment findById(@PathVariable String id) {
        return postCommentRepository.findById(id, PostCommentRepository.COMPLEX_FETCHER_FOR_FRONT).orElseThrow(() -> new BusinessException("数据不存在"));
    }

    @PostMapping("query")
    public Page<@FetchBy(value = "COMPLEX_FETCHER_FOR_FRONT") PostComment> query(@RequestBody QueryRequest<PostCommentSpec> queryRequest) {
        return postCommentRepository.findPage(queryRequest, PostCommentRepository.COMPLEX_FETCHER_FOR_FRONT);
    }

    public String insert(PostCommentInput input) {
        return postCommentRepository.insert(input.toEntity()).id();
    }

    public String update(PostCommentInput input) {
        PostComment postComment = postCommentRepository.findById(input.getId(), PostCommentRepository.COMPLEX_FETCHER_FOR_FRONT).orElseThrow(() -> new BusinessException("数据不存在"));
        if (!postComment.creator().id().equals(StpUtil.getLoginIdAsString())) {
            throw new BusinessException("只能修改自己的数据");
        }
        return postCommentRepository.update(input.toEntity()).id();
    }

    @PostMapping("save")
    public String save(@RequestBody @Validated PostCommentInput input) {
        return StringUtils.hasText(input.getId()) ? update(input) : insert(input);
    }

    @DeleteMapping
    public Boolean delete(@RequestBody List<String> ids) {
        postCommentRepository.findByIds(ids, PostCommentRepository.COMPLEX_FETCHER_FOR_FRONT).forEach(postComment -> {
            if (!postComment.creator().id().equals(StpUtil.getLoginIdAsString())) {
                throw new BusinessException("只能删除自己的数据");
            }
        });
        postCommentRepository.deleteAllById(ids);
        return true;
    }
}
