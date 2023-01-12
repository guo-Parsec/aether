package top.finder.aether.base.api.facade.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.finder.aether.base.api.facade.SysParamFacade;
import top.finder.aether.base.api.holders.SysParamHolders;
import top.finder.aether.base.api.repository.SysParamRepository;
import top.finder.aether.common.utils.LoggerUtil;

import java.util.List;
import java.util.Optional;

import static top.finder.aether.base.api.support.pool.BaseCacheConstantPool.BASE_PARAM_MODEL_CACHE_LIST;
import static top.finder.aether.base.api.support.pool.BaseCacheConstantPool.BASE_PARAM_MODEL_CACHE_SINGLE;

/**
 * <p>系统参数Facade接口实现</p>
 *
 * @author guocq
 * @since 2023/1/11
 */
@Transactional(readOnly = true)
@Service(value = "paramFacade")
public class SysParamFacadeImpl implements SysParamFacade {
    private static final Logger log = LoggerFactory.getLogger(SysParamFacadeImpl.class);

    private final SysParamRepository repository;

    public SysParamFacadeImpl(SysParamRepository repository) {
        this.repository = repository;
    }

    /**
     * <p>根据参数类型查询系统参数</p>
     *
     * @param paramTypeCode 参数类型
     * @return {@link List}
     * @author guocq
     * @date 2023/1/11 13:46
     */
    @Override
    @Cacheable(cacheNames = BASE_PARAM_MODEL_CACHE_LIST, key = "'paramTypeCode:' + #paramTypeCode")
    public List<SysParamHolders> findParamByType(String paramTypeCode) {
        log.debug("根据参数类型[paramTypeCode={}]查询系统参数列表", paramTypeCode);
        if (StrUtil.isBlank(paramTypeCode)) {
            throw LoggerUtil.logAetherError(log, "参数类型[paramTypeCode]不能为空");
        }
        List<SysParamHolders> sysParamHolders = repository.findParamByType(paramTypeCode);
        if (CollUtil.isEmpty(sysParamHolders)) {
            log.debug("根据参数类型[paramTypeCode={}]查询系统参数为空", paramTypeCode);
            return Lists.newArrayList();
        }
        return sysParamHolders;
    }

    /**
     * <p>根据参数码查询系统参数</p>
     *
     * @param paramCode 参数码
     * @return {@link Optional}
     * @author guocq
     * @date 2023/1/11 13:46
     */
    @Override
    @Cacheable(cacheNames = BASE_PARAM_MODEL_CACHE_SINGLE, key = "'paramCode:' + #paramCode")
    public Optional<SysParamHolders> findParamByParamCode(String paramCode) {
        log.debug("根据参数码[paramCode={}]查询系统参数", paramCode);
        if (StrUtil.isBlank(paramCode)) {
            log.error("参数码[paramCode]不能为空");
            throw LoggerUtil.logAetherError(log, "参数码[paramCode]不能为空");
        }
        return Optional.ofNullable(repository.findParamByParamCode(paramCode));
    }
}
