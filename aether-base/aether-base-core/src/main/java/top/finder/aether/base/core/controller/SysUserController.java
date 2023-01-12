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
import top.finder.aether.base.core.dto.SysSysUserPageQueryDto;
import top.finder.aether.base.core.dto.SysUserChangePasswordDto;
import top.finder.aether.base.core.dto.SysUserUpdateDto;
import top.finder.aether.base.core.service.SysUserService;
import top.finder.aether.common.support.annotation.AppBlocking;
import top.finder.aether.common.support.annotation.AppGroup;
import top.finder.aether.common.support.annotation.FeignApi;
import top.finder.aether.common.support.api.Apis;
import top.finder.aether.common.support.pool.AppConstantPool;
import top.finder.aether.data.core.entity.UserDetails;

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

    @ApiOperation(value = "加载系统用户", notes = "加载用户信息并验证是否匹配", hidden = true)
    @GetMapping(value = "/load")
    @FeignApi(AppConstantPool.APP_NAME_SECURITY)
    public Apis<UserDetails> loadUser(@RequestParam("account") String account, @RequestParam("password") String password) {
        return Apis.success(service.loadUser(account, password));
    }

    @ApiOperation(value = "新增系统用户", notes = "新增用户信息")
    @PostMapping(value = "create.do")
    @AppGroup(blocking = {
            @AppBlocking(appNames = {AppConstantPool.APP_NAME_BASE}, blockerId = "userService.systemInnerUserCreate"),
            @AppBlocking(appNames = {AppConstantPool.APP_NAME_SECURITY}, blockerId = "userService.registeredUserCreate")
    })
    public Apis<Void> create(@RequestBody @Validated SysUserCreateDto dto) {
        service.create(dto);
        return Apis.success();
    }

    @ApiOperation(value = "更新系统用户", notes = "更新用户信息")
    @PutMapping(value = "update.do")
    public Apis<Void> update(@RequestBody @Validated SysUserUpdateDto dto) {
        service.update(dto);
        return Apis.success();
    }

    @ApiOperation(value = "删除系统用户", notes = "删除用户信息")
    @DeleteMapping(value = "delete.do")
    public Apis<Void> delete(@RequestBody Set<Long> idSet) {
        service.delete(idSet);
        return Apis.success();
    }

    @ApiOperation(value = "分页系统查询", notes = "分页查询用户信息")
    @GetMapping(value = "/page/query")
    public Apis<IPage<SysUserVo>> pageQuery(@Validated SysSysUserPageQueryDto dto) {
        return Apis.success(service.pageQuery(dto));
    }

    @ApiOperation(value = "后台用户密码修改", notes = "后台用户密码修改")
    @PutMapping(value = "/password-change.do")
    public Apis<Void> changePassword(@Validated @RequestBody SysUserChangePasswordDto dto) {
        service.changePassword(dto);
        return Apis.success();
    }

    @ApiOperation(value = "后台重置用户", notes = "后台重置用户")
    @PutMapping(value = "/reset.do")
    public Apis<Void> resetUser(@RequestParam(value = "account") String account) {
        service.resetUser(account);
        return Apis.success();
    }

    @ApiOperation(value = "后台启用用户", notes = "后台启用用户")
    @PutMapping(value = "/enable.do")
    public Apis<Void> enableUser(@RequestParam(value = "account") String account) {
        service.changeUserEnableStatus(account, BaseConstantPool.ENABLE_STATUS_ENABLE);
        return Apis.success();
    }

    @ApiOperation(value = "后台禁用用户", notes = "后台禁用用户")
    @PutMapping(value = "/disable.do")
    public Apis<Void> disableUser(@RequestParam(value = "account") String account) {
        service.changeUserEnableStatus(account, BaseConstantPool.ENABLE_STATUS_DISABLE);
        return Apis.success();
    }

    @ApiOperation(value = "为用户赋予角色", notes = "为用户赋予角色")
    @PutMapping(value = "/grant-role.do")
    public Apis<Void> grantRoleToUser(@RequestBody @Validated GrantRoleToUserDto dto) {
        service.grantRoleToUser(dto);
        return Apis.success();
    }
}
