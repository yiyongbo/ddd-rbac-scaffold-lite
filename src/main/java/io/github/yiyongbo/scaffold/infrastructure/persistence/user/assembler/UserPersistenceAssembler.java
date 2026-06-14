package io.github.yiyongbo.scaffold.infrastructure.persistence.user.assembler;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.yiyongbo.scaffold.common.enums.EnumUtils;
import io.github.yiyongbo.scaffold.common.enums.YesNoEnum;
import io.github.yiyongbo.scaffold.common.page.PageResult;
import io.github.yiyongbo.scaffold.domain.user.model.entity.UserEntity;
import io.github.yiyongbo.scaffold.infrastructure.persistence.user.po.UserPO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

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

    /**
     * 用户持久化对象列表 转 用户领域实体列表
     */
    List<UserEntity> toEntityList(List<UserPO> userPOList);

    /**
     * 用户持久化对象分页 转 用户领域实体分页
     */
    default PageResult<UserEntity> toEntityPageResult(Page<UserPO> resultPage) {
        return PageResult.of(
                toEntityList(resultPage.getRecords()),
                resultPage.getTotal(),
                resultPage.getCurrent(),
                resultPage.getSize()
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
