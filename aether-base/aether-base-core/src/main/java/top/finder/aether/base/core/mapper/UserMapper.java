package top.finder.aether.base.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.finder.aether.base.core.entity.User;

/**
 * <p>用户与数据库交互Mapper</p>
 *
 * @author guocq
 * @since 2022/12/14
 */
public interface UserMapper extends BaseMapper<User> {
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