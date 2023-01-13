package top.finder.aether.system.core.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import top.finder.aether.data.common.vo.BaseDataVo;

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
public class SysParamVo extends BaseDataVo {
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

    @Override
    public String toString() {
        return new StringJoiner(", ", SysParamVo.class.getSimpleName() + "[", "]")
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