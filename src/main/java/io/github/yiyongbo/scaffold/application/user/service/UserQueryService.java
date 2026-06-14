package io.github.yiyongbo.scaffold.application.user.service;

import io.github.yiyongbo.scaffold.application.user.assembler.UserAppAssembler;
import io.github.yiyongbo.scaffold.application.user.dto.UserDTO;
import io.github.yiyongbo.scaffold.application.user.dto.UserPageDTO;
import io.github.yiyongbo.scaffold.application.user.query.UserPageQuery;
import io.github.yiyongbo.scaffold.common.exception.BizAssert;
import io.github.yiyongbo.scaffold.common.page.PageResult;
import io.github.yiyongbo.scaffold.common.response.CommonResponseCode;
import io.github.yiyongbo.scaffold.domain.role.service.RoleDomainService;
import io.github.yiyongbo.scaffold.domain.user.model.entity.UserEntity;
import io.github.yiyongbo.scaffold.domain.user.repository.UserRepository;
import io.github.yiyongbo.scaffold.domain.user.repository.query.UserPageCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统用户查询服务
 *
 * @author kidd
 * @since 2026/6/14 22:52
 */
@Service
@RequiredArgsConstructor
public class UserQueryService {

    private final UserAppAssembler userAppAssembler;

    private final RoleDomainService roleDomainService;

    private final UserRepository userRepository;

    /**
     * 获取用户详情
     *
     * @param id 用户ID
     * @return 用户DTO
     */
    public UserDTO detail(Long id) {
        if (id == null) {
            return null;
        }

        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> BizAssert.newException(CommonResponseCode.NOT_FOUND, "用户不存在"));

        return userAppAssembler.toDTO(user);
    }

    /**
     * 分页查询用户列表
     *
     * @param query 查询参数
     * @return 用户分页列表
     */
    public PageResult<UserPageDTO> page(UserPageQuery query) {
        BizAssert.notNull(query, CommonResponseCode.PARAM_ERROR, "角色分页查询参数不能为空");

        UserPageCondition condition = userAppAssembler.toPageCondition(query);
        PageResult<UserEntity> pageResult = userRepository.page(condition);

        return userAppAssembler.toDTOPageResult(pageResult);
    }

    public List<Long> listRoleIds(Long userId) {
        BizAssert.notNull(userId, CommonResponseCode.PARAM_ERROR, "用户ID不能为空");

        roleDomainService.validateRoleExists(userId);
        return userRepository.listRoleIds(userId);
    }
}
