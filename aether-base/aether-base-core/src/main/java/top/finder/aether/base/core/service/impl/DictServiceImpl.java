package top.finder.aether.base.core.service.impl;

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
import top.finder.aether.base.api.tools.DictTool;
import top.finder.aether.base.core.dto.DictCreateDto;
import top.finder.aether.base.core.dto.DictUpdateDto;
import top.finder.aether.base.core.entity.Dict;
import top.finder.aether.base.core.mapper.DictMapper;
import top.finder.aether.base.core.service.DictService;
import top.finder.aether.base.core.vo.DictVo;
import top.finder.aether.common.support.helper.TransformerHelper;
import top.finder.aether.common.utils.Loggers;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static top.finder.aether.base.api.support.pool.BaseCacheConstantPool.*;

/**
 * <p>数据字典服务接口实现类</p>
 *
 * @author guocq
 * @since 2022/12/29
 */
@Service(value = "dictService")
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {
    private static final Logger log = LoggerFactory.getLogger(DictServiceImpl.class);

    @Resource
    private DictMapper dictMapper;

    /**
     * <p>新增：字典信息</p>
     *
     * @param createDto 新增入参
     * @author guocq
     * @date 2022/12/29 15:03
     */
    @Override
    @CacheEvict(cacheNames = {BASE_DICT_CACHE_SINGLE, BASE_DICT_CACHE_LIST, BASE_DICT_MODEL_CACHE_SINGLE, BASE_DICT_MODEL_CACHE_LIST}, allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void create(DictCreateDto createDto) {
        log.debug("新增字典信息, 入参={}", createDto);
        checkBeforeCreate(createDto);
        Dict dict = TransformerHelper.transformer(createDto, Dict.class);
        dictMapper.insert(dict);
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
    @CacheEvict(cacheNames = {BASE_DICT_CACHE_SINGLE, BASE_DICT_CACHE_LIST, BASE_DICT_MODEL_CACHE_SINGLE, BASE_DICT_MODEL_CACHE_LIST}, allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<Long> idSet) {
        log.debug("删除角色信息, 入参={}", idSet);
        checkBeforeDelete(idSet);
        dictMapper.logicBatchDeleteByIds(idSet, System.currentTimeMillis());
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
    @CacheEvict(cacheNames = {BASE_DICT_CACHE_SINGLE, BASE_DICT_CACHE_LIST, BASE_DICT_MODEL_CACHE_SINGLE, BASE_DICT_MODEL_CACHE_LIST}, allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update(DictUpdateDto updateDto) {
        log.debug("更新字典信息, 入参={}", updateDto);
        checkBeforeUpdate(updateDto);
        Dict dict = TransformerHelper.transformer(updateDto, Dict.class);
        dictMapper.updateById(dict);
        log.debug("更新字典成功");
    }

    /**
     * <p>查询：字典信息列表</p>
     *
     * @param dict 查询入参
     * @return {@link List < DictVo >}
     * @author guocq
     * @date 2022/12/29 15:03
     */
    @Override
    @Cacheable(cacheNames = BASE_DICT_CACHE_LIST, keyGenerator = "modelKeyGenerator", unless = "#result.isEmpty()")
    public List<DictVo> listQuery(Dict dict) {
        Wrapper<Dict> wrapper = new LambdaQueryWrapper<Dict>()
                .eq(ObjectUtil.isNotEmpty(dict.getId()), Dict::getId, dict.getId())
                .eq(StrUtil.isNotBlank(dict.getDictTypeCode()), Dict::getDictTypeCode, dict.getDictTypeCode())
                .like(StrUtil.isNotBlank(dict.getDictTypeName()), Dict::getDictTypeName, dict.getDictTypeName())
                .eq(ObjectUtil.isNotEmpty(dict.getDictCode()), Dict::getDictCode, dict.getDictCode())
                .like(StrUtil.isNotBlank(dict.getDictName()), Dict::getDictName, dict.getDictName());
        List<Dict> dictList = dictMapper.selectList(wrapper);
        return dictList.stream().map(ele -> TransformerHelper.transformer(ele, DictVo.class))
                .peek(DictTool::translate).collect(Collectors.toList());
    }

    /**
     * <p>新增字典信息前校验</p>
     *
     * @param createDto 新增入参
     * @author guocq
     * @date 2022/12/29 15:07
     */
    private void checkBeforeCreate(DictCreateDto createDto) {
        String dictTypeCode = createDto.getDictTypeCode();
        Integer dictCode = createDto.getDictCode();
        Wrapper<Dict> wrapper = new LambdaQueryWrapper<Dict>()
                .eq(Dict::getDictTypeCode, dictTypeCode)
                .eq(Dict::getDictCode, dictCode);
        boolean exists = dictMapper.exists(wrapper);
        if (exists) {
            Loggers.logAetherValidError(log, "字典数据[dictTypeCode={},dictCode={}]的数据已存在，不能重复新增", dictTypeCode, dictCode);
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
            Loggers.logAetherValidError(log, "删除时主键集合不能为空", idSet);
        }
        Wrapper<Dict> wrapper = new LambdaQueryWrapper<Dict>()
                .in(Dict::getId, idSet);
        Long count = dictMapper.selectCount(wrapper);
        int size = idSet.size();
        if (count == 0) {
            Loggers.logAetherValidError(log, "待删除的idSet={}中没有找到相应的数据，无法删除", idSet);
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
    private void checkBeforeUpdate(DictUpdateDto updateDto) {
        Long id = updateDto.getId();
        Dict dictFromDb = dictMapper.selectById(id);
        boolean exists = dictFromDb != null && ObjectUtil.equals(id, dictFromDb.getId());
        if (!exists) {
            Loggers.logAetherValidError(log, "主键为[id={}]的数据不存在，不能进行更新维护", id);
            return;
        }
        String dictTypeCode = StrUtil.isBlank(updateDto.getDictTypeCode()) ? dictFromDb.getDictTypeCode() : updateDto.getDictTypeCode();
        Integer dictCode = updateDto.getDictCode() == null ? dictFromDb.getDictCode() : updateDto.getDictCode();
        Wrapper<Dict> wrapper = new LambdaQueryWrapper<Dict>()
                .eq(Dict::getDictTypeCode, dictTypeCode)
                .eq(Dict::getDictCode, dictCode)
                .ne(Dict::getId, id);
        exists = dictMapper.exists(wrapper);
        if (exists) {
            Loggers.logAetherValidError(log, "字典数据[dictTypeCode={},dictCode={}]的数据已存在，不能重复新增", dictTypeCode, dictCode);
        }
    }
}
