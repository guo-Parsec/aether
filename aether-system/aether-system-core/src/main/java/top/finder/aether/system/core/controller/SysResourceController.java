package top.finder.aether.system.core.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.finder.aether.common.support.api.Apis;
import top.finder.aether.system.api.support.pool.SystemApiConstantPool;
import top.finder.aether.system.core.dto.SysResourceCreateDto;
import top.finder.aether.system.core.dto.SysResourcePageQueryDto;
import top.finder.aether.system.core.dto.SysResourceQueryDto;
import top.finder.aether.system.core.dto.SysResourceUpdateDto;
import top.finder.aether.system.core.service.SysResourceService;
import top.finder.aether.system.core.vo.SysResourceVo;

import java.util.List;
import java.util.Set;

/**
 * <p>系统资源管理</p>
 *
 * @author guocq
 * @since 2023/1/10
 */
@Api(tags = "系统资源管理")
@RestController
@RequestMapping(value = SystemApiConstantPool.RESOURCE_WEB_API_PREFIX)
public class SysResourceController {
    private final SysResourceService service;

    public SysResourceController(SysResourceService service) {
        this.service = service;
    }

    @ApiOperation(value = "查询资源列表", notes = "资源列表信息查询")
    @GetMapping(value = "/list")
    public Apis<List<SysResourceVo>> list(SysResourceQueryDto dto) {
        return Apis.success(service.listQuery(dto));
    }


    @ApiOperation(value = "分页查询资源列表", notes = "资源列表信息分页查询")
    @GetMapping(value = "/page/query")
    public Apis<IPage<SysResourceVo>> page(SysResourcePageQueryDto dto) {
        return Apis.success(service.pageQuery(dto));
    }

    @ApiOperation(value = "新增系统资源", notes = "资源信息新增操作")
    @PostMapping(value = "/create.do")
    public Apis<Void> create(@RequestBody @Validated SysResourceCreateDto dto) {
        service.create(dto);
        return Apis.success();
    }

    @ApiOperation(value = "更新系统资源", notes = "资源信息更新操作")
    @PutMapping(value = "/update.do")
    public Apis<Void> update(@RequestBody @Validated SysResourceUpdateDto dto) {
        service.update(dto);
        return Apis.success();
    }

    @ApiOperation(value = "删除系统资源", notes = "资源信息删除操作")
    @DeleteMapping(value = "/delete.do")
    public Apis<Void> delete(@RequestBody @Validated Set<Long> idSet) {
        service.delete(idSet);
        return Apis.success();
    }
}
