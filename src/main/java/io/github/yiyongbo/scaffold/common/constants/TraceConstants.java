package io.github.yiyongbo.scaffold.common.constants;

/**
 * 链路追踪常量
 *
 * @author kidd
 * @since 2026/5/29 09:41
 */
public final class TraceConstants {

    /**
     * TraceId 在 MDC 中的 key
     */
    public static final String TRACE_ID = "traceId";

    /**
     * TraceId 请求头 / 响应头名称
     */
    public static final String TRACE_ID_HEADER = "X-Trace-Id";

    private TraceConstants() {
    }

}
