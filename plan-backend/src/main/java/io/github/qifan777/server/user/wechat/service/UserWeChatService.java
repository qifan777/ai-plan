package io.github.qifan777.server.user.wechat.service;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import io.github.qifan777.server.dict.model.DictConstants;
import io.github.qifan777.server.infrastructure.model.LoginDevice;
import io.github.qifan777.server.user.root.entity.User;
import io.github.qifan777.server.user.root.entity.UserDraft;
import io.github.qifan777.server.user.root.entity.UserFetcher;
import io.github.qifan777.server.user.root.entity.UserTable;
import io.github.qifan777.server.user.root.event.UserEvent;
import io.github.qifan777.server.user.root.repository.UserRepository;
import io.github.qifan777.server.user.wechat.entity.UserWeChat;
import io.github.qifan777.server.user.wechat.entity.UserWeChatDraft;
import io.github.qifan777.server.user.wechat.entity.UserWeChatFetcher;
import io.github.qifan777.server.user.wechat.entity.UserWeChatTable;
import io.github.qifan777.server.user.wechat.model.UserWeChatRegisterInput;
import io.github.qifan777.server.user.wechat.model.UserWeChatRegisterInputV2;
import io.github.qifan777.server.user.wechat.repository.UserWeChatRepository;
import io.qifan.infrastructure.common.exception.BusinessException;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class UserWeChatService {

    private final UserWeChatRepository userWeChatRepository;
    private final UserRepository userRepository;
    private final WxMaService wxMaService;
    private final ApplicationEventPublisher eventPublisher;
    private final StringRedisTemplate stringRedisTemplate;


    @SneakyThrows
    public SaTokenInfo register(UserWeChatRegisterInput registerInput) {
        String lock = stringRedisTemplate.opsForValue().get("user:" + registerInput.getPhone());
        if (StringUtils.hasLength(lock)) {
            throw new BusinessException("注销后1日之内不能注册");
        }
        UserWeChatTable t1 = UserWeChatTable.$;
        WxMaJscode2SessionResult session = wxMaService.getUserService()
                .getSessionInfo(registerInput.getLoginCode());
        String openid = session.getOpenid();
        UserWeChat userWeChat = userWeChatRepository.sql()
                .createQuery(t1)
                .where(t1.openId().eq(openid))
                .select(t1.fetch(UserWeChatFetcher.$.allScalarFields().user(UserFetcher.$.phone())))
                .fetchOptional()
                .orElseGet(() -> {
                    UserTable t2 = UserTable.$;
                    // 查询手机号对应的用户
                    User user = userRepository.sql().createQuery(t2)
                            .where(t2.phone().eq(registerInput.getPhone()))
                            .select(t2)
                            .fetchOptional()
                            // 手机号查询的用户为空,则说明该用户从未使用过起凡商城
                            .orElseGet(() -> {
                                return userRepository.save(UserDraft.$.produce(draft -> {
                                    draft.setNickname("微信用户")
                                            // 此处密码无需加密,
                                            .setPassword("123456")
                                            .setStatus(DictConstants.UserStatus.NORMAL)
                                            .setPhone(registerInput.getPhone());
                                }));
                            });
                    return userWeChatRepository.save(UserWeChatDraft.$.produce(draft -> {
                        draft.setUser(user)
                                .setOpenId(openid);
                    }));
                });
        StpUtil.login(userWeChat.user().id(), new SaLoginModel().setDevice(LoginDevice.MP_WECHAT)
                .setTimeout(60 * 60 * 24 * 30 * 36));
        return StpUtil.getTokenInfo();
    }

    @SneakyThrows
    public SaTokenInfo registerV2(UserWeChatRegisterInputV2 registerInputV2) {
        String phoneNumber = wxMaService.getUserService().getPhoneNoInfo(registerInputV2.getPhoneCode()).getPhoneNumber();
        UserWeChatRegisterInput userWeChatRegisterInput = new UserWeChatRegisterInput();
        userWeChatRegisterInput.setPhone(phoneNumber);
        userWeChatRegisterInput.setInviteCode(registerInputV2.getInviteCode());
        userWeChatRegisterInput.setLoginCode(registerInputV2.getLoginCode());
        return register(userWeChatRegisterInput);
    }

    @SneakyThrows
    public SaTokenInfo registerByOpenId(String loginCode) {
        UserWeChatTable t1 = UserWeChatTable.$;
        WxMaJscode2SessionResult session = wxMaService.getUserService()
                .getSessionInfo(loginCode);
        String openid = session.getOpenid();
        UserWeChat userWeChat = userWeChatRepository.sql()
                .createQuery(t1)
                .where(t1.openId().eq(openid))
                .select(t1.fetch(UserWeChatFetcher.$.allScalarFields().user(UserFetcher.$.phone())))
                .fetchOptional()
                .orElseGet(() -> {
                    User user = userRepository.save(UserDraft.$.produce(draft -> {
                        draft.setNickname("微信用户")
                                // 此处密码无需加密,
                                .setPassword("123456")
                                .setStatus(DictConstants.UserStatus.NORMAL)
                                // 不需要手机号
                                .setPhone(RandomUtil.randomNumbers(11));
                    }));
                    return userWeChatRepository.save(UserWeChatDraft.$.produce(draft -> {
                        draft.setUser(user)
                                .setOpenId(openid);
                    }));
                });
        StpUtil.login(userWeChat.user().id(), new SaLoginModel().setDevice(LoginDevice.MP_WECHAT)
                .setTimeout(60 * 60 * 24 * 30 * 36));
        return StpUtil.getTokenInfo();
    }
}