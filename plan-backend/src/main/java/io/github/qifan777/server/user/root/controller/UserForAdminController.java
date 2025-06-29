package io.github.qifan777.server.user.root.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.secure.BCrypt;
import cn.dev33.satoken.stp.StpUtil;
import io.github.qifan777.server.dict.model.DictConstants;
import io.github.qifan777.server.infrastructure.model.QueryRequest;
import io.github.qifan777.server.user.root.entity.User;
import io.github.qifan777.server.user.root.entity.UserDraft;
import io.github.qifan777.server.user.root.entity.dto.UserCreateInput;
import io.github.qifan777.server.user.root.entity.dto.UserSpec;
import io.github.qifan777.server.user.root.entity.dto.UserUpdateInput;
import io.github.qifan777.server.user.root.repository.UserRepository;
import io.qifan.infrastructure.common.exception.BusinessException;
import lombok.AllArgsConstructor;
import org.babyfish.jimmer.client.FetchBy;
import org.babyfish.jimmer.client.meta.DefaultFetcherOwner;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("admin/user")
@AllArgsConstructor
@DefaultFetcherOwner(UserRepository.class)
//@SaCheckPermission("/user")
@Transactional
public class UserForAdminController {
    private final UserRepository userRepository;

    @GetMapping("{id}")
    public @FetchBy(value = "USER_ROLE_FETCHER") User findById(@PathVariable String id) {
        return userRepository.findById(id, UserRepository.USER_ROLE_FETCHER).orElseThrow(() -> new BusinessException("数据不存在"));
    }

    @PostMapping("query")
    public Page<@FetchBy(value = "COMPLEX_FETCHER_FOR_ADMIN") User> query(@RequestBody QueryRequest<UserSpec> queryRequest) {
        return userRepository.findPage(queryRequest, UserRepository.COMPLEX_FETCHER_FOR_ADMIN);
    }

    @SaCheckPermission("/user")
    @PostMapping
    public String create(@RequestBody @Validated UserCreateInput userInput) {
        userInput.setId(null);
        User user = userInput.toEntity();
        return userRepository.insert(beforeSave(user, userInput.getRoleIds())).id();

    }

    @SaCheckPermission("/user")
    @PutMapping
    public String update(@RequestBody @Validated UserUpdateInput userInput) {
        User user = userInput.toEntity();
        if (user.status().equals(DictConstants.UserStatus.BANNED)) {
            StpUtil.kickout(user.id());
            StpUtil.disable(user.id(), 60 * 60 * 24 * 30 * 12 * 10);
        } else if (user.status().equals(DictConstants.UserStatus.NORMAL)) {
            StpUtil.untieDisable(user.id());
        }
        return userRepository.save(beforeSave(user, userInput.getRoleIds())).id();
    }

    @SaCheckPermission("/user")
    @DeleteMapping
    public Boolean delete(@RequestBody List<String> ids) {
        userRepository.deleteAllById(ids);
        return true;
    }

    public User beforeSave(User user, String[] roleIds) {
        return UserDraft.$.produce(user, draft -> {
            if (draft.password().length() <= 16) {
                draft.setPassword(BCrypt.hashpw(draft.password()));
            }
            draft.setRoles(new ArrayList<>());
            Arrays.stream(roleIds).forEach(roleId -> {
                draft.addIntoRoles(userRole -> {
                    userRole.applyRole(role -> role.setId(roleId));
                    userRole.setUser(user);
                });
            });
        });
    }

}