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

    /**
     * 创建角色命令 转 角色领域实体
     */
    @Mapping(target = "enabled", source = "enabled", qualifiedByName = "codeToYesNo")
    RoleEntity toEntity(RoleCreateCommand command);

    /**
     * 更新角色命令 转 角色领域实体
     */
    @Mapping(target = "enabled", source = "enabled", qualifiedByName = "codeToYesNo")
    RoleEntity toEntity(RoleUpdateCommand command);

    /**
     * 角色领域实体 转 角色数据传输对象
     */
    @Mapping(target = "enabled", source = "enabled", qualifiedByName = "yesNoToCode")
    RoleDTO toDTO(RoleEntity entity);

    /**
     * 角色领域实体 转 角色分页数据传输对象
     */
    RolePageCondition toPageCondition(RolePageQuery query);

    /**
     * 角色领域实体列表 转 角色分页 DTO 列表
     */
    @Mapping(target = "enabled", source = "enabled", qualifiedByName = "yesNoToCode")
    RolePageDTO toPageDTO(RoleEntity entity);

    /**
     * 角色领域实体列表 转 角色分页 DTO 列表
     */
    List<RolePageDTO> toPageDTOList(List<RoleEntity> entities);

    /**
     * 角色领域分页结果 转 角色分页 DTO 分页结果
     */
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
