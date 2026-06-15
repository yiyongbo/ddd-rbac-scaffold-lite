package io.github.yiyongbo.scaffold.common.page;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分页结果
 *
 * @param <T> 数据类型
 * @author kidd
 * @since 2026/5/29 21:52
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PageResult<T> {

    @Schema(description = "数据列表")
    private List<T> records;

    @Schema(description = "总条数", example = "100")
    private Long total;

    @Schema(description = "总页数", example = "10")
    private Long pages;

    @Schema(description = "当前页码", example = "1")
    private Long pageNum;

    @Schema(description = "每页条数", example = "10")
    private Long pageSize;


    public static <T> PageResult<T> of(List<T> records, Long total, Long pageNum, Long pageSize) {
        long safeTotal = total == null ? 0L : total;
        long safePageNum = pageNum == null ? 1L : pageNum;
        long safePageSize = pageSize == null || pageSize <= 0 ? 10L : pageSize;

        return new PageResult<>(
                records == null ? List.of() : records,
                safeTotal,
                safePageNum,
                safePageSize,
                calculatePages(safeTotal, safePageSize)
        );
    }

    public static <T> PageResult<T> empty(Long pageNum, Long pageSize) {
        return of(List.of(), 0L, pageNum, pageSize);
    }

    private static Long calculatePages(Long total, Long pageSize) {
        if (total == null || total <= 0 || pageSize == null || pageSize <= 0) {
            return 0L;
        }

        return (total + pageSize - 1) / pageSize;
    }

}
