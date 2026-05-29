package io.github.yiyongbo.scaffold.common.page;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collections;
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

    /**
     * 数据列表
     */
    private List<T> records;

    /**
     * 总条数
     */
    private Long total;

    /**
     * 页码
     */
    private Long pageNo;

    /**
     * 每页条数
     */
    private Long pageSize;

    /**
     * 总页数
     */
    private Long pages;

    public static <T> PageResult<T> of(List<T> records, Long total, Long pageNo, Long pageSize) {
        long safeTotal = total == null ? 0L : total;
        long safePageNo = pageNo == null ? 1L : pageNo;
        long safePageSize = pageSize == null || pageSize <= 0 ? 10L : pageSize;

        return new PageResult<>(
                records == null ? Collections.emptyList() : records,
                safeTotal,
                safePageNo,
                safePageSize,
                calculatePages(safeTotal, safePageSize)
        );
    }

    public static <T> PageResult<T> empty(Long pageNo, Long pageSize) {
        return of(Collections.emptyList(), 0L, pageNo, pageSize);
    }

    private static Long calculatePages(Long total, Long pageSize) {
        if (total == null || total <= 0 || pageSize == null || pageSize <= 0) {
            return 0L;
        }

        return (total + pageSize - 1) / pageSize;
    }

}
