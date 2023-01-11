package top.finder.aether.base.api.facade;

import top.finder.aether.base.api.model.ParamModel;

import java.util.List;
import java.util.Optional;

/**
 * <p>系统参数Facade接口</p>
 *
 * @author guocq
 * @since 2023/1/11
 */
public interface ParamFacade {
    /**
     * <p>根据参数类型查询系统参数</p>
     *
     * @param paramTypeCode 参数类型
     * @return {@link List}
     * @author guocq
     * @date 2023/1/11 13:46
     */
    List<ParamModel> findParamByType(String paramTypeCode);

    /**
     * <p>根据参数码查询系统参数</p>
     *
     * @param paramCode 参数码
     * @return {@link Optional}
     * @author guocq
     * @date 2023/1/11 13:46
     */
    Optional<ParamModel> findParamByParamCode(String paramCode);
}
