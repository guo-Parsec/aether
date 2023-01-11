package top.finder.aether.base.core.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.finder.aether.base.api.vo.ParamVo;
import top.finder.aether.base.core.dto.ParamCreateDto;
import top.finder.aether.base.core.dto.ParamPageQueryDto;
import top.finder.aether.base.core.dto.ParamQueryDto;
import top.finder.aether.base.core.dto.ParamUpdateDto;
import top.finder.aether.base.core.entity.Param;
import top.finder.aether.base.core.mapper.ParamMapper;
import top.finder.aether.base.core.service.ParamService;
import top.finder.aether.common.support.exception.AetherValidException;
import top.finder.aether.common.support.helper.TransformerHelper;
import top.finder.aether.common.utils.Loggers;
import top.finder.aether.data.core.support.helper.PageHelper;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>系统参数服务接口实现类</p>
 *
 * @author guocq
 * @since 2023/1/9
 */
@Service(value = "paramService")
public class ParamServiceImpl implements ParamService {
    private static final Logger log = LoggerFactory.getLogger(ParamServiceImpl.class);

    @Resource
    private ParamMapper mapper;

    /**
     * <p>根据字典查询参数列表</p>
     *
     * @param dto 参数
     * @return {@link List}
     * @author guocq
     * @date 2023/1/9 11:13
     */
    @Override
    @Cacheable(cacheNames = "AMS:PARAM:LIST", keyGenerator = "modelKeyGenerator", unless = "#result.isEmpty()")
    public List<ParamVo> list(ParamQueryDto dto) {
        log.debug("根据字典查询参数列表, 入参={}", dto);
        List<Param> params = mapper.selectList(findQueryWrapper(dto));
        return params.stream().map(record -> TransformerHelper.transformer(record, ParamVo.class)).collect(Collectors.toList());
    }

    /**
     * <p>根据字典查询参数分页列表</p>
     *
     * @param dto 参数
     * @return {@link IPage}
     * @author guocq
     * @date 2023/1/9 11:14
     */
    @Override
    public IPage<ParamVo> page(ParamPageQueryDto dto) {
        log.debug("根据字典查询参数分页列表, 入参={}", dto);
        IPage<Param> page = PageHelper.initPage(dto);
        IPage<Param> rawPage = mapper.selectPage(page, findQueryWrapper(dto));
        return rawPage.convert(record -> TransformerHelper.transformer(record, ParamVo.class));
    }

    /**
     * <p>获取查询wrapper</p>
     *
     * @param dto 查询入参
     * @return {@link Wrapper}
     * @author guocq
     * @date 2023/1/9 11:30
     */
    private Wrapper<Param> findQueryWrapper(ParamQueryDto dto) {
        return new LambdaQueryWrapper<Param>()
                .eq(ObjectUtil.isNotNull(dto.getId()), Param::getId, dto.getId())
                .eq(StrUtil.isNotBlank(dto.getParamTypeCode()), Param::getParamTypeCode, dto.getParamTypeCode())
                .like(StrUtil.isNotBlank(dto.getParamTypeName()), Param::getParamTypeName, dto.getParamTypeName())
                .eq(StrUtil.isNotBlank(dto.getParamCode()), Param::getParamCode, dto.getParamCode())
                .like(StrUtil.isNotBlank(dto.getParamName()), Param::getParamName, dto.getParamName());
    }

