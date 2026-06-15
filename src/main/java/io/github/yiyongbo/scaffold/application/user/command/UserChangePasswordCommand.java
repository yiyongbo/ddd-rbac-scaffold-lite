package io.github.yiyongbo.scaffold.application.user.command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 修改系统用户密码命令
 *
 * @author kidd
 * @since 2026/6/15 15:41
 */
@Data
public class UserChangePasswordCommand {

    /**
     * 用户ID
     */
    private Long id;

    /**
     * 旧密码
     */
    @Schema(description = "旧密码")
    private String oldPassword;

    /**
     * 新密码
     */
    @Schema(description = "新密码")
    private String newPassword;

    /**
     * 确认密码
     */
    @Schema(description = "确认密码")
    private String confirmPassword;
}
