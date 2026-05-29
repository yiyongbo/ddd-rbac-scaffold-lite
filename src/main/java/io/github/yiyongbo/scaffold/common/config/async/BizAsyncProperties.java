package io.github.yiyongbo.scaffold.common.config.async;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 业务异步线程池配置属性
 *
 * @author kidd
 * @since 2026/5/29 16:20
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "app.async.biz")
public class BizAsyncProperties {

    /**
     * 核心线程数
     */
    private int corePoolSize = 4;

    /**
     * 最大线程数
     */
    private int maxPoolSize = 8;

    /**
     * 队列容量
     */
    private int queueCapacity = 500;

    /**
     * 线程空闲存活时间，单位：秒
     */
    private int keepAliveSeconds = 60;

    /**
     * 线程名前缀
     */
    private String threadNamePrefix = "biz-executor-";

    /**
     * 应用关闭时等待任务完成
     */
    private boolean waitForTasksToCompleteOnShutdown = true;

    /**
     * 应用关闭时最多等待时间，单位：秒
     */
    private int awaitTerminationSeconds = 30;

    /**
     * 拒绝策略
     */
    private String rejectedPolicy = "caller-runs";
}
