package top.finder.aether.base.core.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.finder.aether.base.api.support.pool.BaseApiConstantPool;
import top.finder.aether.base.core.service.LogService;
import top.finder.aether.common.model.LogModel;
import top.finder.aether.common.support.api.Apis;

/**
 * <p></p>
 *
 * @author guocq
 * @since 2022/12/30
 */
@Api(tags = "系统日志管理")
@RestController
@RequestMapping(value = BaseApiConstantPool.LOG_WEB_API_PREFIX)
public class LogController {
    private final LogService logService;

    public LogController(LogService logService) {
        this.logService = logService;
    }

    @ApiOperation(value = "保存操作日志信息", notes = "保存操作日志信息")
    @PostMapping(value = "/operate-log/save.do")
    public Apis<Void> doSaveOperateLog(@RequestBody @Validated LogModel logModel) {
        logService.saveOperateLog(logModel);
        return Apis.success();
    }
}
