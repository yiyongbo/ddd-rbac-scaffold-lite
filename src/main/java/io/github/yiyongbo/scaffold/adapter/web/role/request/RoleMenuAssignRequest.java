package io.github.yiyongbo.scaffold.adapter.web.role.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 * 角色菜单权限分配请求
 *
 * @author kidd
 * @since 2026/06/11 23:20
 */
@Data
@Schema(description = "角色菜单权限分配请求")
public class RoleMenuAssignRequest {

    @NotNull(message = "菜单ID列表不能为空")
    @Schema(description = "菜单ID列表，空数组表示清空角色菜单权限", example = "[1,2,3]")
    private List<@NotNull(message = "菜单ID不能为空") Long> menuIds;
}
