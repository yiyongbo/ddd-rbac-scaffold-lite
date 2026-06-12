package io.github.yiyongbo.scaffold.infrastructure.persistence.role.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import io.github.yiyongbo.scaffold.infrastructure.persistence.role.po.RoleMenuPO;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 系统角色菜单关联 Mapper
 *
 * @author kidd
 * @since 2026/6/11 22:00
 */
public interface RoleMenuMapper extends BaseMapper<RoleMenuPO> {

    /**
     * 批量保存角色菜单权限
     *
     * @param roleId 角色ID
     * @param menuIds 菜单ID列表
     */
    void insertBatch(@Param("roleId") Long roleId, @Param("menuIds") List<Long> menuIds, @Param("createAt") LocalDateTime createAt);
}
