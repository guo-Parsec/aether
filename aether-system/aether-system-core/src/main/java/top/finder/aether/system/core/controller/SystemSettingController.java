package top.finder.aether.system.core.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.finder.aether.system.api.support.pool.SystemApiConstantPool;
import top.finder.aether.common.support.api.Apis;
import top.finder.aether.data.cache.support.helper.RedisHelper;
import top.finder.aether.data.core.support.runner.SystemSetting;

/**
 * <p>系统配置管理</p>
 *
 * @author guocq
 * @since 2023/1/12
 */
@Api(tags = "系统配置管理")
@RestController
@RequestMapping(value = SystemApiConstantPool.SYSTEM_SETTING_WEB_API_PREFIX)
public class SystemSettingController {
    private final SystemSetting systemSetting;

    private RedisHelper redisHelper;

    public SystemSettingController(SystemSetting systemSetting) {
        this.systemSetting = systemSetting;
    }

    @Autowired
    public void setRedisHelper(RedisHelper redisHelper) {
        this.redisHelper = redisHelper;
    }

    @ApiOperation(value = "生成feign密钥", notes = "生成feign密钥")
    @PostMapping(value = "/feign-secret/generate.do")
    public Apis<Void> generateFeignSecret() {
        systemSetting.initFeignSecret();
        return Apis.success();
    }
}
