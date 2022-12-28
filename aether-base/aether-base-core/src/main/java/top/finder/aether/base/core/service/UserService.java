package top.finder.aether.base.core.service;

import top.finder.aether.base.api.vo.UserVo;

/**
 * <p>用户操作业务接口</p>
 *
 * @author guocq
 * @since 2022/12/14
 */
public interface UserService {
    /**
     * <p>根据id查询用户数据</p>
     *
     * @param id 主键
     * @return {@link UserVo}
     * @author guocq
     * @date 2022/12/14 9:48
     */
    UserVo findById(Long id);

    /**
     * <p>根据账户信息加载用户</p>
     *
     * @param account  登陆账户
     * @param password 传入需要验证的密码
     * @return {@link UserVo}
     * @author guocq
     * @date 2022/12/28 14:55
     */
    UserVo loadUser(String account, String password);

    /**
     * <p>根据用户名查询用户</p>
     *
     * @param account 用户名
     * @return {@link UserVo }
     * @author guocq
     * @date 2022/12/28 16:08
     */
    UserVo findUserByAccount(String account);
}
