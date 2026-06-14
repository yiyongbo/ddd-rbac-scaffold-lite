package io.github.yiyongbo.scaffold.application.user.assembler;

import io.github.yiyongbo.scaffold.application.user.command.UserCreateCommand;
import io.github.yiyongbo.scaffold.application.user.command.UserUpdateCommand;
import io.github.yiyongbo.scaffold.common.enums.EnumUtils;
import io.github.yiyongbo.scaffold.common.enums.YesNoEnum;
import io.github.yiyongbo.scaffold.domain.user.model.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * 系统用户 Application 对象装配器
 *
 * @author kidd
 * @since 2026/6/14 18:34
 */
@Mapper(componentModel = "spring")
public interface UserAppAssembler {

    /**
     * 创建系统用户命令 转 系统用户领域实体
     */
    @Mapping(target = "enabled", source = "enabled", qualifiedByName = "codeToYesNo")
    UserEntity toEntity(UserCreateCommand command);

    /**
     * 更新系统用户命令 转 系统用户领域实体
     */
    @Mapping(target = "enabled", source = "enabled", qualifiedByName = "codeToYesNo")
    UserEntity toEntity(UserUpdateCommand command);

    @Named("codeToYesNo")
    default YesNoEnum codeToYesNo(Integer code) {
        return EnumUtils.getByCode(YesNoEnum.class, code);
    }
}
