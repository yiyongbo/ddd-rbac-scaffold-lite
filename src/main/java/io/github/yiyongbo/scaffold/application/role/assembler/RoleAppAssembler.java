package io.github.yiyongbo.scaffold.application.role.assembler;


import io.github.yiyongbo.scaffold.application.role.command.RoleCreateCommand;
import io.github.yiyongbo.scaffold.application.role.command.RoleUpdateCommand;
import io.github.yiyongbo.scaffold.application.role.dto.RoleDTO;
import io.github.yiyongbo.scaffold.application.role.dto.RolePageDTO;
import io.github.yiyongbo.scaffold.application.role.query.RolePageQuery;
import io.github.yiyongbo.scaffold.common.enums.EnumUtils;
import io.github.yiyongbo.scaffold.common.enums.YesNoEnum;
import io.github.yiyongbo.scaffold.common.page.PageResult;
import io.github.yiyongbo.scaffold.domain.role.model.entity.RoleEntity;
import io.github.yiyongbo.scaffold.domain.role.repository.query.RolePageCondition;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

/**
 * 系统角色 Application 对象装配器
 *
 * @author kidd
 * @since 2026/6/10 20:12
 */
@Mapper(componentModel = "spring")
public interface RoleAppAssembler {


    @Mapping(target = "enabled", source = "enabled", qualifiedByName = "codeToYesNo")
    RoleEntity toEntity(RoleCreateCommand command);

    @Mapping(target = "enabled", source = "enabled", qualifiedByName = "codeToYesNo")
    RoleEntity toEntity(RoleUpdateCommand command);

    @Mapping(target = "enabled", source = "enabled", qualifiedByName = "yesNoToCode")
    RoleDTO toDTO(RoleEntity entity);

    RolePageCondition toPageCondition(RolePageQuery query);

    @Mapping(target = "enabled", source = "enabled", qualifiedByName = "yesNoToCode")
    RolePageDTO toPageDTO(RoleEntity entity);

    List<RolePageDTO> toPageDTOList(List<RoleEntity> entities);

    default PageResult<RolePageDTO> toDTOPageResult(PageResult<RoleEntity> pageResult) {
        return PageResult.of(
                toPageDTOList(pageResult.getRecords()),
                pageResult.getTotal(),
                pageResult.getPageNum(),
                pageResult.getPageSize()
        );
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
