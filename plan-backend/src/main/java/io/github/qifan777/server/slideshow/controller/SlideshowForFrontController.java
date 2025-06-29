package io.github.qifan777.server.slideshow.controller;

import io.github.qifan777.server.infrastructure.model.QueryRequest;
import io.github.qifan777.server.slideshow.entity.Slideshow;
import io.github.qifan777.server.slideshow.entity.dto.SlideshowSpec;
import io.github.qifan777.server.slideshow.repository.SlideshowRepository;
import io.qifan.infrastructure.common.exception.BusinessException;
import lombok.AllArgsConstructor;
import org.babyfish.jimmer.client.FetchBy;
import org.babyfish.jimmer.client.meta.DefaultFetcherOwner;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("front/slideshow")
@AllArgsConstructor
@DefaultFetcherOwner(SlideshowRepository.class)
@Transactional
public class SlideshowForFrontController {
    private final SlideshowRepository slideshowRepository;

    @GetMapping("{id}")
    public @FetchBy(value = "COMPLEX_FETCHER_FOR_FRONT") Slideshow findById(@PathVariable String id) {
        return slideshowRepository.findById(id, SlideshowRepository.COMPLEX_FETCHER_FOR_FRONT).orElseThrow(() -> new BusinessException("数据不存在"));
    }

    @PostMapping("query")
    public Page<@FetchBy(value = "COMPLEX_FETCHER_FOR_FRONT") Slideshow> query(@RequestBody QueryRequest<SlideshowSpec> queryRequest) {
        return slideshowRepository.findPage(queryRequest, SlideshowRepository.COMPLEX_FETCHER_FOR_FRONT);
    }
}
