package top.finder.aether.system.core.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import top.finder.aether.common.model.IModel;

import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.StringJoiner;

/**
 * <p>角色赋予权限参数</p>
 *
 * @author guocq
 * @since 2023/1/9
 */
@Setter
@Getter
@ApiModel(value = "角色赋予权限参数")
public class GrantResourceToRoleDto implements IModel {
    private static final long serialVersionUID = -8224588158491212646L;

    /**
     * 角色id
     */
    @ApiModelProperty(value = "角色id")
    @NotNull(message = "角色id不能为空")
    private Long id;

    /**
     * 资源id集合
     */
    @ApiModelProperty(value = "资源id集合")
    @NotNull(message = "资源id集合不能为空")
    private Set<Long> resourceId;

    @Override
    public String toString() {
        return new StringJoiner(", ", GrantResourceToRoleDto.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("resourceId=" + resourceId)
                .toString();
    }
}
