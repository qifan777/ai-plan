package io.github.qifan777.server.notice.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.github.qifan777.server.infrastructure.model.QueryRequest;
import io.github.qifan777.server.notice.entity.Notice;
import io.github.qifan777.server.notice.entity.dto.NoticeInput;
import io.github.qifan777.server.notice.entity.dto.NoticeSpec;
import io.github.qifan777.server.notice.repository.NoticeRepository;
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
@RequestMapping("admin/notice")
@AllArgsConstructor
@DefaultFetcherOwner(NoticeRepository.class)
@SaCheckPermission("/notice")
@Transactional
public class NoticeForAdminController {
    private final NoticeRepository noticeRepository;

    @GetMapping("{id}")
    public @FetchBy(value = "COMPLEX_FETCHER_FOR_ADMIN") Notice findById(@PathVariable String id) {
        return noticeRepository.findById(id, NoticeRepository.COMPLEX_FETCHER_FOR_ADMIN).orElseThrow(() -> new BusinessException("数据不存在"));
    }

    @PostMapping("query")
    public Page<@FetchBy(value = "COMPLEX_FETCHER_FOR_ADMIN") Notice> query(@RequestBody QueryRequest<NoticeSpec> queryRequest) {
        return noticeRepository.findPage(queryRequest, NoticeRepository.COMPLEX_FETCHER_FOR_ADMIN);
    }

    public String insert(NoticeInput input) {
        return noticeRepository.insert(input.toEntity()).id();
    }

    public String update(NoticeInput input) {
        return noticeRepository.save(input.toEntity()).id();
    }

    @PostMapping("save")
    public String save(@RequestBody @Validated NoticeInput input) {
        return StringUtils.hasText(input.getId()) ? update(input) : insert(input);
    }

    @DeleteMapping
    public Boolean delete(@RequestBody List<String> ids) {
        noticeRepository.deleteAllById(ids);
        return true;
    }
}