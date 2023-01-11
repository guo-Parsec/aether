package top.finder.aether.base.core.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.finder.aether.base.api.support.pool.BaseApiConstantPool;
import top.finder.aether.base.core.vo.HiLoginVo;
import top.finder.aether.base.core.vo.HiOperateLogVo;
import top.finder.aether.base.core.dto.HiLoginQueryDto;
import top.finder.aether.base.core.dto.HiOperateLogQueryDto;
import top.finder.aether.base.core.service.LogService;
import top.finder.aether.common.model.SystemLogInfo;
import top.finder.aether.common.support.api.Apis;
import top.finder.aether.data.core.support.annotation.ApiResource;

/**
 * <p>系统日志管理</p>
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

    @ApiOperation(value = "保存操作日志信息", notes = "保存操作日志信息", hidden = true)
    @PostMapping(value = "/operate-log/save.do")
    public Apis<Void> doSaveOperateLog(@RequestBody @Validated SystemLogInfo systemLogInfo) {
        logService.saveOperateLog(systemLogInfo);
        return Apis.success();
    }

    @ApiOperation(value = "保存登录日志信息", notes = "保存登录日志信息", hidden = true)
    @PostMapping(value = "/login-log/save.do")
    public Apis<Void> doSaveLoginLog(@RequestBody @Validated SystemLogInfo systemLogInfo) {
        logService.saveLoginLog(systemLogInfo);
        return Apis.success();
    }

    @ApiResource(code = "AMS:LOGIN-LOG:QUERY", name = "分页查询登录日志信息", sort = 140, desc = "分页查询登录日志信息")
    @ApiOperation(value = "分页查询登录日志信息", notes = "分页查询登录日志信息")
    @GetMapping(value = "/login-log/page/query")
    public Apis<IPage<HiLoginVo>> loginLogPageQuery(@Validated HiLoginQueryDto dto) {
        return Apis.success(logService.loginLogPageQuery(dto));
    }

    @ApiResource(code = "AMS:OPERATE-LOG:QUERY", name = "分页查询操作日志信息", sort = 150, desc = "分页查询操作日志信息")
    @ApiOperation(value = "分页查询操作日志信息", notes = "分页查询操作日志信息")
    @GetMapping(value = "/operate-log/page/query")
    public Apis<IPage<HiOperateLogVo>> operateLogPageQuery(@Validated HiOperateLogQueryDto dto) {
        return Apis.success(logService.operateLogPageQuery(dto));
    }
}
