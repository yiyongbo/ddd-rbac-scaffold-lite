package io.github.yiyongbo.scaffold.infrastructure.gateway;

import io.github.yiyongbo.scaffold.domain.user.gateway.PasswordService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * BCrypt 密码服务实现
 *
 * @author kidd
 * @since 2026/6/15 16:07
 */
@Service
public class BCryptPasswordService implements PasswordService {

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public String encode(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
