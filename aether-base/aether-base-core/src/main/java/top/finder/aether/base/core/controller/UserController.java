package top.finder.aether.base.core.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import top.finder.aether.base.api.support.pool.BaseApiConstantPool;
import top.finder.aether.base.api.vo.UserVo;
import top.finder.aether.base.core.service.UserService;
import top.finder.aether.common.support.annotation.FeignApi;
import top.finder.aether.common.support.api.Apis;

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
    @FeignApi("aether-auth")
    public Apis<UserVo> loadUser(@RequestParam("account") String account, @RequestParam("password") String password) {
        return Apis.success(userService.loadUser(account, password));
    }

    @ApiOperation(value = "加载用户", notes = "根据主键加载用户")
    @GetMapping(value = "/find/{id}")
    public Apis<UserVo> findUserById(@PathVariable("id") Long id) {
        return Apis.success(userService.findById(id));
    }
}
