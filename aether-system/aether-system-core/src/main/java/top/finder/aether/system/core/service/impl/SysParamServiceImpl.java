package top.finder.aether.system.core.service.impl;

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
import top.finder.aether.system.core.vo.SysParamVo;
import top.finder.aether.system.core.dto.SysParamCreateDto;
import top.finder.aether.system.core.dto.SysSysParamPageQueryDto;
import top.finder.aether.system.core.dto.SysParamQueryDto;
import top.finder.aether.system.core.dto.SysParamUpdateDto;
import top.finder.aether.system.core.entity.SysParam;
import top.finder.aether.system.core.mapper.SysParamMapper;
import top.finder.aether.system.core.service.SysParamService;
import top.finder.aether.common.support.helper.TransformerHelper;
import top.finder.aether.common.utils.LoggerUtil;
import top.finder.aether.data.core.support.helper.PageHelper;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static top.finder.aether.system.api.support.pool.SystemCacheNameConstantPool.*;

/**
 * <p>系统参数服务接口实现类</p>
 *
 * @author guocq
 * @since 2023/1/9
 */
@Service(value = "paramService")
public class SysParamServiceImpl implements SysParamService {
    private static final Logger log = LoggerFactory.getLogger(SysParamServiceImpl.class);

    @Resource
    private SysParamMapper mapper;

    /**
     * <p>根据字典查询参数列表</p>
     *
     * @param dto 参数
     * @return {@link List}
     * @author guocq
     * @date 2023/1/9 11:13
     */
    @Override
    @Cacheable(cacheNames = M_VO_PARAM, keyGenerator = "modelKeyGenerator", unless = "#result.isEmpty()")
    public List<SysParamVo> list(SysParamQueryDto dto) {
        log.debug("根据字典查询参数列表, 入参={}", dto);
        List<SysParam> sysParams = mapper.selectList(findQueryWrapper(dto));
        return sysParams.stream().map(record -> TransformerHelper.transformer(record, SysParamVo.class)).collect(Collectors.toList());
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
    public IPage<SysParamVo> page(SysSysParamPageQueryDto dto) {
        log.debug("根据字典查询参数分页列表, 入参={}", dto);
        IPage<SysParam> page = PageHelper.initPage(dto);
        IPage<SysParam> rawPage = mapper.selectPage(page, findQueryWrapper(dto));
        return rawPage.convert(record -> TransformerHelper.transformer(record, SysParamVo.class));
    }

    /**
     * <p>获取查询wrapper</p>
     *
     * @param dto 查询入参
     * @return {@link Wrapper}
     * @author guocq
     * @date 2023/1/9 11:30
     */
    private Wrapper<SysParam> findQueryWrapper(SysParamQueryDto dto) {
        return new LambdaQueryWrapper<SysParam>()
                .eq(ObjectUtil.isNotNull(dto.getId()), SysParam::getId, dto.getId())
                .eq(StrUtil.isNotBlank(dto.getParamTypeCode()), SysParam::getParamTypeCode, dto.getParamTypeCode())
                .like(StrUtil.isNotBlank(dto.getParamTypeName()), SysParam::getParamTypeName, dto.getParamTypeName())
                .eq(StrUtil.isNotBlank(dto.getParamCode()), SysParam::getParamCode, dto.getParamCode())
                .like(StrUtil.isNotBlank(dto.getParamName()), SysParam::getParamName, dto.getParamName());
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
    @CacheEvict(cacheNames = {P_PARAM}, allEntries = true)
    public void create(SysParamCreateDto dto) {
        log.debug("系统参数新增, 入参={}", dto);
        checkBeforeCreate(dto);
        SysParam sysParam = TransformerHelper.transformer(dto, SysParam.class);
        mapper.insert(sysParam);
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
    @CacheEvict(cacheNames = {P_PARAM}, allEntries = true)
    public void update(SysParamUpdateDto dto) {
        log.debug("系统参数更新, 入参={}", dto);
        checkBeforeUpdate(dto);
        SysParam sysParam = TransformerHelper.transformer(dto, SysParam.class);
        mapper.updateById(sysParam);
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
    @CacheEvict(cacheNames = {P_PARAM}, allEntries = true)
    public void delete(Set<Long> idSet) {
        log.debug("系统参数批量删除, 入参={}", idSet);
        checkBeforeDelete(idSet);
        mapper.logicBatchDeleteByIds(idSet, System.currentTimeMillis());
        log.debug("系统参数批量删除成功");
    }

    /**
     * <p>新增参数信息前校验</p>
     *
     * @param dto 参数
     * @author guocq
     * @date 2023/1/9 11:19
     */
    private void checkBeforeCreate(SysParamCreateDto dto) {
        String paramCode = dto.getParamCode();
        Wrapper<SysParam> wrapper = new LambdaQueryWrapper<SysParam>()
                .eq(SysParam::getParamCode, paramCode);
        boolean exists = mapper.exists(wrapper);
        if (exists) {
            throw LoggerUtil.logAetherValidError(log, "参数编码为[paramCode={}]的数据已存在，不能重复新增", paramCode);
        }
    }

    /**
     * <p>更新参数信息前校验</p>
     *
     * @param dto 参数
     * @author guocq
     * @date 2023/1/9 11:19
     */
    private void checkBeforeUpdate(SysParamUpdateDto dto) {
        Long id = dto.getId();
        Wrapper<SysParam> wrapper = new LambdaQueryWrapper<SysParam>()
                .eq(SysParam::getId, id);
        boolean exists = mapper.exists(wrapper);
        if (!exists) {
            throw LoggerUtil.logAetherValidError(log, "主键为[id={}]的数据不存在，不能进行更新维护", id);
        }
        String paramCode = dto.getParamCode();
        if (StrUtil.isNotBlank(paramCode)) {
            wrapper = new LambdaQueryWrapper<SysParam>()
                    .eq(SysParam::getParamCode, paramCode)
                    .ne(SysParam::getId, id);
            exists = mapper.exists(wrapper);
            if (exists) {
                throw LoggerUtil.logAetherValidError(log, "参数编码为[paramCode={}]的数据已存在，不能重复更新", paramCode);
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
            throw LoggerUtil.logAetherValidError(log, "删除时主键集合不能为空", idSet);
        }
        Wrapper<SysParam> wrapper = new LambdaQueryWrapper<SysParam>()
                .in(SysParam::getId, idSet);
        Long count = mapper.selectCount(wrapper);
        int size = idSet.size();
        if (count < size) {
            log.warn("待删除的idSet={}中部分主键不存在无法删除，系统将删除已存在的数据{}条", idSet, count);
        }
    }
}
