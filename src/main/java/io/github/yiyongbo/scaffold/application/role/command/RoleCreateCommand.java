package io.github.yiyongbo.scaffold.application.role.command;

import lombok.Data;

/**
 * 系统角色创建命令
 *
 * @author kidd
 * @since 2026/6/10 19:46
 */
@Data
public class RoleCreateCommand {

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
}
