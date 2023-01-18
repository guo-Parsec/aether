package top.finder.aether.system.core.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.finder.aether.common.support.api.Apis;
import top.finder.aether.system.api.support.pool.SystemApiConstantPool;
import top.finder.aether.system.core.dto.SysOperateRecordPageQueryDto;
import top.finder.aether.system.core.dto.SysOperateRecordQueryDto;
import top.finder.aether.system.core.service.SysOperateRecordService;
import top.finder.aether.system.core.vo.SysOperateRecordVo;

import java.util.List;

/**
 * <p>系统操作记录控制器</p>
 *
 * @author guocq
 * @since 2023/01/18
 */
@Api(tags = "系统操作记录控制器")
@RestController
@RequestMapping(value = SystemApiConstantPool.RECORD_WEB_API_PREFIX)
public class SysOperateRecordController {
    private final SysOperateRecordService service;

    public SysOperateRecordController(SysOperateRecordService service) {
        this.service = service;
    }
    
    @ApiOperation(value = "查询系统操作记录列表", notes = "系统操作记录列表信息查询")
    @GetMapping(value = "/list")
    public Apis<List<SysOperateRecordVo>> list(SysOperateRecordQueryDto dto) {
        return Apis.success(service.listQuery(dto));
    }

    @ApiOperation(value = "分页查询系统操作记录列表", notes = "系统操作记录列表信息分页查询")
    @GetMapping(value = "/page/query")
    public Apis<IPage<SysOperateRecordVo>> page(SysOperateRecordPageQueryDto dto) {
        return Apis.success(service.pageQuery(dto));
    }
}
