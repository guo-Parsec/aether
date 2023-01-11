package top.finder.aether.security.core.service;

import top.finder.aether.security.api.entity.SecuritySignature;

/**
 * <p>登录服务类接口</p>
 *
 * @author guocq
 * @since 2022/12/28
 */
public interface LoginService {
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
    SecuritySignature login(String account, String password, String verifyCode);
}
