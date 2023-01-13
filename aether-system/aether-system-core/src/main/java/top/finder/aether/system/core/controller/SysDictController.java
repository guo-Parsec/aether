package top.finder.aether.system.core.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.finder.aether.system.api.support.pool.SystemApiConstantPool;
import top.finder.aether.system.core.dto.SysDictCreateDto;
import top.finder.aether.system.core.dto.SysDictUpdateDto;
import top.finder.aether.system.core.entity.SysDict;
import top.finder.aether.system.core.service.SysDictService;
import top.finder.aether.system.core.vo.SysDictVo;
import top.finder.aether.common.support.api.Apis;

import java.util.List;
import java.util.Set;

/**
 * <p>数据字典操作控制器</p>
 *
 * @author guocq
 * @since 2022/12/29
 */
@Api(tags = "系统字典管理")
@RestController
@RequestMapping(value = SystemApiConstantPool.DICT_WEB_API_PREFIX)
public class SysDictController {
    private final SysDictService service;

    public SysDictController(SysDictService service) {
        this.service = service;
    }

    @ApiOperation(value = "查询字典列表", notes = "字典列表信息查询")
    @GetMapping(value = "/list")
    public Apis<List<SysDictVo>> list(SysDict sysDict) {
        return Apis.success(service.listQuery(sysDict));
    }

    @ApiOperation(value = "新增数据字典", notes = "字典信息新增操作")
    @PostMapping(value = "/create.do")
    public Apis<Void> create(@RequestBody @Validated SysDictCreateDto createDto) {
        service.create(createDto);
        return Apis.success();
    }

    @ApiOperation(value = "更新数据字典", notes = "字典信息更新操作")
    @PutMapping(value = "/update.do")
    public Apis<Void> update(@RequestBody @Validated SysDictUpdateDto updateDto) {
        service.update(updateDto);
        return Apis.success();
    }

    @ApiOperation(value = "删除数据字典", notes = "字典信息删除操作")
    @DeleteMapping(value = "/delete.do")
    public Apis<Void> delete(@RequestBody @Validated Set<Long> idSet) {
        service.delete(idSet);
        return Apis.success();
    }
}
