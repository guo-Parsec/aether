package top.finder.aether.system.core.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.finder.aether.system.core.dto.SysUserChangePasswordDto;
import top.finder.aether.system.core.dto.SysUserUpdateDto;
import top.finder.aether.system.core.service.PersonalService;
import top.finder.aether.system.core.service.SysUserService;

/**
 * <p>个人管理业务接口实现类</p>
 *
 * @author guocq
 * @since 2023/1/19
 */
@Service(value = "personalService")
public class PersonalServiceImpl implements PersonalService {
    private static final Logger log = LoggerFactory.getLogger(PersonalServiceImpl.class);

    private final SysUserService service;

    public PersonalServiceImpl(SysUserService service) {
        this.service = service;
    }

    /**
     * <p>修改：个人用户信息</p>
     *
     * @param dto 更新入参
     * @author guocq
     * @date 2023/1/19 10:40
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePersonalInfo(SysUserUpdateDto dto) {
        service.update(dto);
    }

    /**
     * <p>修改：个人用户密码修改</p>
     *
     * @param dto 更新入参
     * @author guocq
     * @date 2023/1/19 10:41
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void changePersonalPassword(SysUserChangePasswordDto dto) {
        service.changePassword(dto);
    }
}
