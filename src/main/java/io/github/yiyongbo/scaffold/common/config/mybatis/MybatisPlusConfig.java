package io.github.yiyongbo.scaffold.common.config.mybatis;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis-Plus 配置
 *
 * @author kidd
 * @since 2026/5/29 16:57
 */
@Configuration
@MapperScan("io.github.yiyongbo.scaffold.infrastructure.persistence.**.mapper")
public class MybatisPlusConfig {

    /**
     * Mybatis-Plus 插件配置
     * <p>
     * 使用多个插件时，需要注意它们的顺序。建议的顺序是：
     * 1. 多租户、动态表名
     * 2. 分页、乐观锁
     * 3. SQL 性能规范、防止全表更新与删除
     * 总结：对 SQL 进行单次改造的插件应优先放入，不对 SQL 进行改造的插件最后放入。
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();

        // 乐观锁插件
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());

        // 分页插件
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor(DbType.MYSQL);
        paginationInnerInterceptor.setMaxLimit(500L);
        paginationInnerInterceptor.setOverflow(false);
        interceptor.addInnerInterceptor(paginationInnerInterceptor);

        // 防止全表 update/delete
        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());

        return interceptor;

    }
}
