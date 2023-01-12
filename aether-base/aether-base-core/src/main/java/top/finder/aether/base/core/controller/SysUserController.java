package top.finder.aether.base.core.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.finder.aether.base.api.dto.SysUserCreateDto;
import top.finder.aether.base.api.support.pool.BaseApiConstantPool;
import top.finder.aether.base.api.support.pool.BaseConstantPool;
import top.finder.aether.base.api.vo.SysUserVo;
import top.finder.aether.base.core.dto.GrantRoleToUserDto;
import top.finder.aether.base.core.dto.SysUserChangePasswordDto;
import top.finder.aether.base.core.dto.SysSysUserPageQueryDto;
import top.finder.aether.base.core.dto.SysUserUpdateDto;
import top.finder.aether.base.core.service.SysUserService;
import top.finder.aether.common.support.annotation.AppBlocking;
import top.finder.aether.common.support.annotation.AppGroup;
import top.finder.aether.common.support.annotation.FeignApi;
import top.finder.aether.common.support.api.Apis;
import top.finder.aether.common.support.pool.AppConstantPool;
import top.finder.aether.data.core.support.annotation.ApiResource;

import java.util.Set;

/**
 * <p>用户操作控制器</p>
 *
 * @author guocq
 * @since 2022/12/14
 */
@Api(tags = "系统用户管理")
@RestController
@RequestMapping(value = BaseApiConstantPool.USER_WEB_API_PREFIX)
public class SysUserController {
    private final SysUserService service;

    public SysUserController(SysUserService service) {
        this.service = service;
    }

    @ApiOperation(value = "加载用户", notes = "加载用户信息并验证是否匹配", hidden = true)
    @GetMapping(value = "/load")
    @FeignApi(AppConstantPool.APP_NAME_SECURITY)
    public Apis<SysUserVo> loadUser(@RequestParam("account") String account, @RequestParam("password") String password) {
        return Apis.success(service.loadUser(account, password));
    }

    @ApiResource(code = "AMS:USER:CREATE", name = "新增用户信息", sort = 10, desc = "新增用户信息")
    @ApiOperation(value = "新增用户", notes = "新增用户信息")
    @PostMapping(value = "create.do")
    @AppGroup(blocking = {
            @AppBlocking(appNames = {AppConstantPool.APP_NAME_BASE}, blockerId = "userService.systemInnerUserCreate"),
            @AppBlocking(appNames = {AppConstantPool.APP_NAME_SECURITY}, blockerId = "userService.registeredUserCreate")
    })
    public Apis<Void> create(@RequestBody @Validated SysUserCreateDto dto) {
        service.create(dto);
        return Apis.success();
    }

    @ApiResource(code = "AMS:USER:UPDATE", name = "更新用户", sort = 20, desc = "更新用户信息")
    @ApiOperation(value = "更新用户", notes = "更新用户信息")
    @PutMapping(value = "update.do")
    public Apis<Void> update(@RequestBody @Validated SysUserUpdateDto dto) {
        service.update(dto);
        return Apis.success();
    }

    @ApiResource(code = "AMS:USER:DELETE", name = "删除用户", sort = 30, desc = "删除用户信息")
    @ApiOperation(value = "删除用户", notes = "删除用户信息")
    @DeleteMapping(value = "delete.do")
    public Apis<Void> delete(@RequestBody Set<Long> idSet) {
        service.delete(idSet);
        return Apis.success();
    }

    @ApiResource(code = "AMS:USER:QUERY", name = "分页查询", sort = 40, desc = "分页查询用户信息")
    @ApiOperation(value = "分页查询", notes = "分页查询用户信息")
    @GetMapping(value = "/page/query")
    public Apis<IPage<SysUserVo>> pageQuery(@Validated SysSysUserPageQueryDto dto) {
        return Apis.success(service.pageQuery(dto));
    }

    @ApiResource(code = "AMS:USER:CHANGE", name = "用户修改密码", sort = 50, desc = "用户修改密码")
    @ApiOperation(value = "用户修改密码", notes = "用户修改密码")
    @PutMapping(value = "/password-change.do")
    public Apis<Void> changePassword(@Validated @RequestBody SysUserChangePasswordDto dto) {
        service.changePassword(dto);
        return Apis.success();
    }

    @ApiResource(code = "AMS:USER:RESET", name = "重置用户", sort = 60, desc = "重置用户")
    @ApiOperation(value = "重置用户", notes = "重置用户")
    @PutMapping(value = "/reset.do")
    public Apis<Void> resetUser(@RequestParam(value = "account") String account) {
        service.resetUser(account);
        return Apis.success();
    }

    @ApiResource(code = "AMS:USER:ENABLE", name = "启用用户", sort = 70, desc = "启用用户")
    @ApiOperation(value = "启用用户", notes = "启用用户")
    @PutMapping(value = "/enable.do")
    public Apis<Void> enableUser(@RequestParam(value = "account") String account) {
        service.changeUserEnableStatus(account, BaseConstantPool.ENABLE_STATUS_ENABLE);
        return Apis.success();
    }

    @ApiResource(code = "AMS:USER:DISABLE", name = "禁用用户", sort = 80, desc = "禁用用户")
    @ApiOperation(value = "禁用用户", notes = "禁用用户")
    @PutMapping(value = "/disable.do")
    public Apis<Void> disableUser(@RequestParam(value = "account") String account) {
        service.changeUserEnableStatus(account, BaseConstantPool.ENABLE_STATUS_DISABLE);
        return Apis.success();
    }

    @ApiResource(code = "AMS:USER:GRANT", name = "为用户赋予角色", sort = 90, desc = "为用户赋予角色")
    @ApiOperation(value = "为用户赋予角色", notes = "为用户赋予角色")
    @PutMapping(value = "/grant-role.do")
    public Apis<Void> grantRoleToUser(@RequestBody @Validated GrantRoleToUserDto dto) {
        service.grantRoleToUser(dto);
        return Apis.success();
    }
}
