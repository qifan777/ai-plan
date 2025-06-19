package io.github.qifan777.server.post.like.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.github.qifan777.server.infrastructure.model.QueryRequest;
import io.github.qifan777.server.post.like.entity.PostLike;
import io.github.qifan777.server.post.like.entity.dto.PostLikeInput;
import io.github.qifan777.server.post.like.entity.dto.PostLikeSpec;
import io.github.qifan777.server.post.like.repository.PostLikeRepository;
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
@RequestMapping("admin/post-like")
@AllArgsConstructor
@DefaultFetcherOwner(PostLikeRepository.class)
@SaCheckPermission("/post-like")
@Transactional
public class PostLikeForAdminController {
    private final PostLikeRepository postLikeRepository;

    @GetMapping("{id}")
    public @FetchBy(value = "COMPLEX_FETCHER_FOR_ADMIN") PostLike findById(@PathVariable String id) {
        return postLikeRepository.findById(id, PostLikeRepository.COMPLEX_FETCHER_FOR_ADMIN).orElseThrow(() -> new BusinessException("数据不存在"));
    }

    @PostMapping("query")
    public Page<@FetchBy(value = "COMPLEX_FETCHER_FOR_ADMIN") PostLike> query(@RequestBody QueryRequest<PostLikeSpec> queryRequest) {
        return postLikeRepository.findPage(queryRequest, PostLikeRepository.COMPLEX_FETCHER_FOR_ADMIN);
    }

    public String insert(PostLikeInput input) {
        return postLikeRepository.insert(input.toEntity()).id();
    }

    public String update(PostLikeInput input) {
        return postLikeRepository.update(input.toEntity()).id();
    }

    @PostMapping("save")
    public String save(@RequestBody @Validated PostLikeInput input) {
        return StringUtils.hasText(input.getId()) ? update(input) : insert(input);
    }

    @DeleteMapping
    public Boolean delete(@RequestBody List<String> ids) {
        postLikeRepository.deleteAllById(ids);
        return true;
    }
}