package top.finder.aether.security.api.facade;

import cn.hutool.core.collection.CollUtil;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.RequestPath;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.Assert;
import org.springframework.web.server.ServerWebExchange;
import top.finder.aether.common.support.helper.UrlHelper;
import top.finder.aether.common.support.pool.SecurityConstantPool;
import top.finder.aether.data.cache.support.helper.RedisHelper;
import top.finder.aether.security.api.utils.SecurityUtil;
import top.finder.aether.security.api.entity.SecuritySignature;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * <p>安全认证Facade(为WebFlux专用)</p>
 *
 * @author guocq
 * @since 2023/1/11
 */
public class SecurityWebfluxFacade {
    private static final Logger log = LoggerFactory.getLogger(SecurityWebfluxFacade.class);

    /**
     * <p>是否允许放行</p>
     *
     * @param exchange  请求上下文
     * @param whiteList 白名单路径
     * @return java.lang.Boolean
     * @author guocq
     * @date 2022/12/28 11:04
     */
    public static Boolean isReleaseAllowed(ServerWebExchange exchange, List<String> whiteList) {
        ServerHttpRequest request = exchange.getRequest();
        RequestPath requestPath = request.getPath();
        log.debug("请求[requestPath={}]开始认证", requestPath);
        if (anonRelease(requestPath, whiteList)) {
            log.debug("当前请求[{}]是匿名可访问路径，允许放行", requestPath);
            return true;
        }
        String tokenText = request.getHeaders().getFirst(SecurityConstantPool.TOKEN_IN_HEAD_KEY);
        log.debug("从请求头中解析出的原始令牌文本是[{}]", tokenText);
        return pendingCertifiedRelease(requestPath, tokenText);
    }

    /**
     * <p>判断当前请求是否认证通过放行</p>
     *
     * @param requestPath 请求路径
     * @param tokenText   从请求头中获取的原生的令牌文本
     * @return boolean
     * @author guocq
     * @date 2022/12/28 11:05
     */
    private static <U extends Serializable> boolean pendingCertifiedRelease(RequestPath requestPath, String tokenText) {
        if (!SecurityFacade.isLogin(tokenText)) {
            log.error("当前用户未登录系统，不能访问请求[{}]，系统拒绝放行", requestPath);
            return false;
        }
        SecuritySignature signature = SecurityFacade.findSecuritySignature(tokenText);
        Set<String> urls = signature.getUrls();
        String reqUrl = UrlHelper.autoPopulateRequestRootPath(requestPath.toString());
        if (!UrlHelper.matches(reqUrl, urls)) {
            log.error("当前用户没有访问请求[{}]的权限，系统拒绝放行", requestPath);
            return false;
        }
        return true;
    }

    /**
     * <p>判断当前请求路径{@code requestPath}是否为匿名可访问路径，如果是则返回true,否则返回false</p>
     *
     * @param requestPath 请求路径
     * @param whiteList   白名单路径
     * @return boolean
     * @author guocq
     * @date 2022/12/28 11:00
     */
    private static boolean anonRelease(RequestPath requestPath, List<String> whiteList) {
        Assert.notNull(requestPath, "当前请求为null");
        String anonUrlsKey = SecurityUtil.generateAnonUrlsKey();
        @SuppressWarnings("unchecked")
        Set<String> anonUrls = RedisHelper.getInstance().get(anonUrlsKey, Set.class);
        anonUrls = Optional.ofNullable(anonUrls).map(urls -> Sets.newHashSet(CollUtil.addAll(urls, whiteList))).orElse(Sets.newHashSet(whiteList));
        String reqUrl = UrlHelper.autoPopulateRequestRootPath(requestPath.toString());
        return UrlHelper.matches(reqUrl, anonUrls);
    }
}
