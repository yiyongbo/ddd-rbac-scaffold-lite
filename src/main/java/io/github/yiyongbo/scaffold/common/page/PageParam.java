package io.github.yiyongbo.scaffold.common.page;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * 分页参数
 *
 * @author kidd
 * @since 2026/5/29 18:04
 */
@Getter
@Setter
public class PageParam {

    @NotNull(message = "页码不能为空")
    @Min(value = 1, message = "页码不能小于1")
    @Schema(description = "页码，从1开始", example = "1")
    private Long pageNo = 1L;

    @NotNull(message = "每页条数不能为空")
    @Min(value = 1, message = "每页条数不能小于1")
    @Max(value = 100, message = "每页条数不能超过100")
    @Schema(description = "每页条数", example = "10")
    private Long pageSize = 10L;
}
