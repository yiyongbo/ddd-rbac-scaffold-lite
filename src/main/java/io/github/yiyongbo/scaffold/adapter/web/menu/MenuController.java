package io.github.yiyongbo.scaffold.adapter.web.menu;

import io.github.yiyongbo.scaffold.adapter.web.menu.assembler.MenuWebAssembler;
import io.github.yiyongbo.scaffold.adapter.web.menu.request.MenuCreateRequest;
import io.github.yiyongbo.scaffold.adapter.web.menu.request.MenuUpdateRequest;
import io.github.yiyongbo.scaffold.adapter.web.menu.response.MenuResponse;
import io.github.yiyongbo.scaffold.adapter.web.menu.response.MenuTreeResponse;
import io.github.yiyongbo.scaffold.application.menu.command.MenuCreateCommand;
import io.github.yiyongbo.scaffold.application.menu.command.MenuUpdateCommand;
import io.github.yiyongbo.scaffold.application.menu.dto.MenuDTO;
import io.github.yiyongbo.scaffold.application.menu.dto.MenuTreeDTO;
import io.github.yiyongbo.scaffold.application.menu.service.MenuCommandService;
import io.github.yiyongbo.scaffold.application.menu.service.MenuQueryService;
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
 * 系统菜单管理 Controller
 *
 * @author kidd
 * @since 2026/6/9 14:03
 */
@Tag(name = "系统菜单管理")
@Validated
@RestController
@RequestMapping("/api/v1/menus")
@RequiredArgsConstructor
public class MenuController {

    private final MenuCommandService menuCommandService;

    private final MenuQueryService menuQueryService;

    private final MenuWebAssembler menuWebAssembler;

    @Operation(summary = "创建菜单")
    @PostMapping
    public Result<Long> create(@Valid @RequestBody MenuCreateRequest request) {
        MenuCreateCommand createCommand = menuWebAssembler.toCreateCommand(request);
        Long id = menuCommandService.create(createCommand);
        return Result.success(id);
    }

    @Operation(summary = "更新菜单")
    @PutMapping("/{id}")
    public Result<Void> update(
            @Parameter(description = "菜单ID", required = true, example = "1")
            @PathVariable Long id,
            @Valid @RequestBody MenuUpdateRequest request) {

        MenuUpdateCommand updateCommand = menuWebAssembler.toUpdateCommand(id, request);
        menuCommandService.update(updateCommand);
        return Result.success();
    }

    @Operation(summary = "删除菜单")
    @DeleteMapping("/{id}")
    public Result<Void> delete(
            @Parameter(description = "菜单ID", required = true, example = "1")
            @PathVariable Long id) {

        menuCommandService.delete(id);
        return Result.success();
    }

    @Operation(summary = "查询菜单详情")
    @GetMapping("/{id}")
    public Result<MenuResponse> detail(
            @Parameter(description = "菜单ID", required = true, example = "1")
            @PathVariable Long id) {

        MenuDTO menuDTO = menuQueryService.detail(id);
        return Result.success(menuWebAssembler.toResponse(menuDTO));
    }

    @Operation(summary = "查询菜单树")
    @GetMapping("/tree")
    public Result<List<MenuTreeResponse>> tree() {
        List<MenuTreeDTO> treeDTOList = menuQueryService.tree();
        return Result.success(menuWebAssembler.toTreeResponseList(treeDTOList));
    }
}
