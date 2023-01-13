package top.finder.aether.system.core.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.finder.aether.system.api.tools.DictTool;
import top.finder.aether.system.core.dto.SysDictCreateDto;
import top.finder.aether.system.core.dto.SysDictUpdateDto;
import top.finder.aether.system.core.entity.SysDict;
import top.finder.aether.system.core.mapper.SysDictMapper;
import top.finder.aether.system.core.service.SysDictService;
import top.finder.aether.system.core.vo.SysDictVo;
import top.finder.aether.common.support.helper.TransformerHelper;
import top.finder.aether.common.utils.LoggerUtil;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static top.finder.aether.system.api.support.pool.SystemCacheConstantPool.*;

/**
 * <p>数据字典服务接口实现类</p>
 *
 * @author guocq
 * @since 2022/12/29
 */
@Service(value = "dictService")
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements SysDictService {
    private static final Logger log = LoggerFactory.getLogger(SysDictServiceImpl.class);

    @Resource
    private SysDictMapper mapper;

    /**
     * <p>新增：字典信息</p>
     *
     * @param createDto 新增入参
     * @author guocq
     * @date 2022/12/29 15:03
     */
    @Override
    @CacheEvict(cacheNames = {SYSTEM_DICT_CACHE_SINGLE, SYSTEM_DICT_CACHE_LIST, SYSTEM_DICT_MODEL_CACHE_SINGLE, SYSTEM_DICT_MODEL_CACHE_LIST}, allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void create(SysDictCreateDto createDto) {
        log.debug("新增字典信息, 入参={}", createDto);
        checkBeforeCreate(createDto);
        SysDict sysDict = TransformerHelper.transformer(createDto, SysDict.class);
        mapper.insert(sysDict);
        log.debug("新增字典成功");
    }

    /**
     * <p>批量删除：角色信息</p>
     *
     * @param idSet 主键集合
     * @author guocq
     * @date 2022/12/29 15:04
     */
    @Override
    @CacheEvict(cacheNames = {SYSTEM_DICT_CACHE_SINGLE, SYSTEM_DICT_CACHE_LIST, SYSTEM_DICT_MODEL_CACHE_SINGLE, SYSTEM_DICT_MODEL_CACHE_LIST}, allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<Long> idSet) {
        log.debug("删除角色信息, 入参={}", idSet);
        checkBeforeDelete(idSet);
        mapper.logicBatchDeleteByIds(idSet, System.currentTimeMillis());
        log.debug("删除角色成功");
    }

    /**
     * <p>更新：字典信息</p>
     *
     * @param updateDto 更新入参
     * @author guocq
     * @date 2022/12/29 15:03
     */
    @Override
    @CacheEvict(cacheNames = {SYSTEM_DICT_CACHE_SINGLE, SYSTEM_DICT_CACHE_LIST, SYSTEM_DICT_MODEL_CACHE_SINGLE, SYSTEM_DICT_MODEL_CACHE_LIST}, allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update(SysDictUpdateDto updateDto) {
        log.debug("更新字典信息, 入参={}", updateDto);
        checkBeforeUpdate(updateDto);
        SysDict sysDict = TransformerHelper.transformer(updateDto, SysDict.class);
        mapper.updateById(sysDict);
        log.debug("更新字典成功");
    }

    /**
     * <p>查询：字典信息列表</p>
     *
     * @param sysDict 查询入参
     * @return {@link List < DictVo >}
     * @author guocq
     * @date 2022/12/29 15:03
     */
    @Override
    @Cacheable(cacheNames = SYSTEM_DICT_CACHE_LIST, keyGenerator = "modelKeyGenerator", unless = "#result.isEmpty()")
    public List<SysDictVo> listQuery(SysDict sysDict) {
        Wrapper<SysDict> wrapper = new LambdaQueryWrapper<SysDict>()
                .eq(ObjectUtil.isNotEmpty(sysDict.getId()), SysDict::getId, sysDict.getId())
                .eq(StrUtil.isNotBlank(sysDict.getDictTypeCode()), SysDict::getDictTypeCode, sysDict.getDictTypeCode())
                .like(StrUtil.isNotBlank(sysDict.getDictTypeName()), SysDict::getDictTypeName, sysDict.getDictTypeName())
                .eq(ObjectUtil.isNotEmpty(sysDict.getDictCode()), SysDict::getDictCode, sysDict.getDictCode())
                .like(StrUtil.isNotBlank(sysDict.getDictName()), SysDict::getDictName, sysDict.getDictName());
        List<SysDict> sysDictList = mapper.selectList(wrapper);
        return sysDictList.stream().map(ele -> TransformerHelper.transformer(ele, SysDictVo.class))
                .peek(DictTool::translate).collect(Collectors.toList());
    }

    /**
     * <p>新增字典信息前校验</p>
     *
     * @param createDto 新增入参
     * @author guocq
     * @date 2022/12/29 15:07
     */
    private void checkBeforeCreate(SysDictCreateDto createDto) {
        String dictTypeCode = createDto.getDictTypeCode();
        Integer dictCode = createDto.getDictCode();
        Wrapper<SysDict> wrapper = new LambdaQueryWrapper<SysDict>()
                .eq(SysDict::getDictTypeCode, dictTypeCode)
                .eq(SysDict::getDictCode, dictCode);
        boolean exists = mapper.exists(wrapper);
        if (exists) {
            throw LoggerUtil.logAetherValidError(log, "字典数据[dictTypeCode={},dictCode={}]的数据已存在，不能重复新增", dictTypeCode, dictCode);
        }
    }

    /**
     * <p>删除字典信息前校验</p>
     *
     * @param idSet 主键集合
     * @author guocq
     * @date 2022/12/29 15:14
     */
    private void checkBeforeDelete(Set<Long> idSet) {
        if (CollUtil.isEmpty(idSet)) {
            throw LoggerUtil.logAetherValidError(log, "删除时主键集合不能为空", idSet);
        }
        Wrapper<SysDict> wrapper = new LambdaQueryWrapper<SysDict>()
                .in(SysDict::getId, idSet);
        Long count = mapper.selectCount(wrapper);
        int size = idSet.size();
        if (count == 0) {
            throw LoggerUtil.logAetherValidError(log, "待删除的idSet={}中没有找到相应的数据，无法删除", idSet);
        }
        if (count < size) {
            log.warn("待删除的idSet={}中部分主键不存在无法删除，系统将删除已存在的数据{}条", idSet, count);
        }
    }

    /**
     * <p>更新字典信息前校验</p>
     *
     * @param updateDto 更新入参
     * @author guocq
     * @date 2022/12/29 15:14
     */
    private void checkBeforeUpdate(SysDictUpdateDto updateDto) {
        Long id = updateDto.getId();
        SysDict sysDictFromDb = mapper.selectById(id);
        boolean exists = sysDictFromDb != null && ObjectUtil.equals(id, sysDictFromDb.getId());
        if (!exists) {
            throw LoggerUtil.logAetherValidError(log, "主键为[id={}]的数据不存在，不能进行更新维护", id);
        }
        String dictTypeCode = StrUtil.isBlank(updateDto.getDictTypeCode()) ? sysDictFromDb.getDictTypeCode() : updateDto.getDictTypeCode();
        Integer dictCode = updateDto.getDictCode() == null ? sysDictFromDb.getDictCode() : updateDto.getDictCode();
        Wrapper<SysDict> wrapper = new LambdaQueryWrapper<SysDict>()
                .eq(SysDict::getDictTypeCode, dictTypeCode)
                .eq(SysDict::getDictCode, dictCode)
                .ne(SysDict::getId, id);
        exists = mapper.exists(wrapper);
        if (exists) {
            throw LoggerUtil.logAetherValidError(log, "字典数据[dictTypeCode={},dictCode={}]的数据已存在，不能重复新增", dictTypeCode, dictCode);
        }
    }
}
