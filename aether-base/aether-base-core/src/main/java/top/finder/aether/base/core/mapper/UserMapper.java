package top.finder.aether.base.core.mapper;

import top.finder.aether.base.core.entity.User;
import top.finder.aether.data.core.mapper.CommonMapper;

/**
 * <p>用户与数据库交互Mapper</p>
 *
 * @author guocq
 * @since 2022/12/14
 */
public interface UserMapper extends CommonMapper<User> {
    /**
     * <p>根据id查询用户</p>
     *
     * @param id 主键
     * @return {@link User}
     * @author guocq
     * @date 2022/12/14 9:46
     */
    User findById(Long id);
}
