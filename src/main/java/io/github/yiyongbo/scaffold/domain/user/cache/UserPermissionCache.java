package io.github.yiyongbo.scaffold.domain.user.cache;

import java.time.Duration;
import java.util.Collection;
import java.util.List;

/**
 * 用户权限 Cache
 *
 * @author kidd
 * @since 2026/7/8 10:05
 */
public interface UserPermissionCache {

    /**
     * 获取用户权限码列表。
     *
     * @param userId 用户ID
     * @return 权限码列表；缓存不存在时返回 null
     */
    List<String> get(Long userId);

    /**
     * 保存用户权限码列表。
     *
     * @param userId      用户ID
     * @param permissions 权限码列表
     * @param ttl         缓存时间
     */
    void save(Long userId, Collection<String> permissions, Duration ttl);

    /**
     * 删除用户权限缓存。
     *
     * @param userId 用户ID
     */
    void delete(Long userId);

    /**
     * 批量删除用户权限缓存。
     *
     * @param userIds 用户ID集合
     */
    void deleteBatch(Collection<Long> userIds);
}
