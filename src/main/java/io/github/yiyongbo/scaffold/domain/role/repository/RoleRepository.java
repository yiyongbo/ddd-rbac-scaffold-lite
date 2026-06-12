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
}
