package top.finder.aether.auth.core.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import top.finder.aether.auth.core.model.SecuritySubject;
import top.finder.aether.auth.core.service.LoginService;
import top.finder.aether.base.api.client.UserClient;
import top.finder.aether.base.api.vo.UserVo;
import top.finder.aether.common.support.api.Apis;
import top.finder.aether.common.support.helper.CodeHelper;
import top.finder.aether.common.support.strategy.CryptoStrategy;
import top.finder.aether.data.security.core.ISecuritySubject;
import top.finder.aether.data.security.core.SecurityContext;

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
     * @return {@link SecuritySubject}
     * @author guocq
     * @date 2022/12/28 15:25
     */
    @Override
    public SecuritySubject login(String account, String password, String verifyCode) {
        Apis<UserVo> userVoApis = userClient.loadUser(account, password);
        UserVo userVo = Apis.getApiDataNoException(userVoApis);
        if (userVo == null || !userVo.getCertified()) {
            return CodeHelper.logAetherErrorReturn(log, "用户账户信息[{}]与密码[{}]匹配错误", account, password);
        }
        SecuritySubject subject = new SecuritySubject(userVo);
        SecurityContext.login((ISecuritySubject<?>) subject);
        // todo 角色 权限 填充
        return subject;
    }
}
