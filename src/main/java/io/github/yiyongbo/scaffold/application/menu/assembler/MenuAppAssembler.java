package io.github.yiyongbo.scaffold.application.menu.assembler;

import io.github.yiyongbo.scaffold.application.menu.command.MenuCreateCmd;
import io.github.yiyongbo.scaffold.application.menu.command.MenuUpdateCmd;
import io.github.yiyongbo.scaffold.application.menu.dto.MenuDTO;
import io.github.yiyongbo.scaffold.application.menu.dto.MenuTreeDTO;
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
public interface MenuAppAssembler {

    @Mapping(target = "menuType", source = "menuType", qualifiedByName = "codeToMenuType")
    @Mapping(target = "visible", source = "visible", qualifiedByName = "codeToYesNo")
    @Mapping(target = "enabled", source = "enabled", qualifiedByName = "codeToYesNo")
    MenuEntity toEntity(MenuCreateCmd cmd);

    @Mapping(target = "menuType", source = "menuType", qualifiedByName = "codeToMenuType")
    @Mapping(target = "visible", source = "visible", qualifiedByName = "codeToYesNo")
    @Mapping(target = "enabled", source = "enabled", qualifiedByName = "codeToYesNo")
    MenuEntity toEntity(MenuUpdateCmd cmd);

    @Mapping(target = "menuType", source = "menuType", qualifiedByName = "menuTypeToCode")
    @Mapping(target = "menuTypeDesc", source = "menuType", qualifiedByName = "menuTypeToDesc")
    @Mapping(target = "visible", source = "visible", qualifiedByName = "yesNoToCode")
    @Mapping(target = "enabled", source = "enabled", qualifiedByName = "yesNoToCode")
    MenuDTO toDTO(MenuEntity entity);

    @Mapping(target = "menuType", source = "menuType", qualifiedByName = "menuTypeToCode")
    @Mapping(target = "menuTypeDesc", source = "menuType", qualifiedByName = "menuTypeToDesc")
    @Mapping(target = "visible", source = "visible", qualifiedByName = "yesNoToCode")
    @Mapping(target = "enabled", source = "enabled", qualifiedByName = "yesNoToCode")
    MenuTreeDTO toTreeDTO(MenuEntity entity);

    @Named("codeToMenuType")
    default MenuTypeEnum codeToMenuType(Integer code) {
        return EnumUtils.getByCode(MenuTypeEnum.class, code);
    }

    @Named("codeToYesNo")
    default YesNoEnum codeToYesNo(Integer code) {
        return EnumUtils.getByCode(YesNoEnum.class, code);
    }

    @Named("menuTypeToCode")
    default Integer menuTypeToCode(MenuTypeEnum menuType) {
        return menuType == null ? null : menuType.getCode();
    }

    @Named("menuTypeToDesc")
    default String menuTypeToDesc(MenuTypeEnum menuType) {
        return menuType == null ? null : menuType.getDesc();
    }

    @Named("yesNoToCode")
    default Integer yesNoToCode(YesNoEnum yesNo) {
        return yesNo == null ? null : yesNo.getCode();
    }

}
