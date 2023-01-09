package top.finder.aether.base.core.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import top.finder.aether.data.common.vo.BaseDataVo;

import java.util.StringJoiner;

/**
 * <p>字典展示数据</p>
 *
 * @author guocq
 * @since 2022/12/29
 */
@Getter
@Setter
@ApiModel("字典展示数据")
public class DictVo extends BaseDataVo {
    private static final long serialVersionUID = 1467969855445500178L;

    public static final DictVo EMPTY = new DictVo();
    /**
     * 字典类别码值
     */
    @ApiModelProperty("字典类别码值")
    private String dictTypeCode;

    /**
     * 字典类别名称
     */
    @ApiModelProperty("字典类别名称")
    private String dictTypeName;

    /**
     * 字典名称
     */
    @ApiModelProperty("字典名称")
    private String dictName;

    /**
     * 字典码值
     */
    @ApiModelProperty("字典码值")
    private Integer dictCode;

    /**
     * 字典排序
     */
    @ApiModelProperty("字典排序")
    private Integer dictSort;

    @Override
    public String toString() {
        return new StringJoiner(", ", DictVo.class.getSimpleName() + "[", "]")
                .add("dictTypeCode='" + dictTypeCode + "'")
                .add("dictTypeName='" + dictTypeName + "'")
                .add("dictName='" + dictName + "'")
                .add("dictCode=" + dictCode)
                .add("dictSort=" + dictSort)
                .add("gmtCreate=" + gmtCreate)
                .add("gmtModify=" + gmtModify)
                .add("id='" + id + "'")
                .toString();
    }
}