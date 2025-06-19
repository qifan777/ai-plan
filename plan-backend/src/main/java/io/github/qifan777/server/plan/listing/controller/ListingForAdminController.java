package io.github.qifan777.server.plan.listing.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.github.qifan777.server.infrastructure.model.QueryRequest;
import io.github.qifan777.server.plan.listing.entity.Listing;
import io.github.qifan777.server.plan.listing.entity.dto.ListingInput;
import io.github.qifan777.server.plan.listing.entity.dto.ListingSpec;
import io.github.qifan777.server.plan.listing.repository.ListingRepository;
import io.qifan.infrastructure.common.exception.BusinessException;
import lombok.AllArgsConstructor;
import org.babyfish.jimmer.client.FetchBy;
import org.babyfish.jimmer.client.meta.DefaultFetcherOwner;
import org.babyfish.jimmer.sql.ast.mutation.SaveMode;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("admin/listing")
@AllArgsConstructor
@DefaultFetcherOwner(ListingRepository.class)
@SaCheckPermission("/listing")
@Transactional
public class ListingForAdminController {
    private final ListingRepository listingRepository;

    @GetMapping("{id}")
    public @FetchBy(value = "COMPLEX_FETCHER_FOR_ADMIN") Listing findById(@PathVariable String id) {
        return listingRepository.findById(id, ListingRepository.COMPLEX_FETCHER_FOR_ADMIN).orElseThrow(() -> new BusinessException("数据不存在"));
    }

    @PostMapping("query")
    public Page<@FetchBy(value = "COMPLEX_FETCHER_FOR_ADMIN") Listing> query(@RequestBody QueryRequest<ListingSpec> queryRequest) {
        return listingRepository.findPage(queryRequest, ListingRepository.COMPLEX_FETCHER_FOR_ADMIN);
    }

    public String insert(ListingInput input) {
        return listingRepository.save(input.toEntity(), SaveMode.INSERT_ONLY).id();
    }

    public String update(ListingInput input) {
        return listingRepository.save(input.toEntity()).id();
    }

    @PostMapping("save")
    public String save(@RequestBody @Validated ListingInput input) {
        return StringUtils.hasText(input.getId()) ? update(input) : insert(input);
    }

    @DeleteMapping
    public Boolean delete(@RequestBody List<String> ids) {
        listingRepository.deleteAllById(ids);
        return true;
    }
}