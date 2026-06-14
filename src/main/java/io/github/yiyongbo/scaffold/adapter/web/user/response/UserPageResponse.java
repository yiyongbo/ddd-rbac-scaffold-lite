package io.github.yiyongbo.scaffold.adapter.web.user.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户分页响应
 *
 * @author kidd
 * @since 2026/6/14 22:32
 */
@Data
public class UserPageResponse {

    @Schema(description = "用户ID")
    private Long id;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "用户昵称")
    private String nickname;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "是否已启用：1是，0否")
    private Integer enabled;

    @Schema(description = "创建时间")
    private LocalDateTime createdAt;
}
