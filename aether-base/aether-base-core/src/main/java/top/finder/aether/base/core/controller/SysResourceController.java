package top.finder.aether.base.core.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.finder.aether.base.api.support.pool.BaseApiConstantPool;
import top.finder.aether.base.core.service.SysResourceService;

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
}
