package io.github.yiyongbo.scaffold.domain.user.gateway;

/**
 * 密码服务
 *
 * @author kidd
 * @since 2026/6/15 16:06
 */
public interface PasswordService {

    String encode(String rawPassword);

    boolean matches(String rawPassword, String encodedPassword);
}
