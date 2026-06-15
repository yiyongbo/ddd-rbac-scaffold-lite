package io.github.yiyongbo.scaffold.application.role.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 系统角色 DTO
 *
 * @author kidd
 * @since 2026/6/10 19:49
 */
@Data
public class RoleDTO {

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
    private Integer enabled;

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
    private LocalDateTime createAt;

    /**
     * 更新时间
     */
    private LocalDateTime updateAt;
}
