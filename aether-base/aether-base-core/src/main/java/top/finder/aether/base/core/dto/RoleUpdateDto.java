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
 * <p>系统角色创建参数</p>
 *
 * @author guocq
 * @since 2022/12/27
 */
@Getter
@Setter
@ApiModel("系统角色创建参数")
public class RoleUpdateDto implements IModel {
    private static final long serialVersionUID = -8344571878810290148L;

    /**
     * 主键
     */
    @ApiModelProperty("主键")
    @NotNull(message = "主键不能为空")
    private Long id;

    /**
     * 角色编码
     */
    @ApiModelProperty("角色编码")
    private String roleCode;

    /**
     * 角色名称
     */
    @ApiModelProperty("角色名称")
    private String roleName;

    /**
     * 角色排序
     */
    @ApiModelProperty("角色排序")
    @Min(value = 0, message = "角色排序值不能小于0")
    @Max(value = 99999, message = "角色排序值不能大于99999")
    private Integer roleSort;

    /**
     * 角色描述信息
     */
    @ApiModelProperty("角色描述信息")
    private String roleDesc;

    @Override
    public String toString() {
        return new StringJoiner(", ", RoleUpdateDto.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("roleCode='" + roleCode + "'")
                .add("roleName='" + roleName + "'")
                .add("roleSort=" + roleSort)
                .add("roleDesc='" + roleDesc + "'")
                .toString();
    }
}
