package top.finder.aether.base.core.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import top.finder.aether.common.model.IModel;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.StringJoiner;

/**
 * <p>系统字典更新参数</p>
 *
 * @author guocq
 * @since 2022/12/29
 */
@Getter
@Setter
@ApiModel("系统字典更新参数")
public class DictUpdateDto implements IModel {
    private static final long serialVersionUID = 1788392674960100951L;
    /**
     * 主键
     */
    @ApiModelProperty("主键")
    @NotNull(message = "主键不能为空")
    private Long id;

    /**
     * 字典类别码值
     */
    @ApiModelProperty(value = "字典类别码值")
    private String dictTypeCode;

    /**
     * 字典类别名称
     */
    @ApiModelProperty(value = "字典类别名称")
    private String dictTypeName;

    /**
     * 字典名称
     */
    @ApiModelProperty(value = "字典名称")
    private String dictName;

    /**
     * 字典码值
     */
    @ApiModelProperty(value = "字典码值")
    private Integer dictCode;

    /**
     * 字典排序
     */
    @ApiModelProperty(value = "字典排序")
    @Min(value = 0, message = "角色排序值不能小于0")
    @Max(value = 99999, message = "角色排序值不能大于99999")
    private Integer dictSort;

    @Override
    public String toString() {
        return new StringJoiner(", ", DictUpdateDto.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("dictTypeCode='" + dictTypeCode + "'")
                .add("dictTypeName='" + dictTypeName + "'")
                .add("dictName='" + dictName + "'")
                .add("dictCode=" + dictCode)
                .add("dictSort=" + dictSort)
                .toString();
    }
}
