package io.github.yiyongbo.scaffold.domain.role.repository;

import io.github.yiyongbo.scaffold.common.page.PageResult;
import io.github.yiyongbo.scaffold.domain.role.model.entity.RoleEntity;
import io.github.yiyongbo.scaffold.domain.role.repository.query.RolePageCondition;

import java.util.List;
import java.util.Optional;

/**
 * 系统角色仓储
 *
 * @author kidd
 * @since 2026/6/10 20:10
 */
public interface RoleRepository {

    /**
     * 保存角色
     *
     * @param role 角色信息
     * @return 角色ID
     */
    Long save(RoleEntity role);

    /**
     * 根据角色ID更新角色
     *
     * @param role 角色信息
     */
    void updateById(RoleEntity role);

    /**
     * 根据角色ID更新角色启用状态
     *
     * @param id   角色ID
     * @param code 启用状态码
     */
    void updateEnabledById(Long id, Integer code);

    /**
     * 根据角色ID删除角色
     *
     * @param id 角色ID
     */
    void deleteById(Long id);

    /**
     * 根据角色标识查询角色
     *
     * @param roleCode 角色标识
     * @return 角色信息
     */
    Optional<RoleEntity> findByRoleCode(String roleCode);

    /**
     * 校验角色ID是否存在
     *
     * @param id 角色ID
     * @return 是否存在
     */
    boolean existsById(Long id);

    /**
     * 根据角色ID查询角色
     *
     * @param id 角色ID
     * @return 角色信息
     */
    Optional<RoleEntity> findById(Long id);

    /**
     * 分页查询角色
     *
     * @param condition 查询条件
     * @return 角色信息
     */
    PageResult<RoleEntity> page(RolePageCondition condition);

    /**
     * 根据角色ID查询菜单ID列表
     *
     * @param roleId 角色ID
     * @return 菜单ID列表
     */
    List<Long> listMenuIdsByRoleId(Long roleId);

    /**
     * 替换角色关联的菜单
     *
     * @param roleId  角色ID
     * @param menuIds 菜单ID列表
     */
    void replaceRoleMenus(Long roleId, List<Long> menuIds);

    /**
     * 校验角色ID列表是否存在
     *
     * @param roleIds 角色ID列表
     * @return 角色是否全部存在
     */
    boolean existsAllByIds(List<Long> roleIds);

    /**
     * 根据菜单ID删除角色菜单权限
     *
     * @param menuId 菜单ID
     */
    void deleteRoleMenuByMenuId(Long menuId);

    /**
     * 根据角色ID删除角色菜单权限
     *
     * @param roleId 角色ID
     */
    void deleteRoleMenuByRoleId(Long roleId);

    /**
     * 根据菜单ID查询用户ID列表
     *
     * @param menuId 菜单ID
     * @return 用户ID列表
     */
    List<Long> listUserIdsByMenuId(Long menuId);
}
