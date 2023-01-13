package top.finder.aether.system.core.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import top.finder.aether.data.common.vo.BaseDataVo;

/**
 * <p>角色展示数据</p>
 *
 * @author guocq
 * @since 2022/12/27
 */
@Setter
@Getter
@ApiModel("角色展示数据")
public class SysRoleVo extends BaseDataVo {
    private static final long serialVersionUID = -1013398678653792835L;

    public static final SysRoleVo EMPTY = new SysRoleVo();

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
    private Integer roleSort;

    /**
     * 角色描述信息
     */
    @ApiModelProperty("角色描述信息")
    private String roleDesc;
}
