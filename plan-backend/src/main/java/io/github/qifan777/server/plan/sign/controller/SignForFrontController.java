package io.github.qifan777.server.plan.sign.controller;

import cn.dev33.satoken.stp.StpUtil;
import io.github.qifan777.server.infrastructure.model.QueryRequest;
import io.github.qifan777.server.plan.sign.entity.Sign;
import io.github.qifan777.server.plan.sign.entity.dto.SignInput;
import io.github.qifan777.server.plan.sign.entity.dto.SignSpec;
import io.github.qifan777.server.plan.sign.repository.SignRepository;
import io.github.qifan777.server.user.root.entity.User;
import io.github.qifan777.server.user.root.entity.UserTable;
import io.github.qifan777.server.user.root.repository.UserRepository;
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

import static io.github.qifan777.server.plan.sign.repository.SignRepository.SIGN_FETCHER;

@RestController
@RequestMapping("front/sign")
@AllArgsConstructor
@DefaultFetcherOwner(SignRepository.class)
@Transactional
public class SignForFrontController {
    private final SignRepository signRepository;
    private final UserRepository userRepository;

    @GetMapping("{id}")
    public @FetchBy(value = "COMPLEX_FETCHER_FOR_FRONT") Sign findById(@PathVariable String id) {
        return signRepository.findById(id, SignRepository.COMPLEX_FETCHER_FOR_FRONT).orElseThrow(() -> new BusinessException("数据不存在"));
    }

    @PostMapping("query")
    public Page<@FetchBy(value = "COMPLEX_FETCHER_FOR_FRONT") Sign> query(@RequestBody QueryRequest<SignSpec> queryRequest) {
        queryRequest.getQuery().setCreatorId(StpUtil.getLoginIdAsString());
        return signRepository.findPage(queryRequest, SignRepository.COMPLEX_FETCHER_FOR_FRONT);
    }

    public String insert(SignInput input) {
        input.setUserId(StpUtil.getLoginIdAsString());
        return signRepository.save(input.toEntity(), SaveMode.INSERT_ONLY).id();
    }

    public String update(SignInput input) {
        Sign sign = signRepository.findById(input.getId(), SignRepository.COMPLEX_FETCHER_FOR_FRONT).orElseThrow(() -> new BusinessException("数据不存在"));
        if (!sign.creator().id().equals(StpUtil.getLoginIdAsString())) {
            throw new BusinessException("只能修改自己的数据");
        }
        return signRepository.save(input.toEntity()).id();
    }

    @PostMapping("save")
    public String save(@RequestBody @Validated SignInput input) {
        return StringUtils.hasText(input.getId()) ? update(input) : insert(input);
    }

    @DeleteMapping
    public Boolean delete(@RequestBody List<String> ids) {
        signRepository.findByIds(ids, SignRepository.COMPLEX_FETCHER_FOR_FRONT).forEach(sign -> {
            if (!sign.creator().id().equals(StpUtil.getLoginIdAsString())) {
                throw new BusinessException("只能删除自己的数据");
            }
        });
        signRepository.deleteAllById(ids);
        return true;
    }

    @GetMapping("user")
    public List<@FetchBy(value = "SIGN_FETCHER") User> findUser() {
        UserTable t = UserTable.$;
        return userRepository.sql()
                .createQuery(t)
                .orderBy(t.signCount().desc())
                .select(t.fetch(SIGN_FETCHER))
                .execute();
    }
}
