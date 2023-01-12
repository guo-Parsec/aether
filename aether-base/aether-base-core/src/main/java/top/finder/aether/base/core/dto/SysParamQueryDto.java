package top.finder.aether.base.core.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import top.finder.aether.common.model.IModel;

import java.util.StringJoiner;

/**
 * <p>系统参数查询入参</p>
 *
 * @author guocq
 * @since 2023/1/9
 */
@Getter
@Setter
@ApiModel("系统参数查询入参")
public class SysParamQueryDto implements IModel {
    private static final long serialVersionUID = -5891982908163775341L;
    /**
     * 主键
     */
    @ApiModelProperty("主键")
    protected Long id;

    /**
     * 参数类别码值
     */
    @ApiModelProperty(value = "参数类别码值")
    protected String paramTypeCode;

    /**
     * 参数类别名称
     */
    @ApiModelProperty(value = "参数类别名称")
    protected String paramTypeName;

    /**
     * 参数名称
     */
    @ApiModelProperty(value = "参数名称")
    protected String paramName;

    /**
     * 参数码值
     */
    @ApiModelProperty(value = "参数码值")
    protected String paramCode;

    /**
     * 参数值
     */
    @ApiModelProperty(value = "参数值")
    protected String paramValue;

    @Override
    public String toString() {
        return new StringJoiner(", ", SysParamQueryDto.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("paramTypeCode='" + paramTypeCode + "'")
                .add("paramTypeName='" + paramTypeName + "'")
                .add("paramName='" + paramName + "'")
                .add("paramCode='" + paramCode + "'")
                .add("paramValue='" + paramValue + "'")
                .toString();
    }
}
