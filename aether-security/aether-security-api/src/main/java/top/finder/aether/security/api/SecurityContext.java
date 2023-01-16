package top.finder.aether.security.api;

import cn.hutool.core.thread.ThreadUtil;
import top.finder.aether.security.api.entity.SecuritySignature;
import top.finder.aether.security.api.facade.SecurityFacade;
import top.finder.aether.security.api.utils.SecurityUtil;

/**
 * <p>安全认证上下文</p>
 *
 * @author guocq
 * @since 2023/1/16
 */
public class SecurityContext {
    @SuppressWarnings("all")
    private static final ThreadLocal<SecuritySignature> securityContext = ThreadUtil.createThreadLocal(() -> null);

    /**
     * <p>销毁安全上下文</p>
     *
     * @author guocq
     * @date 2023/1/16 9:26
     */
    public static void destroy() {
        securityContext.remove();
    }

    /**
     * <p>获取安全签名</p>
     *
     * @return {@link SecuritySignature}
     * @author guocq
     * @date 2023/1/16 9:24
     */
    public static SecuritySignature get() {
        SecuritySignature signature = securityContext.get();
        if (SecurityUtil.isSecuritySignatureEmpty(signature)) {
            signature = SecurityFacade.findSecuritySignature();
            securityContext.set(signature);
        }
        return signature;
    }

    /**
     * <p>安全上下文初始化</p>
     *
     * @author guocq
     * @date 2023/1/16 9:32
     */
    public static SecuritySignature init() {
        SecuritySignature signature = securityContext.get();
        if (SecurityUtil.isSecuritySignatureEmpty(signature)) {
            signature = SecurityFacade.findSecuritySignature();
            securityContext.set(signature);
        }
        return signature;
    }

    /**
     * <p>获取安全签名并销毁上下文</p>
     *
     * @param signature 安全签名
     * @author guocq
     * @date 2023/1/16 9:25
     */
    public static void put(SecuritySignature signature) {
        securityContext.set(signature);
    }
}
