package io.github.yiyongbo.scaffold.common.page;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

/**
 * 分页查询基类
 *
 * @author kidd
 * @since 2026/5/29 18:04
 */
@Getter
@Setter
public class PageQuery {

    /**
     * 页码
     */
    @Min(value = 1, message = "页码不能小于1")
    private Long pageNo = 1L;

    /**
     * 每页条数
     */
    @Min(value = 1, message = "每页条数不能小于1")
    @Max(value = 100, message = "每页条数不能大于100")
    private Long pageSize = 10L;

}
