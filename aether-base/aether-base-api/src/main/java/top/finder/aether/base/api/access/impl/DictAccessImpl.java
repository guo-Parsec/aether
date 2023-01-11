package top.finder.aether.base.api.access.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.finder.aether.base.api.access.DictAccess;
import top.finder.aether.base.api.model.DictModel;
import top.finder.aether.base.api.repository.DictRepository;
import top.finder.aether.common.support.exception.AetherValidException;

import java.util.List;
import java.util.Optional;

import static top.finder.aether.base.api.support.pool.BaseCacheConstantPool.BASE_DICT_CACHE_LIST;
import static top.finder.aether.base.api.support.pool.BaseCacheConstantPool.BASE_DICT_CACHE_SINGLE;

/**
 * <p>数据字典访问接口实现</p>
 *
 * @author guocq
 * @since 2023/1/11
 */
@Transactional(readOnly = true)
@Service(value = "dictAccess")
public class DictAccessImpl implements DictAccess {
    private static final Logger log = LoggerFactory.getLogger(DictAccessImpl.class);
    private final DictRepository repository;

    public DictAccessImpl(DictRepository repository) {
        this.repository = repository;
    }

    /**
     * <p>根据字典类型查询字典列表</p>
     *
     * @param dictTypeCode 字典类型
     * @return {@link List}
     * @author guocq
     * @date 2023/1/11 9:20
     */
    @Override
    @Cacheable(cacheNames = BASE_DICT_CACHE_LIST, key = "'dictTypeCode:' + #dictTypeCode")
    public List<DictModel> findDictByType(String dictTypeCode) {
        log.debug("根据字典类型[dictTypeCode={}]查询字典列表", dictTypeCode);
        if (StrUtil.isBlank(dictTypeCode)) {
            log.error("字典类型码[dictTypeCode]不能为空");
            throw new AetherValidException("字典类型码[dictTypeCode]不能为空");
        }
        List<DictModel> dictList = repository.findDictByType(dictTypeCode);
        if (CollUtil.isEmpty(dictList)) {
            log.debug("根据字典类型[dictTypeCode={}]查询字典列表为空", dictTypeCode);
            return Lists.newArrayList();
        }
        return dictList;
    }

    /**
     * <p>根据字典类型和字典码查询唯一字典</p>
     *
     * @param dictTypeCode 字典类型
     * @param dictCode     字典码
     * @return {@link Optional}
     * @author guocq
     * @date 2023/1/11 9:43
     */
    @Override
    @Cacheable(cacheNames = BASE_DICT_CACHE_SINGLE, key = "'dictTypeCode:' + #dictTypeCode + ':dictCode:' + #dictCode")
    public Optional<DictModel> findDictByTypeAndCode(String dictTypeCode, Integer dictCode) {
        log.debug("根据字典类型[dictTypeCode={}]和字典码[dictCode={}]查询数据字典", dictTypeCode, dictCode);
        if (StrUtil.isBlank(dictTypeCode)) {
            log.error("字典类型码[dictTypeCode]不能为空");
            throw new AetherValidException("字典类型码[dictTypeCode]不能为空");
        }
        if (ObjectUtil.isEmpty(dictCode)) {
            log.error("字典码[dictCode]不能为空");
            throw new AetherValidException("字典码[dictCode]不能为空");
        }
        DictAccess dictAccess = SpringUtil.getBean(DictAccess.class);
        List<DictModel> dictModelList = dictAccess.findDictByType(dictTypeCode);
        if (CollUtil.isEmpty(dictModelList)) {
            return Optional.empty();
        }
        return dictModelList.stream().filter(dictModel -> ObjectUtil.equals(dictCode, dictModel.getDictCode())).findFirst();
    }
}
