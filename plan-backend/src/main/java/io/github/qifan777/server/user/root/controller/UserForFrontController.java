package io.github.qifan777.server.user.root.controller;

import cn.dev33.satoken.annotation.SaCheckDisable;
import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import io.github.qifan777.server.infrastructure.model.LoginDevice;
import io.github.qifan777.server.infrastructure.model.QueryRequest;
import io.github.qifan777.server.menu.entity.Menu;
import io.github.qifan777.server.menu.entity.MenuTable;
import io.github.qifan777.server.menu.repository.MenuRepository;
import io.github.qifan777.server.user.root.entity.User;
import io.github.qifan777.server.user.root.entity.UserDraft;
import io.github.qifan777.server.user.root.entity.dto.*;
import io.github.qifan777.server.user.root.repository.UserRepository;
import io.github.qifan777.server.user.root.service.UserService;
import io.qifan.infrastructure.common.constants.ResultCode;
import io.qifan.infrastructure.common.exception.BusinessException;
import lombok.AllArgsConstructor;
import org.babyfish.jimmer.client.FetchBy;
import org.babyfish.jimmer.client.meta.DefaultFetcherOwner;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("front/user")
@AllArgsConstructor
@DefaultFetcherOwner(UserRepository.class)
@Transactional
@SaCheckDisable
public class UserForFrontController {
    private final UserRepository userRepository;
    private final MenuRepository menuRepository;
    private final UserService userService;
    private final StringRedisTemplate redisTemplate;

    @GetMapping("basic-info")
    public UserBasicView getBasicInfo(@RequestParam String id) {
        return new UserBasicView(userRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ResultCode.NotFindError, "数据不存在")));
    }

    @GetMapping("info")
    public @FetchBy(value = "USER_ROLE_FETCHER") User getUserInfo() {
        return userRepository.findById(StpUtil.getLoginIdAsString(), UserRepository.USER_ROLE_FETCHER)
                .orElseThrow(() -> new NotLoginException(NotLoginException.NOT_TOKEN_MESSAGE, LoginDevice.MP_WECHAT, NotLoginException.NOT_TOKEN));
    }


    @GetMapping("menus")
    public List<@FetchBy(value = "SIMPLE_FETCHER", ownerType = MenuRepository.class) Menu> getUserMenus() {
        MenuTable t = MenuTable.$;
        return menuRepository.sql()
                .createQuery(t)
                .where(t
                        .roles(roleMenuRelTableEx -> roleMenuRelTableEx
                                .role()
                                .users(userRoleRelTableEx -> userRoleRelTableEx
                                        .user()
                                        .id()
                                        .eq(StpUtil.getLoginIdAsString()))))
                .select(t.fetch(MenuRepository.SIMPLE_FETCHER))
                .execute();
    }

    @SaIgnore
    @PostMapping("register")
    public SaTokenInfo register(@RequestBody @Validated UserRegisterInput registerInput) {
        return userService.register(registerInput);
    }

    @SaIgnore
    @PostMapping("login")
    public SaTokenInfo login(@RequestBody @Validated UserLoginInput loginInput) {
        return userService.login(loginInput);
    }

    @SaIgnore
    @PutMapping("password")
    public SaTokenInfo passwordRest(@RequestBody @Validated UserResetPasswordInput restInput) {
        return userService.passwordRest(restInput);
    }

    @PostMapping("info")
    public String updateInfo(@RequestBody @Validated UserInfoInput restInput) {
        return userRepository.save(UserDraft.$.produce(restInput.toEntity(), draft -> {
            draft.setId(StpUtil.getLoginIdAsString());
        })).id();
    }

    @PostMapping("delete")
    public Boolean delete() {
        User user = userRepository.findById(StpUtil.getLoginIdAsString()).orElseThrow();
        redisTemplate.opsForValue().set("user:" + user.phone(), "1", 1, TimeUnit.DAYS);
        userRepository.deleteById(StpUtil.getLoginIdAsString());
        return true;
    }

    @PostMapping("query")
    public Page<@FetchBy(value = "COMPLEX_FETCHER_FOR_ADMIN") User> query(@RequestBody QueryRequest<UserSpec> queryRequest) {
        queryRequest.setPageSize(10);
        queryRequest.setPageNum(1);
        return userRepository.findPage(queryRequest, UserRepository.COMPLEX_FETCHER_FOR_ADMIN);
    }
}
