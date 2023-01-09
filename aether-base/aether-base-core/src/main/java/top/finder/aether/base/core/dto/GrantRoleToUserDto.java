package top.finder.aether.base.core.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import top.finder.aether.common.model.IModel;

import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.StringJoiner;

/**
 * <p>用户赋予角色参数</p>
 *
 * @author guocq
 * @since 2023/1/9
 */
@Setter
@Getter
@ApiModel(value = "用户赋予角色参数")
public class GrantRoleToUserDto implements IModel {
    private static final long serialVersionUID = -8224588158491212646L;

    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    @NotNull(message = "用户id不能为空")
    private Long id;

    /**
     * 角色id集合
     */
    @ApiModelProperty(value = "角色id集合")
    @NotNull(message = "角色id集合不能为空")
    private Set<Long> roleId;

    @Override
    public String toString() {
        return new StringJoiner(", ", GrantRoleToUserDto.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("roleId=" + roleId)
                .toString();
    }
}
