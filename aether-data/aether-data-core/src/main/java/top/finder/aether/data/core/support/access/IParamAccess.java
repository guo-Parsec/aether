package top.finder.aether.data.core.support.access;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.finder.aether.data.core.model.IParamModel;

import java.util.List;
import java.util.Map;

import static top.finder.aether.common.support.pool.CommonConstantPool.ALL_TEXT;

/**
 * <p>系统参数访问接口</p>
 *
 * @author guocq
 * @since 2023/1/9
 */
public interface IParamAccess {
    Logger log = LoggerFactory.getLogger(IParamAccess.class);
    /**
     * IParamModel list映射 key为 paramTypeCode value为IParamModelList
     */
    Map<String, List<IParamModel>> PARAM_MODELS_MAPPING = Maps.newHashMap();

    /**
     * IParamModel 映射 key为 paramCode value为IParamModelList
     */
    Map<String, IParamModel> PARAM_MODEL_MAPPING = Maps.newHashMap();

    /**
     * <p>根据参数类别码查询参数列表</p>
     *
     * @param paramTypeCode 参数类别码
     * @return {@link IParamModel}
     * @author guocq
     * @date 2023/1/9 13:49
     */
    List<IParamModel> queryParamByParamTypeCode(String paramTypeCode);

    /**
     * <p>根据参数码查询参数</p>
     *
     * @param paramCode 参数码
     * @return {@link IParamModel}
     * @author guocq
     * @date 2023/1/9 13:49
     */
    IParamModel queryParamByParamCode(String paramCode);

    /**
     * <p>根据paramTypeCode清除[PARAM_MODELS_MAPPING]缓存</p>
     *
     * @param paramTypeCode 参数类别码
     * @author guocq
     * @date 2023/1/9 14:19
     */
    default void clearParamModelsMapping(String paramTypeCode) {
        log.debug("清空[PARAM_MODELS_MAPPING]开始, 入参[paramTypeCode={}]", paramTypeCode);
        if (StrUtil.isBlank(paramTypeCode)) {
            log.error("paramTypeCode不能为空");
            return;
        }
        if (ALL_TEXT.equals(paramTypeCode)) {
            log.debug("清空[PARAM_MODELS_MAPPING]全部信息");
            PARAM_MODELS_MAPPING.clear();
            return;
        }
        PARAM_MODELS_MAPPING.remove(paramTypeCode);
    }

    /**
     * <p>清除[PARAM_MODELS_MAPPING]所有缓存</p>
     *
     * @author guocq
     * @date 2023/1/9 14:19
     */
    default void clearParamModelsMapping() {
        clearParamModelsMapping(ALL_TEXT);
    }

    /**
     * <p>根据paramCode清除[PARAM_MODEL_MAPPING]缓存</p>
     *
     * @param paramCode 参数码
     * @author guocq
     * @date 2023/1/9 14:19
     */
    default void clearParamModelMapping(String paramCode) {
        log.debug("清空[PARAM_MODEL_MAPPING]开始, 入参[paramCode={}]", paramCode);
        if (StrUtil.isBlank(paramCode)) {
            log.error("paramCode不能为空");
            return;
        }
        if (ALL_TEXT.equals(paramCode)) {
            log.debug("清空[PARAM_MODEL_MAPPING]全部信息");
            PARAM_MODEL_MAPPING.clear();
            return;
        }
        PARAM_MODEL_MAPPING.remove(paramCode);
    }

    /**
     * <p>清除[PARAM_MODEL_MAPPING]所有缓存</p>
     *
     * @author guocq
     * @date 2023/1/9 14:19
     */
    default void clearParamModelMapping() {
        clearParamModelMapping(ALL_TEXT);
    }
}
