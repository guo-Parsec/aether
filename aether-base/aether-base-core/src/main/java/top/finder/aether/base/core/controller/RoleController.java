package top.finder.aether.base.core.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.finder.aether.base.api.support.pool.BaseApiConstantPool;
import top.finder.aether.base.core.vo.RoleVo;
import top.finder.aether.base.core.dto.RoleCreateDto;
import top.finder.aether.base.core.dto.RoleUpdateDto;
import top.finder.aether.base.core.entity.Role;
import top.finder.aether.base.core.service.RoleService;
import top.finder.aether.common.support.annotation.OperateLog;
import top.finder.aether.common.support.api.Apis;

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
@RequestMapping(value = BaseApiConstantPool.ROLE_WEB_API_PREFIX)
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @ApiOperation(value = "查询列表", notes = "角色列表信息查询")
    @GetMapping(value = "/list")
    public Apis<List<RoleVo>> list(Role role) {
        return Apis.success(roleService.listQuery(role));
    }

    @ApiOperation(value = "新增角色", notes = "角色信息新增操作")
    @PostMapping(value = "/create.do")
    @OperateLog
    public Apis<Void> create(@RequestBody @Validated RoleCreateDto createDto) {
        roleService.create(createDto);
        return Apis.success();
    }

    @ApiOperation(value = "更新角色", notes = "角色信息更新操作")
    @PutMapping(value = "/update.do")
    @OperateLog
    public Apis<Void> update(@RequestBody @Validated RoleUpdateDto updateDto) {
        roleService.update(updateDto);
        return Apis.success();
    }

    @ApiOperation(value = "删除角色", notes = "角色信息删除操作")
    @DeleteMapping(value = "/delete.do")
    @OperateLog
    public Apis<Void> delete(@RequestBody @Validated Set<Long> idSet) {
        roleService.delete(idSet);
        return Apis.success();
    }
}
