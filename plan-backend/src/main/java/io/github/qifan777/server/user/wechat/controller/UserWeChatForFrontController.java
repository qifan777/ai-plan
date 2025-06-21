package io.github.qifan777.server.user.wechat.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.stp.SaTokenInfo;
import io.github.qifan777.server.user.wechat.model.UserWeChatRegisterInput;
import io.github.qifan777.server.user.wechat.model.UserWeChatRegisterInputV2;
import io.github.qifan777.server.user.wechat.repository.UserWeChatRepository;
import io.github.qifan777.server.user.wechat.service.UserWeChatService;
import lombok.AllArgsConstructor;
import org.babyfish.jimmer.client.meta.DefaultFetcherOwner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("front/user-we-chat")
@AllArgsConstructor
@DefaultFetcherOwner(UserWeChatRepository.class)
@Transactional
public class UserWeChatForFrontController {
    private final UserWeChatService userWeChatService;

    @SaIgnore
    @PostMapping("register")
    public SaTokenInfo register(@RequestBody @Validated UserWeChatRegisterInput registerInput) {
        return userWeChatService.register(registerInput);
    }

    @SaIgnore
    @PostMapping("register2")
    public SaTokenInfo registerV2(@RequestBody @Validated UserWeChatRegisterInputV2 registerInputV2) {
        return userWeChatService.registerV2(registerInputV2);
    }

    @PostMapping("register/open-id")
    @SaIgnore
    public SaTokenInfo registerByOpenId(@RequestParam String loginCode) {
        return userWeChatService.registerByOpenId(loginCode);
    }
}
