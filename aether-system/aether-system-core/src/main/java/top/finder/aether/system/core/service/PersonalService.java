package top.finder.aether.system.core.service;

import top.finder.aether.system.core.dto.SysUserChangePasswordDto;
import top.finder.aether.system.core.dto.SysUserUpdateDto;

/**
 * <p>个人管理业务接口</p>
 *
 * @author guocq
 * @since 2023/1/19
 */
public interface PersonalService {
    /**
     * <p>修改：个人用户信息</p>
     *
     * @param dto 更新入参
     * @author guocq
     * @date 2023/1/19 10:40
     */
    void updatePersonalInfo(SysUserUpdateDto dto);

    /**
     * <p>修改：个人用户密码修改</p>
     *
     * @param dto 更新入参
     * @author guocq
     * @date 2023/1/19 10:41
     */
    void changePersonalPassword(SysUserChangePasswordDto dto);
}
