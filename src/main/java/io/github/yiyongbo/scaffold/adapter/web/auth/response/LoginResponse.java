package io.github.yiyongbo.scaffold.adapter.web.auth.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 登录响应
 *
 * @author kidd
 * @since 2026/6/16 22:51
 */
@Data
@Schema(description = "登录响应")
public class LoginResponse {

    @Schema(description = "访问令牌")
    private String accessToken;

    @Schema(description = "令牌前缀", example = "Bearer")
    private String tokenPrefix;

    @Schema(description = "过期时间，单位秒", example = "7200")
    private Long expiresIn;
}
