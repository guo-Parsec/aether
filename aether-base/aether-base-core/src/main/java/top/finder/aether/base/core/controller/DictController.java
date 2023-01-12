package top.finder.aether.base.core.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.finder.aether.base.api.support.pool.BaseApiConstantPool;
import top.finder.aether.base.core.dto.DictCreateDto;
import top.finder.aether.base.core.dto.DictUpdateDto;
import top.finder.aether.base.core.entity.Dict;
import top.finder.aether.base.core.service.DictService;
import top.finder.aether.base.core.vo.DictVo;
import top.finder.aether.common.support.api.Apis;
import top.finder.aether.data.core.support.annotation.ApiResource;

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
@RequestMapping(value = BaseApiConstantPool.DICT_WEB_API_PREFIX)
public class DictController {
    private final DictService dictService;

    public DictController(DictService dictService) {
        this.dictService = dictService;
    }

    @ApiResource(code = "AMS:DICT:QUERY", name = "查询列表", sort = 100, desc = "字典列表信息查询")
    @ApiOperation(value = "查询列表", notes = "字典列表信息查询")
    @GetMapping(value = "/list")
    public Apis<List<DictVo>> list(Dict dict) {
        return Apis.success(dictService.listQuery(dict));
    }

    @ApiResource(code = "AMS:DICT:CREATE", name = "新增字典", sort = 110, desc = "字典信息新增操作")
    @ApiOperation(value = "新增字典", notes = "字典信息新增操作")
    @PostMapping(value = "/create.do")
    public Apis<Void> create(@RequestBody @Validated DictCreateDto createDto) {
        dictService.create(createDto);
        return Apis.success();
    }

    @ApiResource(code = "AMS:DICT:UPDATE", name = "更新字典", sort = 120, desc = "字典信息更新操作")
    @ApiOperation(value = "更新字典", notes = "字典信息更新操作")
    @PutMapping(value = "/update.do")
    public Apis<Void> update(@RequestBody @Validated DictUpdateDto updateDto) {
        dictService.update(updateDto);
        return Apis.success();
    }

    @ApiResource(code = "AMS:DICT:DELETE", name = "删除字典", sort = 130, desc = "字典信息删除操作")
    @ApiOperation(value = "删除字典", notes = "字典信息删除操作")
    @DeleteMapping(value = "/delete.do")
    public Apis<Void> delete(@RequestBody @Validated Set<Long> idSet) {
        dictService.delete(idSet);
        return Apis.success();
    }
}
