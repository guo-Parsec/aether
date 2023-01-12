package top.finder.aether.base.core.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import top.finder.aether.base.core.dto.SysUserChangePasswordDto;
import top.finder.aether.base.api.dto.SysUserCreateDto;
import top.finder.aether.base.api.vo.SysUserVo;
import top.finder.aether.base.core.dto.GrantRoleToUserDto;
import top.finder.aether.base.core.dto.SysSysUserPageQueryDto;
import top.finder.aether.base.core.dto.SysUserUpdateDto;
import top.finder.aether.data.core.entity.UserDetails;

import java.util.Set;

/**
 * <p>用户操作业务接口</p>
 *
 * @author guocq
 * @since 2022/12/14
 */
public interface SysUserService {
    /**
     * <p>根据账户信息加载用户</p>
     *
     * @param account  登陆账户
     * @param password 传入需要验证的密码
     * @return {@link UserDetails}
     * @author guocq
     * @date 2022/12/28 14:55
     */
    UserDetails loadUser(String account, String password);

    /**
     * <p>根据用户名查询用户</p>
     *
     * @param account 用户名
     * @return {@link SysUserVo }
     * @author guocq
     * @date 2022/12/28 16:08
     */
    UserDetails findUserByAccount(String account);

    /**
     * <p>新增：用户信息</p>
     *
     * @param dto 新增用户
     * @author guocq
     * @date 2023/1/5 11:02
     */
    void create(SysUserCreateDto dto);

    /**
     * <p>更新：用户信息</p>
     *
     * @param dto 更新用户
     * @author guocq
     * @date 2023/1/5 11:02
     */
    void update(SysUserUpdateDto dto);

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
     * @return {@link IPage< SysUserVo >}
     * @author guocq
     * @date 2023/1/5 11:04
     */
    IPage<SysUserVo> pageQuery(SysSysUserPageQueryDto dto);

    /**
     * <p>用户修改密码</p>
     *
     * @param dto 参数
     * @author guocq
     * @date 2023/1/9 9:24
     */
    void changePassword(SysUserChangePasswordDto dto);

    /**
     * <p>重置用户</p>
     *
     * @param account 账户信息
     * @author guocq
     * @date 2023/1/9 9:41
     */
    void resetUser(String account);

    /**
     * <p>修改用户启用状态</p>
     *
     * @param account      账户
     * @param enableStatus 启用状态
     * @author guocq
     * @date 2023/1/9 9:50
     */
    void changeUserEnableStatus(String account, Integer enableStatus);

    /**
     * <p>为用户赋予角色</p>
     *
     * @param dto 入参
     * @author guocq
     * @date 2023/1/9 10:14
     */
    void grantRoleToUser(GrantRoleToUserDto dto);

    /**
     * <p>系统内部用户创建</p>
     *
     * @param dto 新增用户参数
     * @author guocq
     * @date 2023/1/5 14:58
     */
    void systemInnerUserCreate(SysUserCreateDto dto);

    /**
     * <p>注册用户创建</p>
     *
     * @param dto 新增用户参数
     * @author guocq
     * @date 2023/1/5 14:58
     */
    void registeredUserCreate(SysUserCreateDto dto);
}
