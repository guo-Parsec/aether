package top.finder.aether.base.core.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.finder.aether.base.api.support.pool.BaseApiConstantPool;
import top.finder.aether.base.core.dto.SysParamCreateDto;
import top.finder.aether.base.core.dto.SysParamQueryDto;
import top.finder.aether.base.core.dto.SysParamUpdateDto;
import top.finder.aether.base.core.dto.SysSysParamPageQueryDto;
import top.finder.aether.base.core.service.SysParamService;
import top.finder.aether.base.core.vo.SysParamVo;
import top.finder.aether.common.support.api.Apis;

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
public class SysParamController {
    private final SysParamService service;

    public SysParamController(SysParamService service) {
        this.service = service;
    }


    @ApiOperation(value = "查询参数列表", notes = "查询参数")
    @GetMapping(value = "/list")
    public Apis<List<SysParamVo>> list(SysParamQueryDto dto) {
        return Apis.success(service.list(dto));
    }

    @ApiOperation(value = "分页查询参数", notes = "分页查询参数")
    @GetMapping(value = "/page")
    public Apis<IPage<SysParamVo>> page(@Validated SysSysParamPageQueryDto dto) {
        return Apis.success(service.page(dto));
    }

    @ApiOperation(value = "新增系统参数", notes = "新增系统参数")
    @PostMapping(value = "/create.do")
    public Apis<Void> create(@RequestBody @Validated SysParamCreateDto dto) {
        service.create(dto);
        return Apis.success();
    }

    @ApiOperation(value = "更新系统参数", notes = "更新系统参数")
    @PutMapping(value = "/update.do")
    public Apis<Void> update(@RequestBody @Validated SysParamUpdateDto dto) {
        service.update(dto);
        return Apis.success();
    }

    @ApiOperation(value = "删除系统参数", notes = "删除系统参数")
    @DeleteMapping(value = "/delete.do")
    public Apis<Void> delete(@RequestBody Set<Long> idSet) {
        service.delete(idSet);
        return Apis.success();
    }
}