    /**
     * <p>系统参数新增</p>
     *
     * @param dto 参数
     * @author guocq
     * @date 2023/1/9 11:15
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(cacheNames = {"AMS:PARAM:SINGLE", "AMS:PARAM:LIST"}, allEntries = true)
    public void create(ParamCreateDto dto) {
        log.debug("系统参数新增, 入参={}", dto);
        checkBeforeCreate(dto);
        Param param = TransformerHelper.transformer(dto, Param.class);
        mapper.insert(param);
        log.debug("系统参数新增成功");
    }

    /**
     * <p>系统参数更新</p>
     *
     * @param dto 参数
     * @author guocq
     * @date 2023/1/9 11:15
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(cacheNames = {"AMS:PARAM:SINGLE", "AMS:PARAM:LIST"}, allEntries = true)
    public void update(ParamUpdateDto dto) {
        log.debug("系统参数更新, 入参={}", dto);
        checkBeforeUpdate(dto);
        Param param = TransformerHelper.transformer(dto, Param.class);
        mapper.updateById(param);
        log.debug("系统参数更新成功");
    }

    /**
     * <p>系统参数批量删除</p>
     *
     * @param idSet 主键列表
     * @author guocq
     * @date 2023/1/9 11:15
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(cacheNames = {"AMS:PARAM:SINGLE", "AMS:PARAM:LIST"}, allEntries = true)
    public void delete(Set<Long> idSet) {
        log.debug("系统参数批量删除, 入参={}", idSet);
        checkBeforeDelete(idSet);
        mapper.logicBatchDeleteByIds(idSet, System.currentTimeMillis());
        log.debug("系统参数批量删除成功");
    }

    /**
     * <p>根据参数类别码查询参数列表</p>
     *
     * @param paramTypeCode 参数类别码
     * @return {@link List}
     * @author guocq
     * @date 2023/1/9 11:46
     * @see ParamService#list(ParamQueryDto)
     */
    @Override
    @Cacheable(cacheNames = "AMS:PARAM:LIST", key = "'paramTypeCode:' + #paramTypeCode", unless = "#result.isEmpty()")
    public List<ParamVo> findParamByParamTypeCode(String paramTypeCode) {
        if (StrUtil.isBlank(paramTypeCode)) {
            log.error("paramTypeCode不能为空");
            throw new AetherValidException("paramTypeCode不能为空");
        }
        ParamQueryDto dto = new ParamPageQueryDto();
        dto.setParamTypeCode(paramTypeCode);
        return list(dto);
    }

    /**
     * <p>根据参数码查询参数</p>
     *
     * @param paramCode 参数码
     * @return {@link ParamVo}
     * @author guocq
     * @date 2023/1/9 11:46
     */
    @Override
    @Cacheable(cacheNames = "AMS:PARAM:SINGLE", key = "'paramCode:' + #paramCode")
    public ParamVo findParamByParamCode(String paramCode) {
        if (StrUtil.isBlank(paramCode)) {
            log.error("paramCode不能为空");
            throw new AetherValidException("paramCode不能为空");
        }
        Wrapper<Param> wrapper = new LambdaQueryWrapper<Param>()
                .eq(Param::getParamCode, paramCode);
        Param param = mapper.selectOne(wrapper);
        if (param == null) {
            log.warn("根据[paramCode={}]查询参数为空", paramCode);
            return new ParamVo();
        }
        return TransformerHelper.transformer(param, ParamVo.class);
    }

    /**
     * <p>新增参数信息前校验</p>
     *
     * @param dto 参数
     * @author guocq
     * @date 2023/1/9 11:19
     */
    private void checkBeforeCreate(ParamCreateDto dto) {
        String paramCode = dto.getParamCode();
        Wrapper<Param> wrapper = new LambdaQueryWrapper<Param>()
                .eq(Param::getParamCode, paramCode);
        boolean exists = mapper.exists(wrapper);
        if (exists) {
            Loggers.logAetherValidError(log, "参数编码为[paramCode={}]的数据已存在，不能重复新增", paramCode);
        }
    }

    /**
     * <p>更新参数信息前校验</p>
     *
     * @param dto 参数
     * @author guocq
     * @date 2023/1/9 11:19
     */
    private void checkBeforeUpdate(ParamUpdateDto dto) {
        Long id = dto.getId();
        Wrapper<Param> wrapper = new LambdaQueryWrapper<Param>()
                .eq(Param::getId, id);
        boolean exists = mapper.exists(wrapper);
        if (!exists) {
            Loggers.logAetherValidError(log, "主键为[id={}]的数据不存在，不能进行更新维护", id);
        }
        String paramCode = dto.getParamCode();
        if (StrUtil.isNotBlank(paramCode)) {
            wrapper = new LambdaQueryWrapper<Param>()
                    .eq(Param::getParamCode, paramCode)
                    .ne(Param::getId, id);
            exists = mapper.exists(wrapper);
            if (exists) {
                Loggers.logAetherValidError(log, "参数编码为[paramCode={}]的数据已存在，不能重复更新", paramCode);
            }
        }
    }

    /**
     * <p>删除参数信息前校验</p>
     *
     * @param idSet 主键列表
     * @author guocq
     * @date 2023/1/9 11:19
     */
    private void checkBeforeDelete(Set<Long> idSet) {
        if (CollUtil.isEmpty(idSet)) {
            Loggers.logAetherValidError(log, "删除时主键集合不能为空", idSet);
        }
        Wrapper<Param> wrapper = new LambdaQueryWrapper<Param>()
                .in(Param::getId, idSet);
        Long count = mapper.selectCount(wrapper);
        int size = idSet.size();
        if (count < size) {
            log.warn("待删除的idSet={}中部分主键不存在无法删除，系统将删除已存在的数据{}条", idSet, count);
        }
    }
}
