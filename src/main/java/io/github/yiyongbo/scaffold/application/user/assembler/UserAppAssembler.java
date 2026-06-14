package io.github.yiyongbo.scaffold.application.user.assembler;

import io.github.yiyongbo.scaffold.application.user.command.UserCreateCommand;
import io.github.yiyongbo.scaffold.application.user.command.UserUpdateCommand;
import io.github.yiyongbo.scaffold.application.user.dto.UserDTO;
import io.github.yiyongbo.scaffold.application.user.dto.UserPageDTO;
import io.github.yiyongbo.scaffold.application.user.query.UserPageQuery;
import io.github.yiyongbo.scaffold.common.enums.EnumUtils;
import io.github.yiyongbo.scaffold.common.enums.YesNoEnum;
import io.github.yiyongbo.scaffold.common.page.PageResult;
import io.github.yiyongbo.scaffold.domain.user.model.entity.UserEntity;
import io.github.yiyongbo.scaffold.domain.user.repository.query.UserPageCondition;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

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

    /**
     * 系统用户领域实体 转 系统用户数据传输对象
     */
    @Mapping(target = "enabled", source = "enabled", qualifiedByName = "yesNoToCode")
    UserDTO toDTO(UserEntity user);

    /**
     * 系统用户分页查询参数 转 系统用户分页查询条件
     */
    UserPageCondition toPageCondition(UserPageQuery query);

    /**
     * 系统用户领域实体列表 转 系统用户数据传输对象列表
     */
    List<UserPageDTO> toDTOList(List<UserEntity> userList);

    /**
     * 系统用户领域实体分页结果 转 系统用户数据传输对象分页结果
     */
    default PageResult<UserPageDTO> toDTOPageResult(PageResult<UserEntity> pageResult) {
        return PageResult.of(
                toDTOList(pageResult.getRecords()),
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
