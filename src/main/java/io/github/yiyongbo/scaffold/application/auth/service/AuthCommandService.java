package io.github.yiyongbo.scaffold.application.auth.service;

import io.github.yiyongbo.scaffold.application.auth.command.LoginCommand;
import io.github.yiyongbo.scaffold.application.auth.dto.LoginResultDTO;
import io.github.yiyongbo.scaffold.common.exception.BizAssert;
import io.github.yiyongbo.scaffold.common.response.CommonResponseCode;
import io.github.yiyongbo.scaffold.domain.auth.model.valueobject.TokenPayloadValueObject;
import io.github.yiyongbo.scaffold.domain.common.gateway.TokenGateway;
import io.github.yiyongbo.scaffold.domain.common.gateway.PasswordGateway;
import io.github.yiyongbo.scaffold.domain.user.model.entity.UserEntity;
import io.github.yiyongbo.scaffold.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 认证命令服务
 *
 * @author kidd
 * @since 2026/6/16 22:59
 */
@Service
@RequiredArgsConstructor
public class AuthCommandService {

    private final UserRepository userRepository;

    private final PasswordGateway passwordGateway;
    private final TokenGateway tokenGateway;

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

        TokenPayloadValueObject tokenPayload = TokenPayloadValueObject.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .build();
        String accessToken = tokenGateway.generateAccessToken(tokenPayload);

        return LoginResultDTO.builder()
                .accessToken(accessToken)
                .tokenType("Bearer")
                .expiresIn(tokenGateway.getAccessTokenExpiresIn())
                .build();
    }

}
