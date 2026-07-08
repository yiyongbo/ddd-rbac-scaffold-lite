package io.github.yiyongbo.scaffold.infrastructure.persistence.role.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.yiyongbo.scaffold.infrastructure.persistence.role.po.RolePO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统角色 Mapper
 *
 * @author kidd
 * @since 2026/6/9 22:10
 */
public interface RoleMapper extends BaseMapper<RolePO> {

    /**
     * 根据菜单ID查询用户ID列表
     *
     * @param menuId 菜单ID
     * @return 用户ID列表
     */
    List<Long> listUserIdsByMenuId(@Param("menuId") Long menuId);
}
