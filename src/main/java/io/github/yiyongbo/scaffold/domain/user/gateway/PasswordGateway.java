package io.github.yiyongbo.scaffold.domain.user.gateway;

/**
 * 密码 Gateway
 *
 * @author kidd
 * @since 2026/6/16 23:17
 */
public interface PasswordGateway {

    /**
     * 密码匹配
     *
     * @param rawPassword 明文密码
     * @param encodedPassword 加密后的密码
     * @return 匹配结果
     */
    boolean matches(String rawPassword, String encodedPassword);

    /**
     * 密码加密
     *
     * @param rawPassword 明文密码
     * @return 加密后的密码
     */
    String encode(String rawPassword);
}
