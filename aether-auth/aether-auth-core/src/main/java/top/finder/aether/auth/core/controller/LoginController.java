package top.finder.aether.auth.core.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.finder.aether.auth.core.model.SecuritySubject;
import top.finder.aether.auth.core.service.LoginService;
import top.finder.aether.auth.core.support.pool.AuthApiConstantPool;
import top.finder.aether.common.support.annotation.LoginLog;
import top.finder.aether.common.support.api.Apis;

/**
 * <p>登录操作控制器</p>
 *
 * @author guocq
 * @since 2022/12/28
 */
@Api(tags = "登录管理")
@RestController
@RequestMapping(value = AuthApiConstantPool.LOGIN_WEB_API_PREFIX)
public class LoginController {
    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @ApiOperation(value = "登录", notes = "用户登录")
    @PostMapping(value = "/login.do")
    @LoginLog(index = 0)
    public Apis<SecuritySubject> login(@RequestParam("account") String account, @RequestParam("password") String password,
                                       @RequestParam(value = "verifyCode", required = false) String verifyCode) {
        return Apis.success(loginService.login(account, password, verifyCode));
    }
}
