package top.finder.aether.base.core.mapper;

import org.apache.ibatis.annotations.Param;
import top.finder.aether.base.core.entity.User;
import top.finder.aether.data.common.mapper.CommonMapper;

import java.util.Set;

/**
 * <p>用户与数据库交互Mapper</p>
 *
 * @author guocq
 * @since 2022/12/14
 */
public interface UserMapper extends CommonMapper<User> {
    /**
     * <p>根据用户id删除用户绑定的角色</p>
     *
     * @param userId 用户id
     * @author guocq
     * @date 2023/1/9 10:30
     */
    void unbindRoleOfUser(@Param(value = "userId") Long userId);

    /**
     * <p>绑定用户的角色</p>
     *
     * @param userId 用户id
     * @param roleIds 角色id列表
     * @author guocq
     * @date 2023/1/9 10:30
     */
    void bindRoleOfUser(@Param(value = "userId") Long userId, @Param("roleIds")Set<Long> roleIds);
}
