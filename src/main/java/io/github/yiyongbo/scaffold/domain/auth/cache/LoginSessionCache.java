package io.github.yiyongbo.scaffold.domain.auth.cache;

import io.github.yiyongbo.scaffold.domain.auth.model.valueobject.LoginSessionValueObject;

import java.time.Duration;

/**
 * 登录会话 Cache
 *
 * @author kidd
 * @since 2026/7/7 16:33
 */
public interface LoginSessionCache {

    /**
     * 保存登录会话
     *
     * @param session 登录会话
     * @param ttl     有效期
     */
    void save(LoginSessionValueObject session, Duration ttl);

    /**
     * 判断登录会话是否存在
     *
     * @param jti jti
     * @return 是否存在
     */
    boolean exists(String jti);

    /**
     * 获取登录会话
     *
     * @param jti jti
     * @return 登录会话，不存在返回 null
     */
    LoginSessionValueObject get(String jti);

    /**
     * 删除登录会话
     *
     * @param jti jti
     */
    void delete(String jti);

    /**
     * 刷新登录会话有效期
     *
     * @param jti jti
     * @param ttl 有效期
     */
    void refreshExpire(String jti, Duration ttl);
}
