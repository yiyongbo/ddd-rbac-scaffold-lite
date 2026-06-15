package io.github.yiyongbo.scaffold.adapter.web.role.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 系统角色响应
 *
 * @author kidd
 * @since 2026/6/9 22:30
 */
@Data
@Schema(description = "系统角色响应")
public class RoleResponse {

    @Schema(description = "角色ID", example = "1")
    private Long id;

    @Schema(description = "角色编码", example = "SYS_ADMIN")
    private String roleCode;

    @Schema(description = "角色名称", example = "系统管理员")
    private String roleName;

    @Schema(description = "是否已启用：1是，0否", example = "1")
    private Integer enabled;

    @Schema(description = "排序值，值越小越靠前", example = "1")
    private Integer sort;

    @Schema(description = "备注", example = "系统管理员")
    private String remark;

    @Schema(description = "创建时间")
    private LocalDateTime createdAt;

    @Schema(description = "更新时间")
    private LocalDateTime updatedAt;
}
