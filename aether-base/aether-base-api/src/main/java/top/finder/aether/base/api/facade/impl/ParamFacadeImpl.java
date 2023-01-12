package top.finder.aether.base.api.facade.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.finder.aether.base.api.facade.ParamFacade;
import top.finder.aether.base.api.model.ParamModel;
import top.finder.aether.base.api.repository.ParamRepository;
import top.finder.aether.common.support.exception.AetherValidException;
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
public class ParamFacadeImpl implements ParamFacade {
    private static final Logger log = LoggerFactory.getLogger(ParamFacadeImpl.class);

    private final ParamRepository repository;

    public ParamFacadeImpl(ParamRepository repository) {
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
    public List<ParamModel> findParamByType(String paramTypeCode) {
        log.debug("根据参数类型[paramTypeCode={}]查询系统参数列表", paramTypeCode);
        if (StrUtil.isBlank(paramTypeCode)) {
            throw LoggerUtil.logAetherError(log, "参数类型[paramTypeCode]不能为空");
        }
        List<ParamModel> paramModels = repository.findParamByType(paramTypeCode);
        if (CollUtil.isEmpty(paramModels)) {
            log.debug("根据参数类型[paramTypeCode={}]查询系统参数为空", paramTypeCode);
            return Lists.newArrayList();
        }
        return paramModels;
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
    public Optional<ParamModel> findParamByParamCode(String paramCode) {
        log.debug("根据参数码[paramCode={}]查询系统参数", paramCode);
        if (StrUtil.isBlank(paramCode)) {
            log.error("参数码[paramCode]不能为空");
            throw LoggerUtil.logAetherError(log, "参数码[paramCode]不能为空");
        }
        return Optional.ofNullable(repository.findParamByParamCode(paramCode));
    }
}
