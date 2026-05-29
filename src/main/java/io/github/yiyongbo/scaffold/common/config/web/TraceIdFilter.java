package io.github.yiyongbo.scaffold.common.config.web;

import io.github.yiyongbo.scaffold.common.constants.TraceConstants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

/**
 * TraceId 过滤器
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class TraceIdFilter extends OncePerRequestFilter {

    private static final int MAX_TRACE_ID_LENGTH = 64;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String traceId = getOrCreateTraceId(request);

        try {
            MDC.put(TraceConstants.TRACE_ID, traceId);
            response.setHeader(TraceConstants.TRACE_ID_HEADER, traceId);
            filterChain.doFilter(request, response);
        } finally {
            MDC.remove(TraceConstants.TRACE_ID);
        }
    }

    private String getOrCreateTraceId(HttpServletRequest request) {
        String traceId = request.getHeader(TraceConstants.TRACE_ID_HEADER);
        if (StringUtils.hasText(traceId) && traceId.length() <= MAX_TRACE_ID_LENGTH) {
            return traceId;
        }
        return UUID.randomUUID().toString().replace("-", "");
    }
}