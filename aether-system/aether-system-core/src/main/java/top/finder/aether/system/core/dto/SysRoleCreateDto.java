package top.finder.aether.system.core.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import top.finder.aether.common.model.IModel;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
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
public class SysRoleCreateDto implements IModel {
    private static final long serialVersionUID = -8344571878810290148L;

    /**
     * 角色编码
     */
    @ApiModelProperty("角色编码")
    @NotEmpty(message = "角色编码不能为空")
    private String roleCode;

    /**
     * 角色名称
     */
    @ApiModelProperty("角色名称")
    @NotEmpty(message = "角色名称不能为空")
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
        return new StringJoiner(", ", SysRoleCreateDto.class.getSimpleName() + "[", "]")
                .add("roleCode='" + roleCode + "'")
                .add("roleName='" + roleName + "'")
                .add("roleSort=" + roleSort)
                .add("roleDesc='" + roleDesc + "'")
                .toString();
    }
}
