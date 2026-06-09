package io.github.yiyongbo.scaffold.common.validation;

import io.github.yiyongbo.scaffold.common.enums.BaseEnum;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * 枚举值校验注解
 */
@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnumValueValidator.class)
public @interface EnumValue {

    /**
     * 错误消息
     */
    String message() default "枚举值不合法";

    /**
     * 枚举类型
     */
    Class<? extends BaseEnum<?>> enumClass();

    /**
     * 是否允许为空
     */
    boolean allowNull() default true;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
