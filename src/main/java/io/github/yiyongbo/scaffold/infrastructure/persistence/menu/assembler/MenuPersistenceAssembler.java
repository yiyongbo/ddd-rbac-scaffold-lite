package io.github.yiyongbo.scaffold.infrastructure.persistence.menu.assembler;

import io.github.yiyongbo.scaffold.common.enums.EnumUtils;
import io.github.yiyongbo.scaffold.common.enums.YesNoEnum;
import io.github.yiyongbo.scaffold.domain.menu.enums.MenuTypeEnum;
import io.github.yiyongbo.scaffold.domain.menu.model.entity.MenuEntity;
import io.github.yiyongbo.scaffold.infrastructure.persistence.menu.po.MenuPO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * 菜单持久化对象装配器
 *
 * @author Kidd
 * @since 2026/6/8 23:19
 */
@Mapper(componentModel = "spring")
public interface MenuPersistenceAssembler {


    /**
     * 菜单领域实体 转 菜单持久化对象
     */
    @Mapping(target = "menuType", source = "menuType", qualifiedByName = "menuTypeToCode")
    @Mapping(target = "visible", source = "visible", qualifiedByName = "yesNoToCode")
    @Mapping(target = "enabled", source = "enabled", qualifiedByName = "yesNoToCode")
    MenuPO toPO(MenuEntity entity);

    /**
     * 菜单持久化对象 转 菜单领域实体
     */
    @Mapping(target = "menuType", source = "menuType", qualifiedByName = "codeToMenuType")
    @Mapping(target = "visible", source = "visible", qualifiedByName = "codeToYesNo")
    @Mapping(target = "enabled", source = "enabled", qualifiedByName = "codeToYesNo")
    MenuEntity toEntity(MenuPO po);

    @Named("menuTypeToCode")
    default Integer menuTypeToCode(MenuTypeEnum menuType) {
        return menuType == null ? null : menuType.getCode();
    }

    @Named("codeToMenuType")
    default MenuTypeEnum codeToMenuType(Integer code) {
        return EnumUtils.getByCode(MenuTypeEnum.class, code);
    }

    @Named("yesNoToCode")
    default Integer yesNoToCode(YesNoEnum yesNo) {
        return yesNo == null ? null : yesNo.getCode();
    }

    @Named("codeToYesNo")
    default YesNoEnum codeToYesNo(Integer code) {
        return EnumUtils.getByCode(YesNoEnum.class, code);
    }

}