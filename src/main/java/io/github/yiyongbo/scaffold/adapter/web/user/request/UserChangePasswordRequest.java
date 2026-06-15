package io.github.yiyongbo.scaffold.adapter.web.user.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 修改系统用户密码请求
 *
 * @author kidd
 * @since 2026/6/15 15:39
 */
@Data
public class UserChangePasswordRequest {

    @NotBlank(message = "旧密码不能为空")
    @Size(max = 32, message = "密码长度不能超过32")
    @Schema(description = "旧密码")
    private String oldPassword;

    @NotBlank(message = "新密码不能为空")
    @Size(max = 32, message = "密码长度不能超过32")
    @Schema(description = "新密码")
    private String newPassword;

    @NotBlank(message = "确认密码不能为空")
    @Size(max = 32, message = "密码长度不能超过32")
    @Schema(description = "确认密码")
    private String confirmPassword;
}
