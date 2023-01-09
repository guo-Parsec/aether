package top.finder.aether.base.api.client;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import top.finder.aether.base.api.model.ParamModel;
import top.finder.aether.base.api.support.pool.BaseApiConstantPool;
import top.finder.aether.base.api.vo.ParamVo;
import top.finder.aether.common.support.api.Apis;
import top.finder.aether.common.support.exception.AetherValidException;
import top.finder.aether.common.support.pool.AppConstantPool;
import top.finder.aether.data.common.model.IParamModel;
import top.finder.aether.data.common.support.access.IParamAccess;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>系统参数对外接口</p>
 *
 * @author guocq
 * @since 2023/1/9
 */
@FeignClient(name = AppConstantPool.APP_BASE + BaseApiConstantPool.PARAM_WEB_API_PREFIX, contextId = "paramClient")
public interface ParamClient extends IParamAccess {
    Logger log = LoggerFactory.getLogger(ParamClient.class);

    /**
     * <p>根据参数类别码查询参数列表</p>
     *
     * @param paramTypeCode 参数类别码
     * @return {@link Apis}
     * @author guocq
     * @date 2023/1/9 13:44
     */
    @GetMapping(value = "/find-param-by-param-type-code")
    Apis<List<ParamVo>> findParamByParamTypeCode(@RequestParam(value = "paramTypeCode") String paramTypeCode);

    /**
     * <p>根据参数类别码查询参数列表</p>
     *
     * @param paramTypeCode 参数类别码
     * @return {@link List}
     * @author guocq
     * @date 2023/1/9 13:49
     * @see ParamClient#findParamByParamTypeCode(String)
     */
    @Override
    default List<IParamModel> queryParamByParamTypeCode(String paramTypeCode) {
        if (StrUtil.isBlank(paramTypeCode)) {
            log.error("参数类别码不能为空");
            throw new AetherValidException("参数类别码不能为空");
        }
        boolean containsKey = PARAM_MODELS_MAPPING.containsKey(paramTypeCode);
        if (containsKey) {
            return PARAM_MODELS_MAPPING.get(paramTypeCode);
        }
        List<ParamVo> paramVos = Apis.getApiDataNoException(findParamByParamTypeCode(paramTypeCode));
        if (paramVos == null) {
            return Lists.newArrayList();
        }
        List<IParamModel> iParamModels = paramVos.stream().map(ParamVo::toParamModel).collect(Collectors.toList());
        PARAM_MODELS_MAPPING.put(paramTypeCode, iParamModels);
        return iParamModels;
    }

    /**
     * <p>根据参数码查询参数</p>
     *
     * @param paramCode 参数码
     * @return {@link Apis}
     * @author guocq
     * @date 2023/1/9 13:44
     */
    @GetMapping(value = "/find-param-by-param-code")
    Apis<ParamVo> findParamByParamCode(@RequestParam(value = "paramCode") String paramCode);

    /**
     * <p>根据参数码查询参数</p>
     *
     * @param paramCode 参数码
     * @return {@link ParamVo}
     * @author guocq
     * @date 2023/1/9 13:49
     * @see ParamClient#findParamByParamTypeCode(String)
     */
    @Override
    default IParamModel queryParamByParamCode(String paramCode) {
        if (StrUtil.isBlank(paramCode)) {
            log.error("参数码不能为空");
            throw new AetherValidException("参数码不能为空");
        }
        boolean containsKey = PARAM_MODEL_MAPPING.containsKey(paramCode);
        if (containsKey) {
            return PARAM_MODEL_MAPPING.get(paramCode);
        }
        ParamVo paramVo = Apis.getApiDataNoException(findParamByParamCode(paramCode));
        if (paramVo == null) {
            return new ParamModel();
        }
        IParamModel paramModel = paramVo.toParamModel();
        PARAM_MODEL_MAPPING.put(paramCode, paramModel);
        return paramModel;
    }
}
