package io.github.yiyongbo.scaffold.domain.role.model.entity;

import io.github.yiyongbo.scaffold.common.enums.YesNoEnum;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 系统角色领域实体
 *
 * @author kidd
 * @since 2026/6/10 20:08
 */
@Data
public class RoleEntity {

    /**
     * 角色ID
     */
    private Long id;

    /**
     * 角色编码
     */
    private String roleCode;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 是否已启用：1是，0否
     */
    private YesNoEnum enabled;

    /**
     * 排序值，值越小越靠前
     */
    private Integer sort;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;
}
