package io.github.yiyongbo.scaffold.adapter.web.role;

import io.github.yiyongbo.scaffold.adapter.web.role.assembler.RoleWebAssembler;
import io.github.yiyongbo.scaffold.adapter.web.role.request.RoleCreateRequest;
import io.github.yiyongbo.scaffold.adapter.web.role.request.RoleMenuAssignRequest;
import io.github.yiyongbo.scaffold.adapter.web.role.request.RolePageRequest;
import io.github.yiyongbo.scaffold.adapter.web.role.request.RoleUpdateRequest;
import io.github.yiyongbo.scaffold.adapter.web.role.response.RolePageResponse;
import io.github.yiyongbo.scaffold.adapter.web.role.response.RoleResponse;
import io.github.yiyongbo.scaffold.application.role.command.RoleCreateCommand;
import io.github.yiyongbo.scaffold.application.role.command.RoleUpdateCommand;
import io.github.yiyongbo.scaffold.application.role.dto.RoleDTO;
import io.github.yiyongbo.scaffold.application.role.dto.RolePageDTO;
import io.github.yiyongbo.scaffold.application.role.query.RolePageQuery;
import io.github.yiyongbo.scaffold.application.role.service.RoleCommandService;
import io.github.yiyongbo.scaffold.application.role.service.RoleQueryService;
import io.github.yiyongbo.scaffold.common.page.PageResult;
import io.github.yiyongbo.scaffold.common.response.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统角色管理 Controller
 *
 * @author kidd
 * @since 2026/6/9 22:14
 */
@Tag(name = "系统角色管理")
@Validated
@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleCommandService roleCommandService;

    private final RoleQueryService roleQueryService;

    private final RoleWebAssembler roleWebAssembler;

    @Operation(summary = "创建角色")
    @PostMapping
    public Result<Long> create(@Valid @RequestBody RoleCreateRequest request) {
        RoleCreateCommand createCommand = roleWebAssembler.toCreateCommand(request);
        Long id = roleCommandService.create(createCommand);
        return Result.success(id);
    }

    @Operation(summary = "更新角色")
    @PutMapping("/{id}")
    public Result<Void> update(
            @Parameter(description = "角色ID", required = true, example = "1") @PathVariable Long id,
            @Valid @RequestBody RoleUpdateRequest request) {

        RoleUpdateCommand updateCommand = roleWebAssembler.toUpdateCommand(id, request);
        roleCommandService.update(updateCommand);
        return Result.success();
    }

    @Operation(summary = "删除角色")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@Parameter(description = "角色ID", required = true, example = "1") @PathVariable Long id) {
        roleCommandService.delete(id);
        return Result.success();
    }

    @Operation(summary = "获取角色详情")
    @GetMapping("/{id}")
    public Result<RoleResponse> detail(@Parameter(description = "角色ID", required = true, example = "1") @PathVariable Long id) {
        RoleDTO role = roleQueryService.detail(id);
        return Result.success(roleWebAssembler.toResponse(role));
    }

    @Operation(summary = "获取角色分页")
    @GetMapping("/page")
    public Result<PageResult<RolePageResponse>> page(@Valid RolePageRequest request) {
        RolePageQuery query = roleWebAssembler.toPageQuery(request);
        PageResult<RolePageDTO> page = roleQueryService.page(query);
        return Result.success(roleWebAssembler.toPageResponse(page));
    }

    @Operation(summary = "查询角色已分配菜单ID列表")
    @GetMapping("/{roleId}/menus")
    public Result<List<Long>> listMenuIds(
            @Parameter(description = "角色ID", required = true, example = "1")
            @PathVariable Long roleId) {

        List<Long> menuIds = roleQueryService.listMenuIds(roleId);
        return Result.success(menuIds);
    }

    @Operation(summary = "分配角色菜单权限")
    @PostMapping("/{roleId}/menus")
    public Result<Void> assignMenus(
            @Parameter(description = "角色ID", required = true, example = "1")
            @PathVariable Long roleId,
            @Valid @RequestBody RoleMenuAssignRequest request) {

        roleCommandService.assignMenus(roleId, request.getMenuIds());
        return Result.success();
    }
}
