package top.finder.aether.base.api.access.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.finder.aether.base.api.access.ParamAccess;
import top.finder.aether.base.api.model.ParamModel;
import top.finder.aether.base.api.repository.ParamRepository;
import top.finder.aether.common.support.exception.AetherValidException;

import java.util.List;
import java.util.Optional;

/**
 * <p></p>
 *
 * @author guocq
 * @since 2023/1/11
 */
@Transactional(readOnly = true)
@Service(value = "paramAccess")
public class ParamAccessImpl implements ParamAccess {
    private static final Logger log = LoggerFactory.getLogger(ParamAccessImpl.class);

    private final ParamRepository repository;

    public ParamAccessImpl(ParamRepository repository) {
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
    public List<ParamModel> findParamByType(String paramTypeCode) {
        log.debug("根据参数类型[paramTypeCode={}]查询系统参数列表", paramTypeCode);
        if (StrUtil.isBlank(paramTypeCode)) {
            log.error("参数类型[paramTypeCode]不能为空");
            throw new AetherValidException("参数类型[paramTypeCode]不能为空");
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
    public Optional<ParamModel> findParamByParamCode(String paramCode) {
        log.debug("根据参数码[paramCode={}]查询系统参数", paramCode);
        if (StrUtil.isBlank(paramCode)) {
            log.error("参数码[paramCode]不能为空");
            throw new AetherValidException("参数码[paramCode]不能为空");
        }
        return Optional.ofNullable(repository.findParamByParamCode(paramCode));
    }
}
