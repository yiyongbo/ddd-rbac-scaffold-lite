package io.github.yiyongbo.scaffold.application.menu.assembler;

import io.github.yiyongbo.scaffold.application.menu.command.MenuCreateCmd;
import io.github.yiyongbo.scaffold.application.menu.command.MenuUpdateCmd;
import io.github.yiyongbo.scaffold.common.enums.EnumUtils;
import io.github.yiyongbo.scaffold.common.enums.YesNoEnum;
import io.github.yiyongbo.scaffold.domain.menu.enums.MenuTypeEnum;
import io.github.yiyongbo.scaffold.domain.menu.model.entity.MenuEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * 系统菜单应用层装配器
 *
 * @author kidd
 * @since 2026/6/9 10:46
 */
@Mapper(componentModel = "spring")
public interface MenuAssembler {

    @Mapping(target = "menuType", source = "menuType", qualifiedByName = "codeToMenuType")
    @Mapping(target = "visible", source = "visible", qualifiedByName = "codeToYesNo")
    @Mapping(target = "enable", source = "enable", qualifiedByName = "codeToYesNo")
    MenuEntity toEntity(MenuCreateCmd cmd);

    @Mapping(target = "menuType", source = "menuType", qualifiedByName = "codeToMenuType")
    @Mapping(target = "visible", source = "visible", qualifiedByName = "codeToYesNo")
    @Mapping(target = "enable", source = "enable", qualifiedByName = "codeToYesNo")
    MenuEntity toEntity(MenuUpdateCmd cmd);

    @Named("codeToMenuType")
    default MenuTypeEnum codeToMenuType(Integer code) {
        return EnumUtils.getByCode(MenuTypeEnum.class, code);
    }

    @Named("codeToYesNo")
    default YesNoEnum codeToYesNo(Integer code) {
        return EnumUtils.getByCode(YesNoEnum.class, code);
    }

}
