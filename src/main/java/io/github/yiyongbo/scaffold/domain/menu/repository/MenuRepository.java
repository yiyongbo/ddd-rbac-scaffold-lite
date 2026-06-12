package io.github.yiyongbo.scaffold.domain.menu.repository;

import io.github.yiyongbo.scaffold.domain.menu.model.entity.MenuEntity;

import java.util.List;
import java.util.Optional;

/**
 * 系统菜单仓储
 *
 * @author kidd
 * @since 2026/6/8 23:27
 */
public interface MenuRepository {

    /**
     * 保存菜单
     *
     * @param menu 菜单信息
     * @return 菜单ID
     */
    Long save(MenuEntity menu);

    /**
     * 根据ID更新菜单
     *
     * @param menu 菜单信息
     */
    void update(MenuEntity menu);

    /**
     * 根据ID删除菜单
     *
     * @param id 菜单ID
     */
    void delete(Long id);

    /**
     * 根据ID查询菜单
     *
      * @param id 菜单ID
     * @return 菜单信息
     */
    Optional<MenuEntity> findById(Long id);

    /**
     * 根据权限标识查询菜单
     *
     * @param permissionCode 权限标识
     * @return 菜单信息
     */
    Optional<MenuEntity> findByPermissionCode(String permissionCode);

    /**
     * 查询全部菜单
     *
     * @return 菜单信息
     */
    List<MenuEntity> findAll();

    /**
     * 判断菜单是否存在
     *
     * @param id 菜单ID
     * @return 是否存在
     */
    boolean existsById(Long id);

    /**
     * 判断是否存在子菜单
     *
     * @param parentId 父级菜单ID
     * @return 是否存在
     */
    boolean existsByParentId(Long parentId);

    /**
     * 判断菜单ID列表是否全部存在
     *
     * @param menuIds 菜单ID列表
     * @return 是否全部存在
     */
    boolean existsAllByIds(List<Long> menuIds);
}
