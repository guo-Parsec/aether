package top.finder.aether.security.api.facade;

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
import top.finder.aether.security.api.entity.SecuritySignature;
import top.finder.aether.security.api.utils.SecurityUtils;

import javax.servlet.http.HttpServletRequest;

import static java.util.concurrent.TimeUnit.HOURS;

/**
 * <p>安全认证Facade</p>
 *
 * @author guocq
 * @since 2023/1/11
 */
public class SecurityFacade {
    private static final Logger log = LoggerFactory.getLogger(SecurityFacade.class);

    /**
     * <p>登录操作</p>
     *
     * @param signature 安全认证签名
     * @author guocq
     * @date 2022/12/27 17:00
     */
    public static <U> void login(final SecuritySignature signature) {
        log.debug("开始登录, 当前签名为[{}]", signature);
        String tokenId = IdUtil.fastSimpleUUID();
        signature.setTokenId(tokenId);
        Long id = signature.getId();
        // 登录时先踢出当前用户
        kickOut(id);
        RedisHelper redisHelper = RedisHelper.getInstance();
        String securityTokenKey = SecurityUtils.generateSecurityTokenKey(tokenId);
        String securityUserKey = SecurityUtils.generateSecurityUserKey(id);
        Long defaultTokenExpireTime = SecurityUtils.getDefaultTokenExpireTime();
        redisHelper.set(securityTokenKey, signature, defaultTokenExpireTime, HOURS);
        redisHelper.set(securityUserKey, tokenId, defaultTokenExpireTime, HOURS);
    }

    /**
     * <p>获取当前请求的安全认证签名</p>
     *
     * @return {@link SecuritySignature}
     * @author guocq
     * @date 2022/12/27 17:27
     */
    public static SecuritySignature findSecuritySignature() {
        String tokenText = CodeHelper.getHttpServletRequest().getHeader(SecurityConstantPool.TOKEN_IN_HEAD_KEY);
        return findSecuritySignature(tokenText);
    }

    /**
     * <p>根据提供的request获取请求的安全认证签名</p>
     *
     * @param request 请求
     * @return {@link SecuritySignature}
     * @author guocq
     * @date 2023/1/13 14:05
     */
    public static SecuritySignature findSecuritySignature(HttpServletRequest request) {
        String tokenText = request.getHeader(SecurityConstantPool.TOKEN_IN_HEAD_KEY);
        return findSecuritySignature(tokenText);
    }

    /**
     * <p>根据从请求头中获取的原生的令牌文本获取安全认证签名</p>
     *
     * @param tokenText 从请求头中获取的原生的令牌文本
     * @return {@link SecuritySignature}
     * @author guocq
     * @date 2022/12/27 17:19
     */
    public static SecuritySignature findSecuritySignature(String tokenText) {
        log.debug("根据令牌文本={}获取安全认证签名", tokenText);
        if (StrUtil.isBlank(tokenText)) {
            log.error("tokenText不能为空");
            throw new AetherException(CommonHttpStatus.UNAUTHORIZED, "tokenText不能为空");
        }
        String effectiveTokenId = SecurityUtils.findEffectiveTokenId(tokenText);
        RedisHelper redisHelper = RedisHelper.getInstance();
        String securityUserKey = SecurityUtils.generateSecurityTokenKey(effectiveTokenId);
        SecuritySignature signature = redisHelper.get(securityUserKey, SecuritySignature.class);
        SecurityUtils.checkSecuritySignatureEmpty(signature);
        return signature;
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
            findSecuritySignature(tokenText);
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
    public static <U> void logout() {
        SecuritySignature signature;
        try {
            signature = findSecuritySignature();
            kickOut(signature.getId());
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
        String securityUserKey = SecurityUtils.generateSecurityUserKey(userId);
        boolean exist = redisHelper.hasKey(securityUserKey);
        if (exist) {
            String tokenId = redisHelper.get(securityUserKey, String.class);
            String securityTokenKey = SecurityUtils.generateSecurityTokenKey(tokenId);
            redisHelper.delete(securityTokenKey);
        }
        redisHelper.delete(securityUserKey);
    }
}
