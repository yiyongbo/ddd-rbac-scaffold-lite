package io.github.yiyongbo.scaffold.common.base;

import com.baomidou.mybatisplus.annotation.Version;

/**
 * 带乐观锁的持久化对象基类
 *
 * @author kidd
 * @since 2026/5/29 17:42
 */
public abstract class VersionBasePO extends BasePO {

    /**
     * 乐观锁版本号
     */
    @Version
    private Integer version;

}
