package top.finder.aether.system.api.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import top.finder.aether.data.common.vo.BaseDataVo;

import java.util.StringJoiner;

/**
 * <p>系统资源展示层</p>
 *
 * @author guocq
 * @since 2023/1/16
 */
@Setter
@Getter
@ApiModel("系统资源展示层")
public class SysResourceVo extends BaseDataVo {
    private static final long serialVersionUID = -9208410785010167154L;

    /**
     * 资源类别码
     */
    @ApiModelProperty(value = "资源类别码")
    private String resourceTypeCode;

    /**
     * 资源类别名称
     */
    @ApiModelProperty(value = "资源类别名称")
    private String resourceTypeName;

    /**
     * 权限码值
     */
    @ApiModelProperty(value = "权限码值")
    private String resourceCode;

    /**
     * 权限名称
     */
    @ApiModelProperty(value = "权限名称")
    private String resourceName;

    /**
     * 资源路径
     */
    @ApiModelProperty(value = "资源路径")
    private String resourceUrl;

    /**
     * 资源描述
     */
    @ApiModelProperty(value = "资源描述")
    private String resourceDesc;

    @Override
    public String toString() {
        return new StringJoiner(", ", SysResourceVo.class.getSimpleName() + "[", "]")
                .add("resourceTypeCode='" + resourceTypeCode + "'")
                .add("resourceTypeName='" + resourceTypeName + "'")
                .add("resourceCode='" + resourceCode + "'")
                .add("resourceName='" + resourceName + "'")
                .add("resourceUrl='" + resourceUrl + "'")
                .add("resourceDesc='" + resourceDesc + "'")
                .add("gmtCreate=" + gmtCreate)
                .add("gmtModify=" + gmtModify)
                .add("id='" + id + "'")
                .toString();
    }
}
