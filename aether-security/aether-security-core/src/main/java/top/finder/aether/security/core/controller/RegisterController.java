package top.finder.aether.security.core.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.finder.aether.base.api.client.SysUserClient;
import top.finder.aether.common.support.api.Apis;
import top.finder.aether.security.core.dto.RegisterDto;
import top.finder.aether.security.core.support.pool.SecurityApiConstantPool;

import javax.annotation.Resource;

/**
 * <p>注册管理</p>
 *
 * @author guocq
 * @since 2023/1/5
 */
@Api(tags = "注册管理")
@RestController
@RequestMapping(value = SecurityApiConstantPool.REGISTER_WEB_API_PREFIX)
public class RegisterController {
    @Resource
    private SysUserClient sysUserClient;

    @ApiOperation(value = "注册用户", notes = "注册用户信息", nickname = "anon")
    @PostMapping(value = "register.do")
    public Apis<Void> register(@RequestBody @Validated RegisterDto dto) {
        return sysUserClient.create(dto.toUserCreateDto());
    }
}
