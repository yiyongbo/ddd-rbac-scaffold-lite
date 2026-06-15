package io.github.yiyongbo.scaffold.domain.user.repository;

import io.github.yiyongbo.scaffold.common.page.PageResult;
import io.github.yiyongbo.scaffold.domain.user.model.entity.UserEntity;
import io.github.yiyongbo.scaffold.domain.user.repository.query.UserPageCondition;

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
     * 根据用户名判断用户是否存在
     *
     * @param username 用户名
     * @return 用户是否存在
     */
    boolean existsByUsername(String username);

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

    /**
     * 根据ID查询用户
     *
     * @param id 用户ID
     * @return 用户领域实体
     */
    Optional<UserEntity> findById(Long id);

    /**
     * 分页查询用户
     *
     * @param condition 查询条件
     * @return 用户领域实体分页结果
     */
    PageResult<UserEntity> page(UserPageCondition condition);

    /**
     * 根据用户ID查询角色ID列表
     *
     * @param userId 用户ID
     * @return 角色ID列表
     */
    List<Long> listRoleIds(Long userId);

    /**
     * 根据角色ID删除用户角色
     *
     * @param roleId 角色ID
     */
    void deleteUserRoleByRoleId(Long roleId);

    /**
     * 根据用户ID删除用户角色
     *
     * @param userId 用户ID
     */
    void deleteUserRoleByUserId(Long userId);

    /**
     * 根据ID更新用户密码
     *
     * @param id 用户ID
      * @param changedPassword 更新后的密码
     */
    void updatePasswordById(Long id, String changedPassword);

    /**
     * 根据ID更新用户启用状态
     *
     * @param id 用户ID
     * @param enabled 启用状态
     */
    void updateEnabledById(Long id, Integer enabled);
}
