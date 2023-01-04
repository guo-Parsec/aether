package top.finder.aether.data.security.support.helper;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import top.finder.aether.common.support.api.CommonHttpStatus;
import top.finder.aether.common.support.helper.CodeHelper;
import top.finder.aether.common.support.pool.CommonConstantPool;
import top.finder.aether.common.support.pool.SecurityConstantPool;
import top.finder.aether.data.cache.support.helper.RedisHelper;
import top.finder.aether.data.security.core.ISecuritySubject;

/**
 * <p>安全认证辅助类</p>
 *
 * @author guocq
 * @since 2022/12/27
 */
@Slf4j
public class SecurityHelper {
    private static final String SECURITY = "SECURITY";

    private static final String USER = "USER";

    private static final String TOKEN = "TOKEN";

    private static final String ANON = "ANON";

    private static final String URLS = "URLS";

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
            CodeHelper.logAetherError(log, "从文本{}中解析有效的token失败", CommonHttpStatus.UNAUTHORIZED, text);
        }
        return effectiveTokenId;
    }

    /**
     * <p>判断安全认证主体是否为空</p>
     *
     * @param securitySubject 安全认证主体
     * @return boolean true:表示为空 false:表示非空
     * @author guocq
     * @date 2022/12/28 10:14
     */
    public static boolean isSecuritySubjectEmpty(ISecuritySubject<?> securitySubject) {
        return ObjectUtil.isNull(securitySubject) || ObjectUtil.isNull(securitySubject.getSignature());
    }

    /**
     * <p>校验安全认证主体是否为空</p>
     *
     * @param securitySubject 安全认证主体
     * @author guocq
     * @date 2022/12/28 10:16
     */
    public static void checkSecuritySubjectEmpty(ISecuritySubject<?> securitySubject) {
        if (isSecuritySubjectEmpty(securitySubject)) {
            CodeHelper.logAetherError(log, "获取登录凭证信息失败，无法获取到登录信息，可能因为令牌已过期", CommonHttpStatus.UNAUTHORIZED);
        }
    }
}
