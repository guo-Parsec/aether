package top.finder.aether.security.core.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import top.finder.aether.base.api.client.UserClient;
import top.finder.aether.base.api.support.pool.BaseConstantPool;
import top.finder.aether.base.api.vo.UserVo;
import top.finder.aether.common.support.api.Apis;
import top.finder.aether.common.support.strategy.CryptoStrategy;
import top.finder.aether.common.utils.LoggerUtil;
import top.finder.aether.security.api.entity.SecuritySignature;
import top.finder.aether.security.api.facade.SecurityFacade;
import top.finder.aether.security.core.service.LoginService;

import javax.annotation.Resource;

/**
 * <p>登录服务接口实现类</p>
 *
 * @author guocq
 * @since 2022/12/28
 */
@Service(value = "loginService")
public class LoginServiceImpl implements LoginService {
    private static final Logger log = LoggerFactory.getLogger(LoginServiceImpl.class);

    private CryptoStrategy strategy;

    @Resource
    private UserClient userClient;

    @Autowired
    public CryptoStrategy setStrategy(@Qualifier("md5SaltCrypto") CryptoStrategy strategy) {
        return strategy;
    }

    /**
     * <p>登录操作</p>
     *
     * @param account    账户信息
     * @param password   密码信息
     * @param verifyCode 验证码信息
     * @return {@link SecuritySignature}
     * @author guocq
     * @date 2022/12/28 15:25
     */
    @Override
    public SecuritySignature login(String account, String password, String verifyCode) {
        Apis<UserVo> userVoApis = userClient.loadUser(account, password);
        UserVo userVo = Apis.getApiDataNoException(userVoApis);
        if (userVo == null || !userVo.getCertified()) {
            throw LoggerUtil.logAetherError(log, "用户账户信息[{}]与密码[{}]匹配错误", account, password);
        }
        if (userVo.getEnableStatus() == null || !userVo.getEnableStatus().equals(BaseConstantPool.ENABLE_STATUS_ENABLE)) {
            throw LoggerUtil.logAetherError(log, "用户[account={}]已被禁用", account);
        }
        SecuritySignature signature = new SecuritySignature(userVo);
        SecurityFacade.login(signature);
        // todo 角色 权限 填充
        return signature;
    }
}
