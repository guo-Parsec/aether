package top.finder.aether.base.core.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.finder.aether.base.api.support.pool.BaseApiConstantPool;
import top.finder.aether.base.core.service.SysResourceService;
import top.finder.aether.common.support.api.Apis;

/**
 * <p>系统资源管理</p>
 *
 * @author guocq
 * @since 2023/1/10
 */
@Api(tags = "系统资源管理")
@RestController
@RequestMapping(value = BaseApiConstantPool.RESOURCE_WEB_API_PREFIX)
public class SysResourceController {
    private final SysResourceService service;

    public SysResourceController(SysResourceService service) {
        this.service = service;
    }

    @ApiOperation(value = "自动生成系统资源数据", notes = "根据applicationName自动生成不同应用的系统资源数据")
    @PostMapping(value = "/auto-generate")
    public Apis<Void> autoGenerateSysResource(@RequestParam(value = "applicationName") String applicationName) {
        service.autoGenerateSysResource(applicationName);
        return Apis.success();
    }
}
