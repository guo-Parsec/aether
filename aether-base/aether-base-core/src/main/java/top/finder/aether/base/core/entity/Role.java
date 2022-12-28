package top.finder.aether.base.core.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import top.finder.aether.data.core.entity.BaseDataEntity;

import java.util.StringJoiner;

/**
 * <p>系统角色表</p>
 *
 * @author guocq
 * @since 2022/12/27
 */
@Getter
@Setter
@TableName(value = "ams_role")
public class Role extends BaseDataEntity {
    private static final long serialVersionUID = -6190533409194671514L;
    /**
     * 角色编码
     */
    @TableField(value = "role_code")
    private String roleCode;

    /**
     * 角色名称
     */
    @TableField(value = "role_name")
    private String roleName;

    /**
     * 角色排序
     */
    @TableField(value = "role_sort")
    private Integer roleSort;

    /**
     * 角色描述信息
     */
    @TableField(value = "role_desc")
    private String roleDesc;

    @Override
    public String toString() {
        return new StringJoiner(", ", Role.class.getSimpleName() + "[", "]")
                .add("roleCode='" + roleCode + "'")
                .add("roleName='" + roleName + "'")
                .add("roleSort=" + roleSort)
                .add("roleDesc='" + roleDesc + "'")
                .add("dataStatus='" + dataStatus + "'")
                .add("gmtCreate=" + gmtCreate)
                .add("gmtModify=" + gmtModify)
                .add("id=" + id)
                .toString();
    }
}