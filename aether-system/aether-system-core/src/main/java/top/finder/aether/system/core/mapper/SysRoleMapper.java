package top.finder.aether.system.core.mapper;

import org.apache.ibatis.annotations.Param;
import top.finder.aether.data.common.mapper.CommonMapper;
import top.finder.aether.system.core.entity.SysRole;

import java.util.Set;

/**
 * <p>系统角色Mapper</p>
 *
 * @author guocq
 * @since 2022/12/27
 */
public interface SysRoleMapper extends CommonMapper<SysRole> {
    /**
     * <p>根据角色id删除角色绑定的资源</p>
     *
     * @param roleId 角色id
     * @author guocq
     * @date 2023/1/9 10:30
     */
    void unbindResourceOfRole(@Param(value = "roleId") Long roleId);

    /**
     * <p>绑定用户的角色</p>
     *
     * @param roleId      角色id
     * @param resourceIds 资源id列表
     * @author guocq
     * @date 2023/1/9 10:30
     */
    void bindResourceOfUser(@Param(value = "roleId") Long roleId, @Param("resourceIds") Set<Long> resourceIds);
}