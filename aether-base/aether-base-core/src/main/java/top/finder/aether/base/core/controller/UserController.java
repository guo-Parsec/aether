package top.finder.aether.base.core.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.finder.aether.base.api.support.pool.BaseApiConstantPool;
import top.finder.aether.base.api.vo.UserVo;
import top.finder.aether.base.api.dto.UserCreateDto;
import top.finder.aether.base.core.service.UserService;
import top.finder.aether.common.support.annotation.AppBlocking;
import top.finder.aether.common.support.annotation.AppGroup;
import top.finder.aether.common.support.annotation.FeignApi;
import top.finder.aether.common.support.annotation.OperateLog;
import top.finder.aether.common.support.api.Apis;
import top.finder.aether.common.support.pool.AppConstantPool;

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
            @AppBlocking(appNames = {AppConstantPool.APP_NAME_BASE}, execBean = UserService.class, execMethod = "systemInnerUserCreate"),
            @AppBlocking(appNames = {AppConstantPool.APP_NAME_AUTH}, execBean = UserService.class, execMethod = "registeredUserCreate")
    })
    @OperateLog
    public Apis<Void> create(@RequestBody @Validated UserCreateDto dto) {
        userService.create(dto);
        return Apis.success();
    }
}
