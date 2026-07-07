package io.github.yiyongbo.scaffold.infrastructure.gateway.security;

import io.github.yiyongbo.scaffold.common.security.LoginUser;
import io.github.yiyongbo.scaffold.domain.auth.model.valueobject.TokenPayloadValueObject;
import io.github.yiyongbo.scaffold.infrastructure.persistence.menu.mapper.AuthPermissionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 登录用户加载器
 *
 * @author kidd
 * @since 2026/7/7 10:52
 */
@Component
@RequiredArgsConstructor
public class LoginUserLoader {

    private final AuthPermissionMapper authPermissionMapper;

    public LoginUser load(TokenPayloadValueObject tokenPayload) {
        List<String> permissions = authPermissionMapper.selectPermissionCodesByUserId(tokenPayload.getUserId());

        return LoginUser.builder()
                .userId(tokenPayload.getUserId())
                .username(tokenPayload.getUsername())
                .permissions(permissions)
                .build();
    }
}
