package top.finder.aether.base.api.vo;

import lombok.Getter;
import lombok.Setter;
import top.finder.aether.data.core.vo.BaseDataVo;

/**
 * <p>角色展示数据</p>
 *
 * @author guocq
 * @since 2022/12/27
 */
@Setter
@Getter
public class RoleVo extends BaseDataVo {
    private static final long serialVersionUID = -1013398678653792835L;

    public static final RoleVo EMPTY = new RoleVo();

    /**
     * 角色编码
     */
    private String roleCode;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色排序
     */
    private Integer roleSort;

    /**
     * 角色描述信息
     */
    private String roleDesc;

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getRoleSort() {
        return roleSort;
    }

    public void setRoleSort(Integer roleSort) {
        this.roleSort = roleSort;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }
}
