package io.github.yiyongbo.scaffold.infrastructure.gateway.security;

import io.github.yiyongbo.scaffold.common.security.LoginUser;
import io.github.yiyongbo.scaffold.domain.auth.model.valueobject.TokenPayloadValueObject;
import io.github.yiyongbo.scaffold.infrastructure.cache.RedisUserPermissionCache;
import io.github.yiyongbo.scaffold.infrastructure.persistence.menu.mapper.AuthPermissionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
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
    private final RedisUserPermissionCache userPermissionCache;

    public LoginUser load(TokenPayloadValueObject tokenPayload) {
        List<String> userPermissions = userPermissionCache.get(tokenPayload.getUserId());

        if (userPermissions == null) {
            userPermissions = authPermissionMapper.selectPermissionCodesByUserId(tokenPayload.getUserId());
            userPermissionCache.save(tokenPayload.getUserId(), userPermissions, Duration.ofDays(1));
        }

        return LoginUser.builder()
                .jti(tokenPayload.getJti())
                .userId(tokenPayload.getUserId())
                .username(tokenPayload.getUsername())
                .permissions(userPermissions)
                .build();
    }
}
