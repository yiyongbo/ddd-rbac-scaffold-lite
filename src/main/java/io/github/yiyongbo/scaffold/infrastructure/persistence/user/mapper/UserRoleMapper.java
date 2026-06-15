package io.github.yiyongbo.scaffold.infrastructure.persistence.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.yiyongbo.scaffold.infrastructure.persistence.user.po.UserRolePO;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户角色 Mapper
 *
 * @author kidd
 * @since 2026/6/14 17:30
 */
public interface UserRoleMapper extends BaseMapper<UserRolePO> {

    /**
     * 批量插入用户角色
     *
     * @param userId 用户ID
     * @param roleIdList 角色ID列表
     * @param createAt 创建时间
     */
    void insertBatch(@Param("userId") Long userId, @Param("roleIdList") List<Long> roleIdList, @Param("createAt") LocalDateTime createAt);
}
