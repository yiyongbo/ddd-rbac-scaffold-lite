package io.github.yiyongbo.scaffold.adapter.web.user.request;

import io.github.yiyongbo.scaffold.common.enums.YesNoEnum;
import io.github.yiyongbo.scaffold.common.validation.EnumValue;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 创建系统用户请求
 *
 * @author kidd
 * @since 2026/6/14 18:08
 */
@Data
@Schema(description = "创建系统用户请求")
public class UserCreateRequest {

    @NotBlank(message = "用户名不能为空")
    @Size(max = 32, message = "用户名长度不能超过32")
    @Schema(description = "用户名")
    private String username;

    @Size(max = 64, message = "用户昵称长度不能超过64")
    @Schema(description = "用户昵称")
    private String nickname;

    @NotBlank(message = "邮箱不能为空")
    @Size(max = 64, message = "邮箱长度不能超过64")
    @Schema(description = "邮箱")
    private String email;

    @NotBlank(message = "手机号不能为空")
    @Size(max = 11, message = "手机号长度不能超过11")
    @Schema(description = "手机号")
    private String phone;

    @Size(max = 255, message = "头像长度不能超过255")
    @Schema(description = "头像")
    private String avatar;

    @NotNull(message = "是否已启用不能为空")
    @EnumValue(enumClass = YesNoEnum.class, message = "是否已启用不合法")
    @Schema(description = "是否已启用：1是，0否")
    private Integer enabled;
}
