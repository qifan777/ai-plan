package io.github.qifan777.server.post.like.controller;

import cn.dev33.satoken.stp.StpUtil;
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
@RequestMapping("front/post-like")
@AllArgsConstructor
@DefaultFetcherOwner(PostLikeRepository.class)
@Transactional
public class PostLikeForFrontController {
    private final PostLikeRepository postLikeRepository;

    @GetMapping("{id}")
    public @FetchBy(value = "COMPLEX_FETCHER_FOR_FRONT") PostLike findById(@PathVariable String id) {
        return postLikeRepository.findById(id, PostLikeRepository.COMPLEX_FETCHER_FOR_FRONT).orElseThrow(() -> new BusinessException("数据不存在"));
    }

    @PostMapping("query")
    public Page<@FetchBy(value = "COMPLEX_FETCHER_FOR_FRONT") PostLike> query(@RequestBody QueryRequest<PostLikeSpec> queryRequest) {
        queryRequest.getQuery().setCreatorId(StpUtil.getLoginIdAsString());
        return postLikeRepository.findPage(queryRequest, PostLikeRepository.COMPLEX_FETCHER_FOR_FRONT);
    }

    public String insert(PostLikeInput input) {
        return postLikeRepository.insert(input.toEntity()).id();
    }

    public String update(PostLikeInput input) {
        PostLike postLike = postLikeRepository.findById(input.getId(), PostLikeRepository.COMPLEX_FETCHER_FOR_FRONT).orElseThrow(() -> new BusinessException("数据不存在"));
        if (!postLike.creator().id().equals(StpUtil.getLoginIdAsString())) {
            throw new BusinessException("只能修改自己的数据");
        }
        return postLikeRepository.update(input.toEntity()).id();
    }

    @PostMapping("save")
    public String save(@RequestBody @Validated PostLikeInput input) {
        return StringUtils.hasText(input.getId()) ? update(input) : insert(input);
    }

    @DeleteMapping
    public Boolean delete(@RequestBody List<String> ids) {
        postLikeRepository.findByIds(ids, PostLikeRepository.COMPLEX_FETCHER_FOR_FRONT).forEach(postLike -> {
            if (!postLike.creator().id().equals(StpUtil.getLoginIdAsString())) {
                throw new BusinessException("只能删除自己的数据");
            }
        });
        postLikeRepository.deleteAllById(ids);
        return true;
    }
}
