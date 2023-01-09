package top.finder.aether.base.core.mapper;

import org.apache.ibatis.annotations.Param;
import top.finder.aether.base.core.entity.Role;
import top.finder.aether.data.common.mapper.CommonMapper;

import java.util.Set;

/**
 * <p>系统角色Mapper</p>
 *
 * @author guocq
 * @since 2022/12/27
 */
public interface RoleMapper extends CommonMapper<Role> {
    /**
     * <p>根据用户id查询角色码值列表</p>
     *
     * @param userId 用户id
     * @return {@link Set}
     * @author guocq
     * @date 2023/1/9 10:44
     */
    Set<String> findRoleCodeByUserId(@Param("userId") Long userId);
}