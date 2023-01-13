package top.finder.aether.system.core.mapper;

import org.apache.ibatis.annotations.Param;
import top.finder.aether.system.core.entity.SysRole;
import top.finder.aether.data.common.mapper.CommonMapper;

import java.util.Set;

/**
 * <p>系统角色Mapper</p>
 *
 * @author guocq
 * @since 2022/12/27
 */
public interface SysRoleMapper extends CommonMapper<SysRole> {
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