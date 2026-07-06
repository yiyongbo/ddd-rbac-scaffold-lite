package io.github.yiyongbo.scaffold.common.config.mybatis;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import io.github.yiyongbo.scaffold.common.security.SecurityUserHolder;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * MyBatis-Plus 字段自动填充处理器
 *
 * @author kidd
 * @since 2026/5/29 17:03
 */
@Component
public class MybatisPlusMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        LocalDateTime now = LocalDateTime.now();

        strictInsertFill(metaObject, "createdAt", LocalDateTime.class, now);
        strictInsertFill(metaObject, "updatedAt", LocalDateTime.class, now);

        Long userId = SecurityUserHolder.getUserIdOrNull();
        if (userId != null) {
            strictInsertFill(metaObject, "createdBy", Long.class, userId);
            strictInsertFill(metaObject, "updatedBy", Long.class, userId);
        }

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        strictUpdateFill(metaObject, "updatedAt", LocalDateTime.class, LocalDateTime.now());

        Long userId = SecurityUserHolder.getUserIdOrNull();
        if (userId != null) {
            strictInsertFill(metaObject, "updatedBy", Long.class, userId);
        }
    }

}
