package top.finder.aether.base.core.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import top.finder.aether.common.model.IModel;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.StringJoiner;

/**
 * <p>系统参数更新参数</p>
 *
 * @author guocq
 * @since 2023/1/9
 */
@Getter
@Setter
@ApiModel("系统参数更新参数")
public class SysParamUpdateDto implements IModel {
    private static final long serialVersionUID = 4407496119835824294L;

    /**
     * 主键
     */
    @ApiModelProperty("主键")
    @NotNull(message = "主键不能为空")
    private Long id;

    /**
     * 参数类别码值
     */
    @Length(max = 64, message = "参数类别码值长度不能超过64")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "参数类别码值只能包含数字和英文")
    @ApiModelProperty(value = "参数类别码值")
    protected String paramTypeCode;

    /**
     * 参数类别名称
     */
    @Length(max = 64, message = "参数类别码值长度不能超过64")
    @ApiModelProperty(value = "参数类别名称")
    protected String paramTypeName;

    /**
     * 参数名称
     */
    @Length(max = 64, message = "参数名称长度不能超过64")
    @ApiModelProperty(value = "参数名称")
    protected String paramName;

    /**
     * 参数码值
     */
    @Length(max = 64, message = "参数码值长度不能超过64")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "参数码值只能包含数字和英文")
    @ApiModelProperty(value = "参数码值")
    protected String paramCode;

    /**
     * 参数值
     */
    @ApiModelProperty(value = "参数值")
    @Length(max = 64, message = "参数值长度不能超过64")
    protected String paramValue;

    /**
     * 参数排序
     */
    @ApiModelProperty(value = "参数排序")
    @Min(value = 0, message = "参数排序值不能小于0")
    @Max(value = 99999, message = "参数排序值不能大于99999")
    private Integer paramSort;

    @Override
    public String toString() {
        return new StringJoiner(", ", SysParamUpdateDto.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("paramTypeCode='" + paramTypeCode + "'")
                .add("paramTypeName='" + paramTypeName + "'")
                .add("paramName='" + paramName + "'")
                .add("paramCode='" + paramCode + "'")
                .add("paramValue='" + paramValue + "'")
                .add("paramSort=" + paramSort)
                .toString();
    }
}
