package top.finder.aether.system.core.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.finder.aether.common.support.api.Apis;
import top.finder.aether.system.api.support.pool.SystemApiConstantPool;
import top.finder.aether.system.core.dto.*;
import top.finder.aether.system.core.service.SysRoleService;
import top.finder.aether.system.core.vo.SysRoleVo;

import java.util.List;
import java.util.Set;

/**
 * <p>角色操作控制器</p>
 *
 * @author guocq
 * @since 2022/12/27
 */
@Api(tags = "系统角色管理")
@RestController
@RequestMapping(value = SystemApiConstantPool.ROLE_WEB_API_PREFIX)
public class SysRoleController {
    private final SysRoleService service;

    public SysRoleController(SysRoleService service) {
        this.service = service;
    }

    @ApiOperation(value = "查询角色列表", notes = "角色列表信息查询")
    @GetMapping(value = "/list")
    public Apis<List<SysRoleVo>> list(SysRoleQueryDto dto) {
        return Apis.success(service.listQuery(dto));
    }


    @ApiOperation(value = "分页查询角色列表", notes = "角色列表信息分页查询")
    @GetMapping(value = "/page/query")
    public Apis<IPage<SysRoleVo>> page(SysRolePageQueryDto dto) {
        return Apis.success(service.pageQuery(dto));
    }

    @ApiOperation(value = "新增系统角色", notes = "角色信息新增操作")
    @PostMapping(value = "/create.do")
    public Apis<Void> create(@RequestBody @Validated SysRoleCreateDto createDto) {
        service.create(createDto);
        return Apis.success();
    }

    @ApiOperation(value = "更新系统角色", notes = "角色信息更新操作")
    @PutMapping(value = "/update.do")
    public Apis<Void> update(@RequestBody @Validated SysRoleUpdateDto updateDto) {
        service.update(updateDto);
        return Apis.success();
    }

    @ApiOperation(value = "删除系统角色", notes = "角色信息删除操作")
    @DeleteMapping(value = "/delete.do")
    public Apis<Void> delete(@RequestBody @Validated Set<Long> idSet) {
        service.delete(idSet);
        return Apis.success();
    }

    @ApiOperation(value = "为角色赋予资源", notes = "为角色赋予资源")
    @PostMapping(value = "/grant-resource.do")
    public Apis<Void> grantResourceToRole(@RequestBody @Validated GrantResourceToRoleDto dto) {
        service.grantResourceToRole(dto);
        return Apis.success();
    }
}
