package top.finder.aether.auth.core.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.finder.aether.auth.core.support.pool.AuthApiConstantPool;
import top.finder.aether.base.api.client.UserClient;
import top.finder.aether.base.api.dto.UserCreateDto;
import top.finder.aether.common.support.annotation.OperateLog;
import top.finder.aether.common.support.api.Apis;

import javax.annotation.Resource;

/**
 * <p>注册管理</p>
 *
 * @author guocq
 * @since 2023/1/5
 */
@Api(tags = "注册管理")
@RestController
@RequestMapping(value = AuthApiConstantPool.REGISTER_WEB_API_PREFIX)
public class RegisterController {
    @Resource
    private UserClient userClient;

    @ApiOperation(value = "注册用户", notes = "注册用户信息")
    @PostMapping(value = "register.do")
    @OperateLog
    public Apis<Void> register(@RequestBody @Validated UserCreateDto dto) {
        return userClient.create(dto);
    }
}
