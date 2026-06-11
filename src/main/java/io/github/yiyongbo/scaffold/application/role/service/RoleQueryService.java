package io.github.yiyongbo.scaffold.application.role.service;

import io.github.yiyongbo.scaffold.application.role.assembler.RoleAppAssembler;
import io.github.yiyongbo.scaffold.application.role.dto.RoleDTO;
import io.github.yiyongbo.scaffold.application.role.dto.RolePageDTO;
import io.github.yiyongbo.scaffold.application.role.query.RolePageQuery;
import io.github.yiyongbo.scaffold.common.exception.BizAssert;
import io.github.yiyongbo.scaffold.common.page.PageResult;
import io.github.yiyongbo.scaffold.common.response.CommonResponseCode;
import io.github.yiyongbo.scaffold.domain.role.model.entity.RoleEntity;
import io.github.yiyongbo.scaffold.domain.role.repository.RoleRepository;
import io.github.yiyongbo.scaffold.domain.role.repository.query.RolePageCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 系统角色查询服务
 *
 * @author kidd
 * @since 2026/6/10 19:51
 */
@Service
@RequiredArgsConstructor
public class RoleQueryService {

    private final RoleRepository roleRepository;

    private final RoleAppAssembler roleAppAssembler;

    public RoleDTO detail(Long id) {
        BizAssert.notNull(id, CommonResponseCode.PARAM_ERROR, "角色ID不能为空");

        RoleEntity role = roleRepository.findById(id)
                .orElseThrow(() -> BizAssert.newException(CommonResponseCode.NOT_FOUND, "角色不存在"));

        return roleAppAssembler.toDTO(role);
    }

    public PageResult<RolePageDTO> page(RolePageQuery query) {
        BizAssert.notNull(query, CommonResponseCode.PARAM_ERROR, "角色分页查询参数不能为空");

        RolePageCondition condition = roleAppAssembler.toPageCondition(query);
        PageResult<RoleEntity> pageResult = roleRepository.page(condition);

        return roleAppAssembler.toDTOPageResult(pageResult);
    }
}
