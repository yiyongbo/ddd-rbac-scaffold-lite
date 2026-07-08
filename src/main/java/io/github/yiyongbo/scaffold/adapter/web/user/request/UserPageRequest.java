package io.github.yiyongbo.scaffold.adapter.web.user.request;

import io.github.yiyongbo.scaffold.common.page.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户分页请求
 *
 * @author kidd
 * @since 2026/6/14 22:33
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "用户分页请求")
public class UserPageRequest extends PageParam {

    @Size(max = 32, message = "用户名长度不能超过32")
    @Schema(description = "用户名")
    private String username;
}
