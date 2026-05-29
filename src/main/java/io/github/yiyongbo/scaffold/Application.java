package io.github.yiyongbo.scaffold;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 启动类
 *
 * @author kidd
 * @since 2026/05/28 16:18
 */
// TODO 暂时排除数据源自动配置，后续在配置数据源
@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class
})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
