package top.finder.aether.base.core.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.finder.aether.base.api.dto.UserCreateDto;
import top.finder.aether.base.api.support.pool.BaseApiConstantPool;
import top.finder.aether.base.api.support.pool.BaseConstantPool;
import top.finder.aether.base.api.vo.UserVo;
import top.finder.aether.base.api.dto.UserChangePasswordDto;
import top.finder.aether.base.core.dto.UserPageQueryDto;
import top.finder.aether.base.core.dto.UserUpdateDto;
import top.finder.aether.base.core.service.UserService;
import top.finder.aether.common.support.annotation.AppBlocking;
import top.finder.aether.common.support.annotation.AppGroup;
import top.finder.aether.common.support.annotation.FeignApi;
import top.finder.aether.common.support.annotation.OperateLog;
import top.finder.aether.common.support.api.Apis;
import top.finder.aether.common.support.pool.AppConstantPool;

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
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "加载用户", notes = "加载用户信息并验证是否匹配", hidden = true)
    @GetMapping(value = "/load")
    @FeignApi(AppConstantPool.APP_NAME_AUTH)
    public Apis<UserVo> loadUser(@RequestParam("account") String account, @RequestParam("password") String password) {
        return Apis.success(userService.loadUser(account, password));
    }

    @ApiOperation(value = "加载用户", notes = "根据主键加载用户")
    @GetMapping(value = "/find/{id}")
    public Apis<UserVo> findUserById(@PathVariable("id") Long id) {
        return Apis.success(userService.findById(id));
    }

    @ApiOperation(value = "新增用户", notes = "新增用户信息")
    @PostMapping(value = "create.do")
    @AppGroup(blocking = {
            @AppBlocking(appNames = {AppConstantPool.APP_NAME_BASE}, blockerId = "userService.systemInnerUserCreate"),
            @AppBlocking(appNames = {AppConstantPool.APP_NAME_AUTH}, blockerId = "userService.registeredUserCreate")
    })
    @OperateLog
    public Apis<Void> create(@RequestBody @Validated UserCreateDto dto) {
        userService.create(dto);
        return Apis.success();
    }

    @ApiOperation(value = "更新用户", notes = "更新用户信息")
    @PutMapping(value = "update.do")
    @OperateLog
    public Apis<Void> update(@RequestBody @Validated UserUpdateDto dto) {
        userService.update(dto);
        return Apis.success();
    }

    @ApiOperation(value = "删除用户", notes = "删除用户信息")
    @DeleteMapping(value = "delete.do")
    @OperateLog
    public Apis<Void> delete(@RequestBody Set<Long> idSet) {
        userService.delete(idSet);
        return Apis.success();
    }

    @ApiOperation(value = "分页查询", notes = "分页查询用户信息")
    @GetMapping(value = "/page/query")
    public Apis<IPage<UserVo>> pageQuery(@Validated UserPageQueryDto dto) {
        return Apis.success(userService.pageQuery(dto));
    }

    @ApiOperation(value = "用户修改密码", notes = "用户修改密码")
    @GetMapping(value = "/password-change.do")
    public Apis<Void> changePassword(@Validated UserChangePasswordDto dto) {
        userService.changePassword(dto);
        return Apis.success();
    }

    @ApiOperation(value = "重置用户", notes = "重置用户")
    @GetMapping(value = "/reset.do")
    public Apis<Void> resetUser(@RequestParam(value = "account") String account) {
        userService.resetUser(account);
        return Apis.success();
    }

    @ApiOperation(value = "启用用户", notes = "启用用户")
    @GetMapping(value = "/enable.do")
    public Apis<Void> enableUser(@RequestParam(value = "account") String account) {
        userService.changeUserEnableStatus(account, BaseConstantPool.ENABLE_STATUS_ENABLE);
        return Apis.success();
    }

    @ApiOperation(value = "禁用用户", notes = "禁用用户")
    @GetMapping(value = "/disable.do")
    public Apis<Void> disableUser(@RequestParam(value = "account") String account) {
        userService.changeUserEnableStatus(account, BaseConstantPool.ENABLE_STATUS_DISABLE);
        return Apis.success();
    }
}
