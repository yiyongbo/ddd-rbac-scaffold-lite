package io.github.yiyongbo.scaffold.domain.user.repository;

import io.github.yiyongbo.scaffold.domain.user.model.entity.UserEntity;

import java.util.List;
import java.util.Optional;

/**
 * 系统用户仓储
 *
 * @author kidd
 * @since 2026/6/14 17:34
 */
public interface UserRepository {

    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return 用户领域实体
     */
    Optional<UserEntity> findByUsername(String username);

    /**
     * 保存用户
     *
     * @param user 用户领域实体
     * @return 用户ID
     */
    Long save(UserEntity user);

    /**
     * 根据ID判断用户是否存在
     * @param id 用户ID
     * @return 用户是否存在
     */
    boolean existsById(Long id);

    /**
     * 根据ID更新用户
     *
     * @param user 用户领域实体
     */
    void updateById(UserEntity user);

    /**
     * 根据ID删除用户
     *
     * @param id 用户ID
     */
    void deleteById(Long id);

    /**
     * 替换用户关联的角色
     *
     * @param userId 用户ID
     * @param roleIds 角色ID列表
     */
    void replaceUserRoles(Long userId, List<Long> roleIds);
}
