package io.github.qifan777.server.post.root.controller;

import cn.dev33.satoken.stp.StpUtil;
import io.github.qifan777.server.infrastructure.model.QueryRequest;
import io.github.qifan777.server.post.root.entity.Post;
import io.github.qifan777.server.post.root.entity.dto.PostInput;
import io.github.qifan777.server.post.root.entity.dto.PostSpec;
import io.github.qifan777.server.post.root.repository.PostRepository;
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
@RequestMapping("front/post")
@AllArgsConstructor
@DefaultFetcherOwner(PostRepository.class)
@Transactional
public class PostForFrontController {
    private final PostRepository postRepository;

    @GetMapping("{id}")
    public @FetchBy(value = "COMPLEX_FETCHER_FOR_FRONT") Post findById(@PathVariable String id) {
        return postRepository.findById(id, PostRepository.COMPLEX_FETCHER_FOR_FRONT).orElseThrow(() -> new BusinessException("数据不存在"));
    }

    @PostMapping("query")
    public Page<@FetchBy(value = "COMPLEX_FETCHER_FOR_FRONT") Post> query(@RequestBody QueryRequest<PostSpec> queryRequest) {
        return postRepository.findPage(queryRequest, PostRepository.COMPLEX_FETCHER_FOR_FRONT);
    }

    public String insert(PostInput input) {
        return postRepository.insert(input.toEntity()).id();
    }

    public String update(PostInput input) {
        Post post = postRepository.findById(input.getId(), PostRepository.COMPLEX_FETCHER_FOR_FRONT).orElseThrow(() -> new BusinessException("数据不存在"));
        if (!post.creator().id().equals(StpUtil.getLoginIdAsString())) {
            throw new BusinessException("只能修改自己的数据");
        }
        return postRepository.update(input.toEntity()).id();
    }

    @PostMapping("save")
    public String save(@RequestBody @Validated PostInput input) {
        return StringUtils.hasText(input.getId()) ? update(input) : insert(input);
    }

    @DeleteMapping
    public Boolean delete(@RequestBody List<String> ids) {
        for (Post post : postRepository.findByIds(ids, PostRepository.COMPLEX_FETCHER_FOR_FRONT)) {
            if (!post.creator().id().equals(StpUtil.getLoginIdAsString())) {
                throw new BusinessException("只能删除自己的数据");
            }
        }
        postRepository.deleteAllById(ids);
        return true;
    }
}
