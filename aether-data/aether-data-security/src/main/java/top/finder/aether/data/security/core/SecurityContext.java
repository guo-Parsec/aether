package top.finder.aether.data.security.core;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import top.finder.aether.common.support.api.CommonHttpStatus;
import top.finder.aether.common.support.exception.AetherException;
import top.finder.aether.common.support.helper.CodeHelper;
import top.finder.aether.common.support.pool.SecurityConstantPool;
import top.finder.aether.data.cache.support.helper.RedisHelper;
import top.finder.aether.data.security.support.helper.SecurityHelper;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * <p>安全认证上下文</p>
 *
 * @author guocq
 * @since 2022/12/27
 */
public class SecurityContext {
    private static final Logger log = LoggerFactory.getLogger(SecurityContext.class);

    private static final Long DEFAULT_EXPIRE_TIME = 6L;

    /**
     * <p>登录操作</p>
     *
     * @param subject 安全认证主体
     * @author guocq
     * @date 2022/12/27 17:00
     */
    public static <U extends Serializable> void login(final ISecuritySubject<U> subject) {
        log.debug("登录操作开始, 当前登录人={}", subject);
        String tokenId = IdUtil.fastSimpleUUID();
        subject.setTokenId(tokenId);
        Long id = subject.getId();
        // 登录时先踢出当前用户
        kickOut(id);
        RedisHelper redisHelper = RedisHelper.getInstance();
        String securityTokenKey = SecurityHelper.generateSecurityTokenKey(tokenId);
        String securityUserKey = SecurityHelper.generateSecurityUserKey(id);
        redisHelper.set(securityTokenKey, subject, DEFAULT_EXPIRE_TIME, TimeUnit.HOURS);
        redisHelper.set(securityUserKey, tokenId, DEFAULT_EXPIRE_TIME, TimeUnit.HOURS);
    }

    /**
     * <p>获取当前请求的安全认证主体</p>
     *
     * @return {@link ISecuritySubject}
     * @author guocq
     * @date 2022/12/27 17:27
     */
    public static <U extends Serializable> ISecuritySubject<U> findSecuritySubject() {
        String tokenText = CodeHelper.getHttpServletRequest().getHeader(SecurityConstantPool.TOKEN_IN_HEAD_KEY);
        return findSecuritySubject(tokenText);
    }

    /**
     * <p>根据从请求头中获取的原生的令牌文本获取安全认证主体</p>
     *
     * @param tokenText 从请求头中获取的原生的令牌文本
     * @return {@link ISecuritySubject}
     * @author guocq
     * @date 2022/12/27 17:19
     */
    public static <U extends Serializable> ISecuritySubject<U> findSecuritySubject(String tokenText) {
        log.debug("根据令牌文本={}获取安全认证主体", tokenText);
        if (StrUtil.isBlank(tokenText)) {
            CodeHelper.logAetherError(log, "tokenText不能为空", CommonHttpStatus.UNAUTHORIZED);
        }
        String effectiveTokenId = SecurityHelper.findEffectiveTokenId(tokenText);
        RedisHelper redisHelper = RedisHelper.getInstance();
        String securityUserKey = SecurityHelper.generateSecurityTokenKey(effectiveTokenId);
        @SuppressWarnings("unchecked")
        ISecuritySubject<U> securitySubject = redisHelper.get(securityUserKey, ISecuritySubject.class);
        SecurityHelper.checkSecuritySubjectEmpty(securitySubject);
        return securitySubject;
    }

    /**
     * <p>根据当前请求判断用户是否认证过</p>
     *
     * @return boolean
     * @author guocq
     * @date 2022/12/27 17:30
     */
    public static boolean isLogin() {
        String tokenText = CodeHelper.getHttpServletRequest().getHeader(SecurityConstantPool.TOKEN_IN_HEAD_KEY);
        return isLogin(tokenText);
    }

    /**
     * <p>根据提供的{@code tokenText}判断用户是否认证过</p>
     *
     * @param tokenText 从请求头中获取的原生的令牌文本
     * @return boolean
     * @author guocq
     * @date 2022/12/27 17:30
     */
    public static boolean isLogin(String tokenText) {
        log.debug("根据令牌文本={}判断当前请求是否登录过", tokenText);
        try {
            findSecuritySubject(tokenText);
        } catch (AetherException aetherException) {
            log.error("根据令牌文本={}判断结果为：当前请求尚未登录", tokenText);
            return false;
        }
        return true;
    }

    /**
     * <p>注销当前用户</p>
     *
     * @author guocq
     * @date 2022/12/27 17:34
     */
    public static <U extends Serializable> void logout() {
        ISecuritySubject<U> subject;
        try {
            subject = findSecuritySubject();
            kickOut(subject.getId());
        } catch (AetherException aetherException) {
            log.error("当前用户已退出系统");
        }
    }

    /**
     * <p>踢人下线</p>
     *
     * @param userId 用户id
     * @author guocq
     * @date 2022/12/27 16:49
     */
    public static void kickOut(Long userId) {
        log.debug("id为{}的用户将被踢下线", userId);
        Assert.notNull(userId, "userId不能为空");
        RedisHelper redisHelper = RedisHelper.getInstance();
        String securityUserKey = SecurityHelper.generateSecurityUserKey(userId);
        boolean exist = redisHelper.hasKey(securityUserKey);
        redisHelper.delete(securityUserKey);
        if (exist) {
            String tokenId = redisHelper.get(securityUserKey, String.class);
            String securityTokenKey = SecurityHelper.generateSecurityTokenKey(tokenId);
            redisHelper.delete(securityTokenKey);
        }
    }
}
