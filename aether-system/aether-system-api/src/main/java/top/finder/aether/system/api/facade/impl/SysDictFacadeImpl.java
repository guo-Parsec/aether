package top.finder.aether.system.api.facade.impl;

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
import top.finder.aether.system.api.facade.SysDictFacade;
import top.finder.aether.system.api.holders.SysDictHolders;
import top.finder.aether.system.api.repository.SysDictRepository;
import top.finder.aether.common.utils.LoggerUtil;

import java.util.List;
import java.util.Optional;

import static top.finder.aether.system.api.support.pool.dict.DictCacheConstantPool.M_HO_DICT;
import static top.finder.aether.system.api.support.pool.dict.DictCacheConstantPool.S_OHO_DICT;

/**
 * <p>数据字典Facade接口实现</p>
 *
 * @author guocq
 * @since 2023/1/11
 */
@Transactional(readOnly = true)
@Service(value = "dictFacade")
public class SysDictFacadeImpl implements SysDictFacade {
    private static final Logger log = LoggerFactory.getLogger(SysDictFacadeImpl.class);
    private final SysDictRepository repository;

    public SysDictFacadeImpl(SysDictRepository repository) {
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
    @Cacheable(cacheNames = M_HO_DICT, key = "'dictTypeCode:' + #dictTypeCode")
    public List<SysDictHolders> findDictByType(String dictTypeCode) {
        log.debug("根据字典类型[dictTypeCode={}]查询字典列表", dictTypeCode);
        if (StrUtil.isBlank(dictTypeCode)) {
            throw LoggerUtil.logAetherError(log, "字典类型码[dictTypeCode]不能为空");
        }
        List<SysDictHolders> dictList = repository.findDictByType(dictTypeCode);
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
    @Cacheable(cacheNames = S_OHO_DICT, key = "'dictTypeCode:' + #dictTypeCode + ':dictCode:' + #dictCode")
    public Optional<SysDictHolders> findDictByTypeAndCode(String dictTypeCode, Integer dictCode) {
        log.debug("根据字典类型[dictTypeCode={}]和字典码[dictCode={}]查询数据字典", dictTypeCode, dictCode);
        if (StrUtil.isBlank(dictTypeCode)) {
            log.error("字典类型码[dictTypeCode]不能为空");
            throw LoggerUtil.logAetherError(log, "字典类型码[dictTypeCode]不能为空");
        }
        if (ObjectUtil.isEmpty(dictCode)) {
            log.error("字典码[dictCode]不能为空");
            throw LoggerUtil.logAetherError(log, "字典码[dictCode]不能为空");
        }
        SysDictFacade sysDictFacade = SpringUtil.getBean(SysDictFacade.class);
        List<SysDictHolders> sysDictHoldersList = sysDictFacade.findDictByType(dictTypeCode);
        if (CollUtil.isEmpty(sysDictHoldersList)) {
            return Optional.empty();
        }
        return sysDictHoldersList.stream().filter(dictModel -> ObjectUtil.equals(dictCode, dictModel.getDictCode())).findFirst();
    }
}
