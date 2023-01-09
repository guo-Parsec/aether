package top.finder.aether.data.common.model;

import top.finder.aether.common.model.IModel;

/**
 * <p>参数模型接口</p>
 *
 * @author guocq
 * @since 2023/1/9
 */
public interface IParamModel extends IModel {
    /**
     * <p>获取参数类别码值</p>
     *
     * @return java.lang.String
     * @author guocq
     * @date 2023/1/9 13:54
     */
    String getParamTypeCode();

    /**
     * <p>获取参数类别名称</p>
     *
     * @return java.lang.String
     * @author guocq
     * @date 2023/1/9 13:54
     */
    String getParamTypeName();

    /**
     * <p>获取参数名称</p>
     *
     * @return java.lang.String
     * @author guocq
     * @date 2023/1/9 13:54
     */
    String getParamName();

    /**
     * <p>获取参数码值</p>
     *
     * @return java.lang.String
     * @author guocq
     * @date 2023/1/9 13:54
     */
    String getParamCode();

    /**
     * <p>获取参数值</p>
     *
     * @return java.lang.String
     * @author guocq
     * @date 2023/1/9 13:54
     */
    String getParamValue();

    /**
     * <p>设置参数类别码</p>
     *
     * @param paramTypeCode 类别码
     * @author guocq
     * @date 2023/1/9 14:00
     */
    void setParamTypeCode(String paramTypeCode);

    /**
     * <p>设置参数类别名称</p>
     *
     * @param paramTypeName 类别名称
     * @author guocq
     * @date 2023/1/9 14:00
     */
    void setParamTypeName(String paramTypeName);

    /**
     * <p>设置参数名称</p>
     *
     * @param paramName 参数名称
     * @author guocq
     * @date 2023/1/9 14:00
     */
    void setParamName(String paramName);

    /**
     * <p>设置参数码</p>
     *
     * @param paramCode 参数码
     * @author guocq
     * @date 2023/1/9 14:00
     */
    void setParamCode(String paramCode);

    /**
     * <p>设置参数值</p>
     *
     * @param paramValue 参数值
     * @author guocq
     * @date 2023/1/9 14:00
     */
    void setParamValue(String paramValue);
}
