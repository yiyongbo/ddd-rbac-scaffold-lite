package io.github.yiyongbo.scaffold.infrastructure.persistence.user.assembler;

import io.github.yiyongbo.scaffold.common.enums.EnumUtils;
import io.github.yiyongbo.scaffold.common.enums.YesNoEnum;
import io.github.yiyongbo.scaffold.domain.user.model.entity.UserEntity;
import io.github.yiyongbo.scaffold.infrastructure.persistence.user.po.UserPO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * 系统用户持久化对象装配器
 *
 * @author kidd
 * @since 2026/6/14 17:31
 */
@Mapper(componentModel = "spring")
public interface UserPersistenceAssembler {

    /**
     * 用户领域实体 转 用户持久化对象
     */
    @Mapping(target = "enabled", source = "enabled", qualifiedByName = "yesNoToCode")
    UserPO toPO(UserEntity user);

    /**
     * 用户持久化对象 转 用户领域实体
     */
    @Mapping(target = "enabled", source = "enabled", qualifiedByName = "codeToYesNo")
    UserEntity toEntity(UserPO userPO);

    @Named("yesNoToCode")
    default Integer yesNoToCode(YesNoEnum yesNo) {
        return yesNo == null ? null : yesNo.getCode();
    }

    @Named("codeToYesNo")
    default YesNoEnum codeToYesNo(Integer code) {
        return EnumUtils.getByCode(YesNoEnum.class, code);
    }
}
