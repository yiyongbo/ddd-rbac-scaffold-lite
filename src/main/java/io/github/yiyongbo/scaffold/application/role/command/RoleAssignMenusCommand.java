package io.github.yiyongbo.scaffold.application.role.command;

import lombok.Data;

import java.util.List;

/**
 * 角色菜单权限分配命令
 *
 * @author kidd
 * @since 2026/06/11 23:30
 */
@Data
public class RoleAssignMenusCommand {

    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 菜单ID列表
     */
    private List<Long> menuIds;

}
