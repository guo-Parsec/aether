package top.finder.aether.common.support.helper;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import org.slf4j.Logger;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.finder.aether.common.support.api.IHttpStatus;
import top.finder.aether.common.support.exception.AetherException;
import top.finder.aether.common.support.exception.AetherValidException;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>代码辅助类</p>
 *
 * @author guocq
 * @since 2022/12/15
 */
public class CodeHelper {
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
}
