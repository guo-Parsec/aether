package top.finder.aether.common.support.helper;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.finder.aether.common.support.api.IHttpStatus;
import top.finder.aether.common.support.exception.AetherException;
import top.finder.aether.common.support.exception.AetherValidException;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Map;

/**
 * <p>代码辅助类</p>
 *
 * @author guocq
 * @since 2022/12/15
 */
public class CodeHelper {
    private static ThreadLocal<Map<String, String>> headerThreadLocal = ThreadUtil.createThreadLocal(Maps::newHashMap);

    /**
     * <p>将源对象转化为{@code V}类型对象</p>
     *
     * @param source 源对象
     * @param vClass 目标类对象
     * @return V
     * @author guocq
     * @date 2022/12/15 16:30
     */
    public static <V> V cast(Object source, Class<V> vClass) {
        if (ObjectUtil.isNull(source) || ObjectUtil.isNull(vClass)) {
            throw new IllegalArgumentException("目标对象[source]和转换类对象[vClass]不能为空");
        }
        return vClass.cast(source);
    }

    /**
     * <p>记录runtime异常日志并抛出</p>
     *
     * @param log      记录日志对象
     * @param template 日志模板
     * @param params   参数
     * @author guocq
     * @date 2022/12/27 10:28
     */
    public static void logRuntimeError(Logger log, CharSequence template, Object... params) {
        String errorMessage = StrUtil.format(template, params);
        log.error(errorMessage);
        throw new RuntimeException(errorMessage);
    }

    /**
     * <p>记录Aether异常日志并抛出</p>
     *
     * @param log      记录日志对象
     * @param template 日志模板
     * @param params   参数
     * @author guocq
     * @date 2022/12/27 10:28
     */
    public static void logAetherError(Logger log, CharSequence template, Object... params) {
        String errorMessage = StrUtil.format(template, params);
        log.error(errorMessage);
        throw new AetherException(errorMessage);
    }

    /**
     * <p>记录Aether异常日志并抛出</p>
     *
     * @param log      记录日志对象
     * @param template 日志模板
     * @param params   参数
     * @author guocq
     * @date 2022/12/27 10:28
     */
    public static void logAetherError(Logger log, CharSequence template, IHttpStatus httpStatus, Object... params) {
        String errorMessage = StrUtil.format(template, params);
        log.error(errorMessage);
        throw new AetherException(httpStatus, errorMessage);
    }

    public static <T> T logAetherErrorReturn(Logger log, CharSequence template, Object... params) {
        String errorMessage = StrUtil.format(template, params);
        log.error(errorMessage);
        throw new AetherException(errorMessage);
    }

    /**
     * <p>记录AetherValid异常日志并抛出</p>
     *
     * @param log      记录日志对象
     * @param template 日志模板
     * @param params   参数
     * @author guocq
     * @date 2022/12/27 10:28
     */
    public static void logAetherValidError(Logger log, CharSequence template, Object... params) {
        String errorMessage = StrUtil.format(template, params);
        log.error(errorMessage);
        throw new AetherValidException(errorMessage);
    }

    /**
     * 获取当前请求
     *
     * @return 请求
     */
    public static HttpServletRequest getHttpServletRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert requestAttributes != null;
        return requestAttributes.getRequest();
    }

    /**
     * <p>获取请求头信息</p>
     *
     * @param
     * @return java.util.Map<java.lang.String, java.lang.String>
     * @author guocq
     * @date 2022/12/30 16:26
     */
    public static Map<String, String> getHeaders() {
        Map<String, String> headersMap = Maps.newHashMap();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            Enumeration<String> headerNames = request.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String name = headerNames.nextElement();
                pushEffectiveHeader(headersMap, name, request.getHeader(name));
            }
        }
        return headersMap;
    }

    /**
     * <p>设置共享请求头</p>
     *
     * @param headers 请求头
     * @author guocq
     * @date 2022/12/30 16:32
     */
    public static void setHeadersToShare(final Map<String, String> headers) {
        if (MapUtil.isEmpty(headers)) {
            return;
        }
        headerThreadLocal.set(headers);
    }

    /**
     * <p>设置共享请求头</p>
     *
     * @return headers 请求头
     * @author guocq
     * @date 2022/12/30 16:32
     */
    public static Map<String, String> getHeadersToShare() {
        return headerThreadLocal.get();
    }

    /**
     * <p>清楚本地缓存</p>
     *
     * @author guocq
     * @date 2022/12/30 16:33
     */
    public static void clearHeadersToShare() {
        headerThreadLocal.remove();
    }

    /**
     * <p>设置有效请求头map</p>
     *
     * @param headersMap 请求头map
     * @param name       header名称
     * @param value      值
     * @author guocq
     * @date 2022/12/30 16:24
     */
    private static void pushEffectiveHeader(Map<String, String> headersMap, String name, String value) {
        if (StrUtil.isNotBlank(name) && StrUtil.isNotBlank(value)) {
            headersMap.put(name, value);
        }
    }
}
