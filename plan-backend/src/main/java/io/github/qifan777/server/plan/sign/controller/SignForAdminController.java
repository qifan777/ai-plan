package io.github.qifan777.server.plan.sign.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.stp.StpUtil;
import io.github.qifan777.server.infrastructure.model.QueryRequest;
import io.github.qifan777.server.plan.sign.entity.Sign;
import io.github.qifan777.server.plan.sign.entity.dto.SignInput;
import io.github.qifan777.server.plan.sign.entity.dto.SignSpec;
import io.github.qifan777.server.plan.sign.repository.SignRepository;
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
@RequestMapping("admin/sign")
@AllArgsConstructor
@DefaultFetcherOwner(SignRepository.class)
@SaCheckPermission("/sign")
@Transactional
public class SignForAdminController {
    private final SignRepository signRepository;

    @GetMapping("{id}")
    public @FetchBy(value = "COMPLEX_FETCHER_FOR_ADMIN") Sign findById(@PathVariable String id) {
        return signRepository.findById(id, SignRepository.COMPLEX_FETCHER_FOR_ADMIN).orElseThrow(() -> new BusinessException("数据不存在"));
    }

    @PostMapping("query")
    public Page<@FetchBy(value = "COMPLEX_FETCHER_FOR_ADMIN") Sign> query(@RequestBody QueryRequest<SignSpec> queryRequest) {
        return signRepository.findPage(queryRequest, SignRepository.COMPLEX_FETCHER_FOR_ADMIN);
    }

    public String insert(SignInput input) {
        return signRepository.save(input.toEntity(), SaveMode.INSERT_ONLY).id();
    }

    public String update(SignInput input) {
        return signRepository.save(input.toEntity()).id();
    }

    @PostMapping("save")
    public String save(@RequestBody @Validated SignInput input) {
        input.setUserId(StpUtil.getLoginIdAsString());
        return StringUtils.hasText(input.getId()) ? update(input) : insert(input);
    }

    @DeleteMapping
    public Boolean delete(@RequestBody List<String> ids) {
        signRepository.deleteAllById(ids);
        return true;
    }
}