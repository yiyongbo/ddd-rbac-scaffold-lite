package io.github.yiyongbo.scaffold.application.menu.assembler;

import io.github.yiyongbo.scaffold.application.menu.command.MenuCreateCommand;
import io.github.yiyongbo.scaffold.application.menu.command.MenuUpdateCommand;
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
 * 系统菜单 Application 对象装配器
 *
 * @author kidd
 * @since 2026/6/9 10:46
 */
@Mapper(componentModel = "spring")
public interface MenuAppAssembler {

    /**
     * 创建菜单请求 转 菜单领域实体
     */
    @Mapping(target = "menuType", source = "menuType", qualifiedByName = "codeToMenuType")
    @Mapping(target = "enabled", source = "enabled", qualifiedByName = "codeToYesNo")
    MenuEntity toEntity(MenuCreateCommand command);

    /**
     * 更新菜单请求 转 菜单领域实体
     */
    @Mapping(target = "menuType", source = "menuType", qualifiedByName = "codeToMenuType")
    @Mapping(target = "enabled", source = "enabled", qualifiedByName = "codeToYesNo")
    MenuEntity toEntity(MenuUpdateCommand command);

    /**
     * 菜单领域实体 转 菜单DTO
     */
    @Mapping(target = "menuType", source = "menuType", qualifiedByName = "menuTypeToCode")
    @Mapping(target = "menuTypeDesc", source = "menuType", qualifiedByName = "menuTypeToDesc")
    @Mapping(target = "enabled", source = "enabled", qualifiedByName = "yesNoToCode")
    MenuDTO toDTO(MenuEntity entity);

    /**
     * 菜单领域实体 转 菜单树DTO
     */
    @Mapping(target = "menuType", source = "menuType", qualifiedByName = "menuTypeToCode")
    @Mapping(target = "menuTypeDesc", source = "menuType", qualifiedByName = "menuTypeToDesc")
    @Mapping(target = "enabled", source = "enabled", qualifiedByName = "yesNoToCode")
    MenuTreeDTO toTreeDTO(MenuEntity entity);

    @Named("codeToMenuType")
    default MenuTypeEnum codeToMenuType(Integer code) {
        return EnumUtils.getByCode(MenuTypeEnum.class, code);
    }

    @Named("menuTypeToCode")
    default Integer menuTypeToCode(MenuTypeEnum menuType) {
        return menuType == null ? null : menuType.getCode();
    }

    @Named("menuTypeToDesc")
    default String menuTypeToDesc(MenuTypeEnum menuType) {
        return menuType == null ? null : menuType.getDesc();
    }

    @Named("codeToYesNo")
    default YesNoEnum codeToYesNo(Integer code) {
        return EnumUtils.getByCode(YesNoEnum.class, code);
    }

    @Named("yesNoToCode")
    default Integer yesNoToCode(YesNoEnum yesNo) {
        return yesNo == null ? null : yesNo.getCode();
    }

}
