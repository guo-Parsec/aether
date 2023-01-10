package top.finder.aether.base.core.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.finder.aether.base.api.support.pool.BaseApiConstantPool;
import top.finder.aether.base.core.dto.ParamCreateDto;
import top.finder.aether.base.core.dto.ParamPageQueryDto;
import top.finder.aether.base.core.dto.ParamQueryDto;
import top.finder.aether.base.core.dto.ParamUpdateDto;
import top.finder.aether.base.core.service.ParamService;
import top.finder.aether.base.api.vo.ParamVo;
import top.finder.aether.common.support.annotation.OperateLog;
import top.finder.aether.common.support.api.Apis;
import top.finder.aether.data.core.support.annotation.ApiResource;

import java.util.List;
import java.util.Set;

/**
 * <p>系统参数管理</p>
 *
 * @author guocq
 * @since 2023/1/9
 */
@Api(tags = "系统参数管理")
@RestController
@RequestMapping(value = BaseApiConstantPool.PARAM_WEB_API_PREFIX)
public class ParamController {
    private final ParamService service;

    public ParamController(ParamService service) {
        this.service = service;
    }

    @ApiResource(code = "AMS:PARAM:QUERY", name = "查询参数", sort = 160, desc = "查询参数")
    @ApiOperation(value = "查询", notes = "查询参数")
    @GetMapping(value = "/list")
    public Apis<List<ParamVo>> list(ParamQueryDto dto) {
        return Apis.success(service.list(dto));
    }

    @ApiResource(code = "AMS:PARAM:QUERY", name = "分页查询参数", sort = 170, desc = "分页查询参数")
    @ApiOperation(value = "分页", notes = "分页查询参数")
    @GetMapping(value = "/page")
    public Apis<IPage<ParamVo>> page(@Validated ParamPageQueryDto dto) {
        return Apis.success(service.page(dto));
    }

    @ApiResource(code = "AMS:PARAM:CREATE", name = "新增系统参数", sort = 180, desc = "新增系统参数")
    @ApiOperation(value = "新增", notes = "新增系统参数")
    @PostMapping(value = "/create.do")
    @OperateLog
    public Apis<Void> create(@RequestBody @Validated ParamCreateDto dto) {
        service.create(dto);
        return Apis.success();
    }

    @ApiResource(code = "AMS:PARAM:UPDATE", name = "更新系统参数", sort = 190, desc = "更新系统参数")
    @ApiOperation(value = "更新", notes = "更新系统参数")
    @PutMapping(value = "/update.do")
    @OperateLog
    public Apis<Void> update(@RequestBody @Validated ParamUpdateDto dto) {
        service.update(dto);
        return Apis.success();
    }

    @ApiResource(code = "AMS:PARAM:DELETE", name = "删除系统参数", sort = 200, desc = "删除系统参数")
    @ApiOperation(value = "删除", notes = "删除系统参数")
    @DeleteMapping(value = "/delete.do")
    @OperateLog
    public Apis<Void> delete(@RequestBody Set<Long> idSet) {
        service.delete(idSet);
        return Apis.success();
    }

    @ApiResource(code = "AMS:PARAM:QUERY", name = "根据参数类别码查询参数列表", sort = 210, desc = "根据参数类别码查询参数列表")
    @ApiOperation(value = "查询", notes = "根据参数类别码查询参数列表")
    @GetMapping(value = "/find-param-by-param-type-code")
    public Apis<List<ParamVo>> findParamByParamTypeCode(@RequestParam(value = "paramTypeCode") String paramTypeCode) {
        return Apis.success(service.findParamByParamTypeCode(paramTypeCode));
    }

    @ApiResource(code = "AMS:PARAM:QUERY", name = "根据参数码查询参数", sort = 220, desc = "根据参数码查询参数")
    @ApiOperation(value = "查询", notes = "根据参数码查询参数")
    @GetMapping(value = "/find-param-by-param-code")
    public Apis<ParamVo> findParamByParamCode(@RequestParam(value = "paramCode") String paramCode) {
        return Apis.success(service.findParamByParamCode(paramCode));
    }
}
