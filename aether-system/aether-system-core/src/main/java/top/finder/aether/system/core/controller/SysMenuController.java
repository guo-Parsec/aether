package top.finder.aether.system.core.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.finder.aether.common.support.api.Apis;
import top.finder.aether.system.api.support.pool.SystemApiConstantPool;
import top.finder.aether.system.core.dto.SysMenuCreateDto;
import top.finder.aether.system.core.dto.SysMenuPageQueryDto;
import top.finder.aether.system.core.dto.SysMenuQueryDto;
import top.finder.aether.system.core.dto.SysMenuUpdateDto;
import top.finder.aether.system.core.service.SysMenuService;
import top.finder.aether.system.core.vo.SysMenuVo;

import java.util.List;
import java.util.Set;

/**
 * <p>系统菜单控制器</p>
 *
 * @author guocq
 * @since 2023/01/18
 */
@Api(tags = "系统菜单控制器")
@RestController
@RequestMapping(value = SystemApiConstantPool.MENU_WEB_API_PREFIX)
public class SysMenuController {
    private final SysMenuService service;

    public SysMenuController(SysMenuService service) {
        this.service = service;
    }

    @ApiOperation(value = "新增系统菜单", notes = "系统菜单新增操作")
    @PostMapping(value = "/create.do")
    public Apis<Void> create(@RequestBody @Validated SysMenuCreateDto dto) {
        service.create(dto);
        return Apis.success();
    }

    @ApiOperation(value = "删除系统菜单", notes = "系统菜单删除操作")
    @DeleteMapping(value = "/delete.do")
    public Apis<Void> delete(@RequestBody @Validated Set<Long> idSet) {
        service.delete(idSet);
        return Apis.success();
    }

    @ApiOperation(value = "更新系统菜单", notes = "系统菜单更新操作")
    @PutMapping(value = "/update.do")
    public Apis<Void> update(@RequestBody @Validated SysMenuUpdateDto dto) {
        service.update(dto);
        return Apis.success();
    }

    @ApiOperation(value = "查询系统菜单列表", notes = "系统菜单列表信息查询")
    @GetMapping(value = "/list")
    public Apis<List<SysMenuVo>> list(SysMenuQueryDto dto) {
        return Apis.success(service.listQuery(dto));
    }

    @ApiOperation(value = "分页查询系统菜单列表", notes = "系统菜单列表信息分页查询")
    @GetMapping(value = "/page/query")
    public Apis<IPage<SysMenuVo>> page(SysMenuPageQueryDto dto) {
        return Apis.success(service.pageQuery(dto));
    }
}
