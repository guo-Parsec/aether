package top.finder.aether.base.core.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import top.finder.aether.base.api.vo.UserVo;
import top.finder.aether.base.api.dto.UserCreateDto;
import top.finder.aether.base.core.dto.UserPageQueryDto;
import top.finder.aether.base.core.dto.UserUpdateDto;

import java.util.Set;

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

    /**
     * <p>新增：用户信息</p>
     *
     * @param dto 新增用户
     * @author guocq
     * @date 2023/1/5 11:02
     */
    void create(UserCreateDto dto);

    /**
     * <p>更新：用户信息</p>
     *
     * @param dto 更新用户
     * @author guocq
     * @date 2023/1/5 11:02
     */
    void update(UserUpdateDto dto);

    /**
     * <p>批量删除：用户信息</p>
     *
     * @param idSet 主键集合
     * @author guocq
     * @date 2023/1/5 11:02
     */
    void delete(Set<Long> idSet);

    /**
     * <p>分页查询用户</p>
     *
     * @param dto 分页参数
     * @return {@link IPage<UserVo>}
     * @author guocq
     * @date 2023/1/5 11:04
     */
    IPage<UserVo> pageQuery(UserPageQueryDto dto);

    /**
     * <p>系统内部用户创建</p>
     *
     * @param dto 新增用户参数
     * @author guocq
     * @date 2023/1/5 14:58
     */
    void systemInnerUserCreate(UserCreateDto dto);

    /**
     * <p>注册用户创建</p>
     *
     * @param dto 新增用户参数
     * @author guocq
     * @date 2023/1/5 14:58
     */
    void registeredUserCreate(UserCreateDto dto);
}
