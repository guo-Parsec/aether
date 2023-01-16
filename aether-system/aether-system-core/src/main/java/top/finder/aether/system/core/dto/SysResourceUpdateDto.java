package top.finder.aether.system.core.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import top.finder.aether.common.model.IModel;
import top.finder.aether.data.core.support.enums.ResourceType;

import javax.validation.constraints.NotNull;
import java.util.StringJoiner;

/**
 * <p>系统资源更新参数</p>
 *
 * @author guocq
 * @since 2023/1/16
 */
@Setter
@Getter
@ApiModel(value = "系统资源更新参数")
public class SysResourceUpdateDto implements IModel {
    private static final long serialVersionUID = 6675010339306807158L;

    @NotNull(message = "主键不能为空")
    @ApiModelProperty(value = "主键")
    private Long id;

    /**
     * 资源类别
     */
    @ApiModelProperty(value = "资源类别")
    private ResourceType resourceType;

    /**
     * 权限码值
     */
    @Length(min = 4, max = 50, message = "权限码值长度不能小于4，也不能超过50")
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
        return new StringJoiner(", ", SysResourceUpdateDto.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("resourceType=" + resourceType)
                .add("resourceCode='" + resourceCode + "'")
                .add("resourceName='" + resourceName + "'")
                .add("resourceUrl='" + resourceUrl + "'")
                .add("resourceDesc='" + resourceDesc + "'")
                .toString();
    }
}
