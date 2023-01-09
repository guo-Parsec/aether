package top.finder.aether.base.api.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import top.finder.aether.base.api.model.ParamModel;
import top.finder.aether.data.common.vo.BaseDataVo;
import top.finder.aether.data.common.model.IParamModel;

import java.util.StringJoiner;

/**
 * <p>系统参数数据展示层</p>
 *
 * @author guocq
 * @since 2023/1/9
 */
@ApiModel(value = "系统参数数据展示层")
@Getter
@Setter
public class ParamVo extends BaseDataVo {
    private static final long serialVersionUID = 1334359948430520729L;
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

    /**
     * 参数排序
     */
    @ApiModelProperty(value = "参数排序")
    private Integer paramSort;

    /**
     * <p>转换为IParamModel</p>
     *
     * @return {@link IParamModel}
     * @author guocq
     * @date 2023/1/9 14:03
     */
    public IParamModel toParamModel() {
        IParamModel paramModel = new ParamModel();
        paramModel.setParamTypeCode(paramTypeCode);
        paramModel.setParamTypeName(paramTypeName);
        paramModel.setParamName(paramName);
        paramModel.setParamCode(paramCode);
        paramModel.setParamValue(paramValue);
        return paramModel;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ParamVo.class.getSimpleName() + "[", "]")
                .add("paramTypeCode='" + paramTypeCode + "'")
                .add("paramTypeName='" + paramTypeName + "'")
                .add("paramName='" + paramName + "'")
                .add("paramCode='" + paramCode + "'")
                .add("paramValue='" + paramValue + "'")
                .add("paramSort=" + paramSort)
                .add("gmtCreate=" + gmtCreate)
                .add("gmtModify=" + gmtModify)
                .add("id=" + id)
                .toString();
    }
}