package io.github.yiyongbo.scaffold.adapter.web.role.request;

import io.github.yiyongbo.scaffold.common.page.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统角色分页请求
 *
 * @author kidd
 * @since 2026/6/9 22:36
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "系统角色分页请求")
public class RolePageRequest extends PageParam {

    @Size(max = 64, message = "角色名称长度不能超过64")
    @Schema(description = "角色名称", example = "系统管理员")
    private String roleName;
}
