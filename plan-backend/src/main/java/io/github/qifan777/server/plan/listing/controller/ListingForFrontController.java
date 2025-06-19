package io.github.qifan777.server.plan.listing.controller;

import cn.dev33.satoken.stp.StpUtil;
import io.github.qifan777.server.infrastructure.model.QueryRequest;
import io.github.qifan777.server.plan.listing.entity.Listing;
import io.github.qifan777.server.plan.listing.entity.dto.ListingInput;
import io.github.qifan777.server.plan.listing.entity.dto.ListingSpec;
import io.github.qifan777.server.plan.listing.repository.ListingRepository;
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
@RequestMapping("front/listing")
@AllArgsConstructor
@DefaultFetcherOwner(ListingRepository.class)
@Transactional
public class ListingForFrontController {
    private final ListingRepository listingRepository;

    @GetMapping("{id}")
    public @FetchBy(value = "COMPLEX_FETCHER_FOR_FRONT") Listing findById(@PathVariable String id) {
        return listingRepository.findById(id, ListingRepository.COMPLEX_FETCHER_FOR_FRONT).orElseThrow(() -> new BusinessException("数据不存在"));
    }

    @PostMapping("query")
    public Page<@FetchBy(value = "COMPLEX_FETCHER_FOR_FRONT") Listing> query(@RequestBody QueryRequest<ListingSpec> queryRequest) {
        queryRequest.getQuery().setCreatorId(StpUtil.getLoginIdAsString());
        return listingRepository.findPage(queryRequest, ListingRepository.COMPLEX_FETCHER_FOR_FRONT);
    }

    public String insert(ListingInput input) {
        input.setUserId(StpUtil.getLoginIdAsString());
        return listingRepository.insert(input.toEntity()).id();
    }

    public String update(ListingInput input) {
        Listing listing = listingRepository.findById(input.getId(), ListingRepository.COMPLEX_FETCHER_FOR_FRONT).orElseThrow(() -> new BusinessException("数据不存在"));
        if (!listing.creator().id().equals(StpUtil.getLoginIdAsString())) {
            throw new BusinessException("只能修改自己的数据");
        }
        return listingRepository.save(input.toEntity()).id();
    }

    @PostMapping("save")
    public String save(@RequestBody @Validated ListingInput input) {
        return StringUtils.hasText(input.getId()) ? update(input) : insert(input);
    }

    @DeleteMapping
    public Boolean delete(@RequestBody List<String> ids) {
        listingRepository.findByIds(ids, ListingRepository.COMPLEX_FETCHER_FOR_FRONT).forEach(listing -> {
            if (!listing.creator().id().equals(StpUtil.getLoginIdAsString())) {
                throw new BusinessException("只能删除自己的数据");
            }
        });
        listingRepository.deleteAllById(ids);
        return true;
    }
}
