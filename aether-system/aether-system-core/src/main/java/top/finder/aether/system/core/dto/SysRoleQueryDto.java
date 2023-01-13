package top.finder.aether.system.core.dto;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import top.finder.aether.common.model.IModel;
import top.finder.aether.system.core.entity.SysRole;

import java.util.StringJoiner;

/**
 * <p>系统角色查询入参</p>
 *
 * @author guocq
 * @since 2023/1/13
 */
@Setter
@Getter
public class SysRoleQueryDto implements IModel {
    private static final long serialVersionUID = -2929351937225661944L;

    /**
     * 角色主键
     */
    @ApiModelProperty(value = "主键")
    protected Long id;

    /**
     * 角色编码
     */
    @ApiModelProperty(value = "角色编码")
    protected String roleCode;

    /**
     * 角色名称
     */
    @ApiModelProperty(value = "角色名称")
    protected String roleName;

    /**
     * 角色描述信息
     */
    @ApiModelProperty(value = "角色描述信息")
    protected String roleDesc;

    /**
     * <p>获取查询通用wrapper</p>
     *
     * @return {@link LambdaQueryWrapper}
     * @author guocq
     * @date 2023/1/13 15:30
     */
    public LambdaQueryWrapper<SysRole> getCommonWrapper() {
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ObjectUtil.isNotNull(this.id), SysRole::getId, this.id)
                .eq(StrUtil.isNotBlank(this.roleCode), SysRole::getRoleCode, this.roleCode)
                .like(StrUtil.isNotBlank(this.roleName), SysRole::getRoleName, this.roleName)
                .like(ObjectUtil.isNotNull(this.roleDesc), SysRole::getRoleDesc, this.roleDesc)
                .orderByAsc(SysRole::getRoleSort)
                .orderByDesc(SysRole::getGmtModify)
                .orderByDesc(SysRole::getGmtCreate);
        return wrapper;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", SysRoleQueryDto.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("roleCode='" + roleCode + "'")
                .add("roleName='" + roleName + "'")
                .add("roleDesc='" + roleDesc + "'")
                .toString();
    }
}
