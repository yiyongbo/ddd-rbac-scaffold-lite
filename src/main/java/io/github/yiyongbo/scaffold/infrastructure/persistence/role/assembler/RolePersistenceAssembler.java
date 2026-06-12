package io.github.yiyongbo.scaffold.infrastructure.persistence.role.assembler;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.yiyongbo.scaffold.common.enums.EnumUtils;
import io.github.yiyongbo.scaffold.common.enums.YesNoEnum;
import io.github.yiyongbo.scaffold.common.page.PageResult;
import io.github.yiyongbo.scaffold.domain.role.model.entity.RoleEntity;
import io.github.yiyongbo.scaffold.infrastructure.persistence.role.po.RolePO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

/**
 * 系统角色持久化对象装配器
 *
 * @author kidd
 * @since 2026/6/10 21:00
 */
@Mapper(componentModel = "spring")
public interface RolePersistenceAssembler {

    /**
     * 角色领域实体 转 角色持久化对象
     */
    @Mapping(target = "enabled", source = "enabled", qualifiedByName = "yesNoToCode")
    RolePO toPO(RoleEntity entity);

    /**
     * 角色持久化对象 转 角色领域实体
     */
    @Mapping(target = "enabled", source = "enabled", qualifiedByName = "codeToYesNo")
    RoleEntity toEntity(RolePO po);

    /**
     * 角色持久化对象列表 转 角色领域实体列表
     */
    List<RoleEntity> toEntityList(List<RolePO> poList);

    /**
     * 角色持久化分页对象 转 角色领域分页结果
     */
    default PageResult<RoleEntity> toEntityPageResult(Page<RolePO> page) {
        return PageResult.of(
                toEntityList(page.getRecords()),
                page.getTotal(),
                page.getCurrent(),
                page.getSize()
        );
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
