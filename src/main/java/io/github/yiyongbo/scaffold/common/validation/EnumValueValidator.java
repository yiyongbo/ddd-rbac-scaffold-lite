package io.github.yiyongbo.scaffold.common.validation;

import io.github.yiyongbo.scaffold.common.enums.BaseEnum;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;

/**
 * 枚举值校验器
 *
 * @author kidd
 * @since 2026/6/9 15:28
 */
public class EnumValueValidator implements ConstraintValidator<EnumValue, Object> {

    private Class<? extends BaseEnum<?>> enumClass;

    private boolean allowNull;

    @Override
    public void initialize(EnumValue constraintAnnotation) {
        this.enumClass = constraintAnnotation.enumClass();
        this.allowNull = constraintAnnotation.allowNull();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) {
            return allowNull;
        }

        BaseEnum<?>[] enumConstants = enumClass.getEnumConstants();
        if (enumConstants == null) {
            return false;
        }

        for (BaseEnum<?> enumConstant : enumConstants) {
            if (Objects.equals(enumConstant.getCode(), value)) {
                return true;
            }
        }

        return false;
    }
}
