package top.finder.aether.security.api.facade;

import com.google.common.collect.Maps;
import top.finder.aether.security.api.entity.SecuritySignature;

import java.util.Map;
import java.util.Set;

/**
 * <p>安全注入Facade接口</p>
 *
 * @author guocq
 * @since 2023/1/19
 */
public interface SecurityInjectFacade {
    Map<String, Set<String>> SECURITY_DETAILS_MAP = Maps.newHashMap();

    /**
     * <p>注入角色集合</p>
     *
     * @param signature 安全签名 {@link SecuritySignature}
     * @return java.util.Set<java.lang.String>
     * @author guocq
     * @date 2023/1/19 13:54
     */
    Set<String> injectRoleSet(SecuritySignature signature);

    /**
     * <p>注入权限集合</p>
     *
     * @param signature 安全签名 {@link SecuritySignature}
     * @return java.util.Set<java.lang.String>
     * @author guocq
     * @date 2023/1/19 13:55
     */
    Set<String> injectPermissionSet(SecuritySignature signature);

    /**
     * <p>注入资源url集合</p>
     *
     * @param signature 安全签名 {@link SecuritySignature}
     * @return java.util.Set<java.lang.String>
     * @author guocq
     * @date 2023/1/19 13:55
     */
    Set<String> injectResourceUrlSet(SecuritySignature signature);

    /**
     * <p>清空缓存</p>
     *
     * @author guocq
     * @date 2023/1/19 14:56
     */
    default void clearMap() {
        SECURITY_DETAILS_MAP.clear();
    }
}
