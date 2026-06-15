package io.github.yiyongbo.scaffold.adapter.web.user.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 * 分配用户角色请求
 *
 * @author kidd
 * @since 2026/6/14 18:30
 */
@Data
@Schema(description = "分配用户角色请求")
public class UserAssignRolesRequest {

    @NotNull(message = "角色ID列表不能为空")
    @Schema(description = "角色ID列表", example = "[1, 2, 3]")
    private List<Long> roleIds;
}
