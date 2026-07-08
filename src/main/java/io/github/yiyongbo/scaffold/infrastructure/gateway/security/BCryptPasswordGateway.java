package io.github.yiyongbo.scaffold.infrastructure.gateway.security;

import io.github.yiyongbo.scaffold.domain.user.gateway.PasswordGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * BCrypt 密码服务实现
 *
 * @author kidd
 * @since 2026/6/15 16:07
 */
@Component
@RequiredArgsConstructor
public class BCryptPasswordGateway implements PasswordGateway {

    private final PasswordEncoder passwordEncoder;

    @Override
    public String encode(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
