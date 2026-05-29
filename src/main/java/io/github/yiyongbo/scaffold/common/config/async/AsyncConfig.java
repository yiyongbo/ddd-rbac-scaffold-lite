package io.github.yiyongbo.scaffold.common.config.async;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.slf4j.MDC;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskDecorator;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 异步线程池配置
 *
 * @author kidd
 * @since 2026/5/29 16:05
 */
@Slf4j
@EnableAsync
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(BizAsyncProperties.class)
public class AsyncConfig implements AsyncConfigurer {

    public static final String BIZ_EXECUTOR = "bizExecutor";

    private final BizAsyncProperties properties;

    @Bean(BIZ_EXECUTOR)
    public ThreadPoolTaskExecutor bizExecutor(TaskDecorator taskDecorator) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        executor.setThreadNamePrefix(properties.getThreadNamePrefix());
        executor.setCorePoolSize(properties.getCorePoolSize());
        executor.setMaxPoolSize(properties.getMaxPoolSize());
        executor.setQueueCapacity(properties.getQueueCapacity());
        executor.setKeepAliveSeconds(properties.getKeepAliveSeconds());

        executor.setTaskDecorator(taskDecorator);
        executor.setRejectedExecutionHandler(resolveRejectedExecutionHandler(properties.getRejectedPolicy()));

        executor.setWaitForTasksToCompleteOnShutdown(properties.isWaitForTasksToCompleteOnShutdown());
        executor.setAwaitTerminationSeconds(properties.getAwaitTerminationSeconds());

        executor.initialize();
        return executor;

    }

    /**
     * 默认 @Async 使用的线程池
     */
    @Override
    public Executor getAsyncExecutor() {
        return bizExecutor(mdcTaskDecorator());
    }

    /**
     * 处理 @Async void 方法中的未捕获异常
     */
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new GlobalAsyncExceptionHandler();
    }

    /**
     * MDC 上下文传递装饰器，用于异步线程传递 traceId
     */
    @Bean
    public TaskDecorator mdcTaskDecorator() {
        return runnable -> {
            var contextMap = MDC.getCopyOfContextMap();

            return () -> {
                if (contextMap != null) {
                    MDC.setContextMap(contextMap);
                }

                try {
                    runnable.run();
                } finally {
                    MDC.clear();
                }
            };
        };
    }

    private RejectedExecutionHandler resolveRejectedExecutionHandler(String rejectedPolicy) {
        if ("abort".equalsIgnoreCase(rejectedPolicy)) {
            return new ThreadPoolExecutor.AbortPolicy();
        }

        if ("discard".equalsIgnoreCase(rejectedPolicy)) {
            return new ThreadPoolExecutor.DiscardPolicy();
        }

        if ("discard-oldest".equalsIgnoreCase(rejectedPolicy)) {
            return new ThreadPoolExecutor.DiscardOldestPolicy();
        }

        if ("caller-runs".equalsIgnoreCase(rejectedPolicy)) {
            return new ThreadPoolExecutor.CallerRunsPolicy();
        }

        log.warn("未知线程池拒绝策略：{}，使用默认 CallerRunsPolicy", rejectedPolicy);
        return new ThreadPoolExecutor.CallerRunsPolicy();
    }

    /**
     * 全局异步异常处理器
     */
    private static class GlobalAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

        @Override
        public void handleUncaughtException(@NonNull Throwable ex, Method method, Object @NonNull ... params) {
            log.error("异步任务执行异常：method={}", method.getName(), ex);
        }
    }
}
