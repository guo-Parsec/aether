package top.finder.aether.base.core.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import top.finder.aether.data.common.entity.BaseDataEntity;

import java.util.StringJoiner;

/**
 * <p>系统参数表</p>
 *
 * @author guocq
 * @since 2023/1/9
 */
@ApiModel(value = "系统参数表")
@Getter
@Setter
@TableName(value = "ams_param")
public class Param extends BaseDataEntity {
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
        return new StringJoiner(", ", Param.class.getSimpleName() + "[", "]")
                .add("paramTypeCode='" + paramTypeCode + "'")
                .add("paramTypeName='" + paramTypeName + "'")
                .add("paramName='" + paramName + "'")
                .add("paramCode='" + paramCode + "'")
                .add("paramValue='" + paramValue + "'")
                .add("paramSort=" + paramSort)
                .add("deleteAt=" + deleteAt)
                .add("gmtCreate=" + gmtCreate)
                .add("gmtModify=" + gmtModify)
                .add("id=" + id)
                .toString();
    }
}