package io.github.yiyongbo.scaffold.adapter.web.role.request;

import io.github.yiyongbo.scaffold.common.enums.YesNoEnum;
import io.github.yiyongbo.scaffold.common.validation.EnumValue;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 创建系统角色请求
 *
 * @author kidd
 * @since 2026/6/9 22:22
 */
@Data
@Schema(description = "创建系统角色请求")
public class RoleCreateRequest {

    @NotBlank(message = "角色编码不能为空")
    @Size(max = 64, message = "角色编码长度不能超过64")
    @Schema(description = "角色编码", example = "SYS_ADMIN")
    private String roleCode;

    @NotBlank(message = "角色名称不能为空")
    @Size(max = 64, message = "角色名称长度不能超过64")
    @Schema(description = "角色名称", example = "系统管理员")
    private String roleName;

    @NotNull(message = "是否已启用不能为空")
    @EnumValue(enumClass = YesNoEnum.class, message = "是否已启用不合法")
    @Schema(description = "是否已启用：1是，0否", example = "1")
    private Integer enabled;

    @NotNull(message = "排序值不能为空")
    @Min(value = 0, message = "排序值不能小于0")
    @Schema(description = "排序值，值越小越靠前", example = "1")
    private Integer sort;

    @Size(max = 255, message = "备注长度不能超过255")
    @Schema(description = "备注", example = "系统管理员")
    private String remark;
}
