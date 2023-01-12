package top.finder.aether.security.api.utils;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.finder.aether.base.api.facade.SysParamFacade;
import top.finder.aether.base.api.holders.SysParamHolders;
import top.finder.aether.common.support.api.CommonHttpStatus;
import top.finder.aether.common.support.pool.CommonConstantPool;
import top.finder.aether.common.support.pool.SecurityConstantPool;
import top.finder.aether.common.utils.LoggerUtil;
import top.finder.aether.data.cache.support.helper.RedisHelper;
import top.finder.aether.security.api.entity.SecuritySignature;

import java.util.Optional;

import static top.finder.aether.security.api.utils.SecurityInnerPool.*;

/**
 * <p>安全认证辅助类</p>
 *
 * @author guocq
 * @since 2022/12/27
 */
public class SecurityUtils {
    private static final Logger log = LoggerFactory.getLogger(SecurityUtils.class);
    /**
     * <p>根据用户id生成指定userId的用户认证通过的用户信息存储的key</p>
     * <p>e.g. AETHER:SECURITY:USER:{userId}</p>
     *
     * @param userId 用户id
     * @return java.lang.String
     * @author guocq
     * @date 2022/12/27 14:19
     */
    public static String generateSecurityUserKey(Long userId) {
        String template = RedisHelper.keyJoin(true, CommonConstantPool.BASE_REDIS_KEY, SECURITY, USER,
                CommonConstantPool.EMPTY_VAR_PLACEHOLDER);
        String securityUserKey = StrUtil.format(template, userId);
        log.debug("根据userId={}生成的安全用户key为{}", userId, securityUserKey);
        return securityUserKey;
    }

    /**
     * <p>根据tokenId生成token存储的key</p>
     * <p>e.g. AETHER:SECURITY:token:{token}</p>
     *
     * @param tokenId 生成的唯一的uuid
     * @return java.lang.String
     * @author guocq
     * @date 2022/12/27 14:05
     */
    public static String generateSecurityTokenKey(String tokenId) {
        String template = RedisHelper.keyJoin(true, CommonConstantPool.BASE_REDIS_KEY, SECURITY, TOKEN,
                CommonConstantPool.EMPTY_VAR_PLACEHOLDER);
        String securityTokenKey = StrUtil.format(template, tokenId);
        log.debug("根据tokenId={}生成的安全用户key为{}", tokenId, securityTokenKey);
        return securityTokenKey;
    }

    /**
     * <p>生成可匿名访问通过的url存储的key</p>
     * <p>e.g. AETHER:SECURITY:ANON:URLS</p>
     *
     * @return java.lang.String
     * @author guocq
     * @date 2022/12/28 10:43
     */
    public static String generateAnonUrlsKey() {
        String anonUrlsKey = RedisHelper.keyJoin(true, CommonConstantPool.BASE_REDIS_KEY, SECURITY, ANON, URLS);
        log.debug("生成可匿名访问通过的url存储的key为{}", anonUrlsKey);
        return anonUrlsKey;
    }

    /**
     * <p>从字符串中获取有效的令牌id</p>
     *
     * @param text 字符串
     * @return 有效的令牌id
     */
    public static String findEffectiveTokenId(String text) {
        if (StrUtil.isBlank(text) || !text.startsWith(SecurityConstantPool.EFFECTIVE_TOKEN_PREFIX)) {
            return StrUtil.EMPTY;
        }
        String effectiveTokenId = text.replace(SecurityConstantPool.EFFECTIVE_TOKEN_PREFIX, StrUtil.EMPTY);
        if (StrUtil.isBlank(effectiveTokenId)) {
            throw LoggerUtil.logAetherError(log, "从文本{}中解析有效的token失败", text);
        }
        return effectiveTokenId;
    }

    /**
     * <p>校验安全认证签名是否为空</p>
     *
     * @param signature 安全认证签名
     * @author guocq
     * @date 2022/12/28 10:16
     */
    public static void checkSecuritySignatureEmpty(SecuritySignature signature) {
        if (isSecuritySignatureEmpty(signature)) {
            throw LoggerUtil.logAetherError(log, CommonHttpStatus.UNAUTHORIZED, "获取登录凭证信息失败，无法获取到登录信息，可能因为令牌已过期");
        }
    }

    /**
     * <p>判断安全认证签名是否为空</p>
     *
     * @param signature 安全认证签名
     * @return boolean true:表示为空 false:表示非空
     * @author guocq
     * @date 2022/12/28 10:14
     */
    public static boolean isSecuritySignatureEmpty(SecuritySignature signature) {
        return ObjectUtil.isNull(signature) || ObjectUtil.isNull(signature.getDetails());
    }

    /**
     * <p>获取默认令牌过期时间</p>
     *
     * @return java.lang.Long
     * @author guocq
     * @date 2023/1/9 16:55
     */
    public static Long getDefaultTokenExpireTime() {
        SysParamFacade sysParamFacade = null;
        try {
            sysParamFacade = SpringUtil.getBean(SysParamFacade.class);
        } catch (Exception e) {
            return DEFAULT_EXPIRE_TIME;
        }
        Optional<SysParamHolders> optional = sysParamFacade.findParamByParamCode(PARAM_DEFAULT_TOKEN_EXPIRE_TIME);
        if (!optional.isPresent()) {
            return DEFAULT_EXPIRE_TIME;
        }
        try {
            return Long.valueOf(optional.get().getParamValue());
        } catch (NumberFormatException e) {
            return DEFAULT_EXPIRE_TIME;
        }
    }
}

interface SecurityInnerPool {
   String SECURITY = "SECURITY";

    String USER = "USER";

    String TOKEN = "TOKEN";

    String ANON = "ANON";

    String URLS = "URLS";

    /**
     * 默认令牌过期时间(小时)
     */
    String PARAM_DEFAULT_TOKEN_EXPIRE_TIME = "DEFAULT_TOKEN_EXPIRE_TIME";

    /**
     * 默认过期时间 - 小时
     */
    Long DEFAULT_EXPIRE_TIME = 6L;
}
