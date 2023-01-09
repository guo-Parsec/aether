package top.finder.aether.base.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import top.finder.aether.data.core.model.IParamModel;

import java.util.StringJoiner;

/**
 * <p>参数模型</p>
 *
 * @author guocq
 * @since 2023/1/9
 */
@Setter
@Getter
@ApiModel("参数模型")
public class ParamModel implements IParamModel {
    private static final long serialVersionUID = 5896078517591280922L;

    /**
     * 参数类别码值
     */
    @ApiModelProperty(value = "参数类别码值")
    private String paramTypeCode;

    /**
     * 参数类别名称
     */
    @ApiModelProperty(value = "参数类别名称")
    private String paramTypeName;

    /**
     * 参数名称
     */
    @ApiModelProperty(value = "参数名称")
    private String paramName;

    /**
     * 参数码值
     */
    @ApiModelProperty(value = "参数码值")
    private String paramCode;

    /**
     * 参数值
     */
    @ApiModelProperty(value = "参数值")
    private String paramValue;

    @Override
    public String toString() {
        return new StringJoiner(", ", ParamModel.class.getSimpleName() + "[", "]")
                .add("paramTypeCode='" + paramTypeCode + "'")
                .add("paramTypeName='" + paramTypeName + "'")
                .add("paramName='" + paramName + "'")
                .add("paramCode='" + paramCode + "'")
                .add("paramValue='" + paramValue + "'")
                .toString();
    }
}
