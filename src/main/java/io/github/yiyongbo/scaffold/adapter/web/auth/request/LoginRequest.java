package io.github.yiyongbo.scaffold.adapter.web.auth.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 登录请求
 *
 * @author kidd
 * @since 2026/6/16 22:48
 */
@Data
@Schema(description = "登录请求")
public class LoginRequest {

    @NotBlank(message = "用户名不能为空")
    @Size(max = 32, message = "用户名长度不能超过32")
    @Schema(description = "用户名", example = "admin")
    private String username;

    @NotBlank(message = "密码不能为空")
    @Size(max = 32, message = "密码长度不能超过32")
    @Schema(description = "密码", example = "123456")
    private String password;
}
