package io.github.qifan777.server.post.root.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
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
@RequestMapping("admin/post")
@AllArgsConstructor
@DefaultFetcherOwner(PostRepository.class)
@SaCheckPermission("/post")
@Transactional
public class PostForAdminController {
    private final PostRepository postRepository;

    @GetMapping("{id}")
    public @FetchBy(value = "COMPLEX_FETCHER_FOR_ADMIN") Post findById(@PathVariable String id) {
        return postRepository.findById(id, PostRepository.COMPLEX_FETCHER_FOR_ADMIN).orElseThrow(() -> new BusinessException("数据不存在"));
    }

    @PostMapping("query")
    public Page<@FetchBy(value = "COMPLEX_FETCHER_FOR_ADMIN") Post> query(@RequestBody QueryRequest<PostSpec> queryRequest) {
        return postRepository.findPage(queryRequest, PostRepository.COMPLEX_FETCHER_FOR_ADMIN);
    }

    public String insert(PostInput input) {
        return postRepository.insert(input.toEntity()).id();
    }

    public String update(PostInput input) {
        return postRepository.update(input.toEntity()).id();
    }

    @PostMapping("save")
    public String save(@RequestBody @Validated PostInput input) {
        return StringUtils.hasText(input.getId()) ? update(input) : insert(input);
    }

    @DeleteMapping
    public Boolean delete(@RequestBody List<String> ids) {
        postRepository.deleteAllById(ids);
        return true;
    }
}