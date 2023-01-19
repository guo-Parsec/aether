package top.finder.aether.security.core.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import top.finder.aether.security.api.entity.SecuritySignature;
import top.finder.aether.security.api.facade.SecurityInjectFacade;
import top.finder.aether.system.api.facade.SysUserFacade;

import java.util.Set;

import static top.finder.aether.system.api.support.pool.SystemConstantPool.*;

/**
 * <p>安全注入Facade实现类</p>
 *
 * @author guocq
 * @since 2023/1/19
 */
@Primary
@Component(value = "securityInjectFacade")
public class SecurityInjectFacadeImpl implements SecurityInjectFacade {
    private static final Logger log = LoggerFactory.getLogger(SecurityInjectFacadeImpl.class);

    private final SysUserFacade sysUserFacade;

    public SecurityInjectFacadeImpl(SysUserFacade sysUserFacade) {
        this.sysUserFacade = sysUserFacade;
    }

    /**
     * <p>注入角色集合</p>
     *
     * @param signature 安全签名 {@link SecuritySignature}
     * @return java.util.Set<java.lang.String>
     * @author guocq
     * @date 2023/1/19 13:54
     */
    @Override
    public Set<String> injectRoleSet(SecuritySignature signature) {
        if (!SECURITY_DETAILS_MAP.containsKey(SECURITY_DETAILS_KEY_ROLES)) {
            SECURITY_DETAILS_MAP.putAll(sysUserFacade.findSecurityDetailsByUserId(signature.getId()));
        }
        return SECURITY_DETAILS_MAP.get(SECURITY_DETAILS_KEY_ROLES);
    }

    /**
     * <p>注入权限集合</p>
     *
     * @param signature 安全签名 {@link SecuritySignature}
     * @return java.util.Set<java.lang.String>
     * @author guocq
     * @date 2023/1/19 13:55
     */
    @Override
    public Set<String> injectPermissionSet(SecuritySignature signature) {
        if (!SECURITY_DETAILS_MAP.containsKey(SECURITY_DETAILS_KEY_PERMISSIONS)) {
            SECURITY_DETAILS_MAP.putAll(sysUserFacade.findSecurityDetailsByUserId(signature.getId()));
        }
        return SECURITY_DETAILS_MAP.get(SECURITY_DETAILS_KEY_PERMISSIONS);
    }

    /**
     * <p>注入资源url集合</p>
     *
     * @param signature 安全签名 {@link SecuritySignature}
     * @return java.util.Set<java.lang.String>
     * @author guocq
     * @date 2023/1/19 13:55
     */
    @Override
    public Set<String> injectResourceUrlSet(SecuritySignature signature) {
        if (!SECURITY_DETAILS_MAP.containsKey(SECURITY_DETAILS_KEY_RESOURCE_URLS)) {
            SECURITY_DETAILS_MAP.putAll(sysUserFacade.findSecurityDetailsByUserId(signature.getId()));
        }
        return SECURITY_DETAILS_MAP.get(SECURITY_DETAILS_KEY_RESOURCE_URLS);
    }
}
