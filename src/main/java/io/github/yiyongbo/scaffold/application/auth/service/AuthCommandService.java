package io.github.yiyongbo.scaffold.application.auth.service;

import cn.hutool.core.util.IdUtil;
import io.github.yiyongbo.scaffold.application.auth.command.LoginCommand;
import io.github.yiyongbo.scaffold.application.auth.dto.LoginResultDTO;
import io.github.yiyongbo.scaffold.common.exception.BizAssert;
import io.github.yiyongbo.scaffold.common.response.CommonResponseCode;
import io.github.yiyongbo.scaffold.common.security.LoginUser;
import io.github.yiyongbo.scaffold.common.security.SecurityUserHolder;
import io.github.yiyongbo.scaffold.domain.auth.cache.LoginSessionCache;
import io.github.yiyongbo.scaffold.domain.auth.gateway.TokenGateway;
import io.github.yiyongbo.scaffold.domain.auth.model.valueobject.LoginSessionValueObject;
import io.github.yiyongbo.scaffold.domain.auth.model.valueobject.TokenPayloadValueObject;
import io.github.yiyongbo.scaffold.domain.user.gateway.PasswordGateway;
import io.github.yiyongbo.scaffold.domain.user.model.entity.UserEntity;
import io.github.yiyongbo.scaffold.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * 认证命令服务
 *
 * @author kidd
 * @since 2026/6/16 22:59
 */
@Service
@RequiredArgsConstructor
public class AuthCommandService {

    private static final String TOKEN_TYPE = "Bearer";

    private final UserRepository userRepository;

    private final PasswordGateway passwordGateway;
    private final TokenGateway tokenGateway;
    private final LoginSessionCache loginSessionCache;

    /**
     * 用户登录
     *
     * @param command 登录命令
     * @return 登录结果
     */
    public LoginResultDTO login(LoginCommand command) {
        UserEntity user = userRepository.findByUsername(command.getUsername())
                .orElseThrow(() -> BizAssert.newException(CommonResponseCode.UNAUTHORIZED, "账号或密码错误"));

        BizAssert.isTrue(user.isEnabled(), CommonResponseCode.UNAUTHORIZED, "账号已禁用");

        boolean passwordMatched = passwordGateway.matches(command.getPassword(), user.getPassword());
        BizAssert.isTrue(passwordMatched, CommonResponseCode.UNAUTHORIZED, "账号或密码错误");

        String jti = IdUtil.fastUUID();
        TokenPayloadValueObject tokenPayload = TokenPayloadValueObject.builder()
                .jti(jti)
                .userId(user.getId())
                .username(user.getUsername())
                .build();
        String accessToken = tokenGateway.generateAccessToken(tokenPayload);

        LoginSessionValueObject loginSession = LoginSessionValueObject.builder()
                .jti(jti)
                .userId(user.getId())
                .username(user.getUsername())
                .loginTime(LocalDateTime.now())
                .build();
        Long accessTokenExpiresIn = tokenGateway.getAccessTokenExpiresIn();
        loginSessionCache.save(loginSession, Duration.ofSeconds(accessTokenExpiresIn));

        return LoginResultDTO.builder()
                .accessToken(accessToken)
                .tokenType(TOKEN_TYPE)
                .expiresIn(accessTokenExpiresIn)
                .build();
    }

    public void logout() {
        LoginUser loginUser = SecurityUserHolder.getLoginUser();
        loginSessionCache.delete(loginUser.getJti());
    }
}
