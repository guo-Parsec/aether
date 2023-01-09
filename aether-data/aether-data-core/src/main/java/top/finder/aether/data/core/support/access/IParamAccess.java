package top.finder.aether.data.core.support.access;

import top.finder.aether.data.core.model.IParamModel;

import java.util.List;

/**
 * <p>系统参数访问接口</p>
 *
 * @author guocq
 * @since 2023/1/9
 */
public interface IParamAccess {
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
}
