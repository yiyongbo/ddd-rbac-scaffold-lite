package io.github.yiyongbo.scaffold.adapter.web.user;

import io.github.yiyongbo.scaffold.adapter.web.user.assembler.UserWebAssembler;
import io.github.yiyongbo.scaffold.adapter.web.user.request.UserAssignRolesRequest;
import io.github.yiyongbo.scaffold.adapter.web.user.request.UserCreateRequest;
import io.github.yiyongbo.scaffold.adapter.web.user.request.UserPageRequest;
import io.github.yiyongbo.scaffold.adapter.web.user.request.UserUpdateRequest;
import io.github.yiyongbo.scaffold.adapter.web.user.response.UserPageResponse;
import io.github.yiyongbo.scaffold.adapter.web.user.response.UserResponse;
import io.github.yiyongbo.scaffold.application.user.command.UserCreateCommand;
import io.github.yiyongbo.scaffold.application.user.command.UserUpdateCommand;
import io.github.yiyongbo.scaffold.application.user.dto.UserDTO;
import io.github.yiyongbo.scaffold.application.user.dto.UserPageDTO;
import io.github.yiyongbo.scaffold.application.user.query.UserPageQuery;
import io.github.yiyongbo.scaffold.application.user.service.UserCommandService;
import io.github.yiyongbo.scaffold.application.user.service.UserQueryService;
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
 * 系统用户管理 Controller
 *
 * @author kidd
 * @since 2026/6/14 17:36
 */
@Tag(name = "系统用户管理")
@Validated
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserCommandService userCommandService;
    private final UserQueryService userQueryService;

    private final UserWebAssembler userWebAssembler;

    @Operation(summary = "创建用户")
    @PostMapping
    public Result<Long> create(@Valid @RequestBody UserCreateRequest request) {
        UserCreateCommand createCommand = userWebAssembler.toCreateCommand(request);
        Long id = userCommandService.create(createCommand);
        return Result.success(id);
    }

    @Operation(summary = "更新用户")
    @PutMapping("/{id}")
    public Result<Void> update(
            @Parameter(description = "用户ID", required = true, example = "1") @PathVariable Long id,
            @Valid @RequestBody UserUpdateRequest request) {
        UserUpdateCommand updateCommand = userWebAssembler.toUpdateCommand(id, request);
        userCommandService.update(updateCommand);
        return Result.success();
    }

    @Operation(summary = "删除用户")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@Parameter(description = "用户ID", required = true, example = "1") @PathVariable Long id) {
        userCommandService.delete(id);
        return Result.success();
    }

    @Operation(summary = "获取用户详情")
    @GetMapping("/{id}")
    public Result<UserResponse> detail(@Parameter(description = "用户ID", required = true, example = "1") @PathVariable Long id) {
        UserDTO user = userQueryService.detail(id);
        return Result.success(userWebAssembler.toResponse(user));
    }

    @Operation(summary = "获取用户分页")
    @GetMapping("/page")
    public Result<PageResult<UserPageResponse>> page(@Valid UserPageRequest request) {
        UserPageQuery query = userWebAssembler.toPageQuery(request);
        PageResult<UserPageDTO> page = userQueryService.page(query);
        return Result.success(userWebAssembler.toPageResponse(page));
    }

    @Operation(summary = "查询用户角色 ID 列表")
    @GetMapping("/{userId}/roleIds")
    public Result<List<Long>> listRoleIds(@Parameter(description = "用户ID", required = true, example = "1") @PathVariable Long userId) {
        List<Long> roleIds =  userQueryService.listRoleIds(userId);
        return Result.success(roleIds);
    }

    @Operation(summary = "分配用户角色")
    @PostMapping("/{userId}/roles")
    public Result<Void> assignRoles(
            @Parameter(description = "用户ID", required = true, example = "1") @PathVariable Long userId,
            @Valid @RequestBody UserAssignRolesRequest request) {

        userCommandService.assignRoles(userId, request.getRoleIds());
        return Result.success();
    }

}
