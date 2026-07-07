package io.github.yiyongbo.scaffold.infrastructure.persistence.menu.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 认证权限 Mapper
 *
 * @author kidd
 * @since 2026/7/7 10:53
 */
public interface AuthPermissionMapper {

    /**
     * 根据用户ID查询权限码列表
     *
     * @param userId 用户ID
     * @return 权限码列表
     */
    List<String> selectPermissionCodesByUserId(@Param("userId") Long userId);
}
