package top.finder.aether.common.utils;

import ch.qos.logback.classic.Level;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Maps;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.springframework.util.Assert;
import top.finder.aether.common.support.function.TernaryConsumer;

import java.util.Map;

/**
 * <p>日志输出辅助类</p>
 *
 * @author guocq
 * @since 2023/1/5
 */
public class Loggers {
    private static final Map<Level, TernaryConsumer<Logger, String, Object[]>> LOG_MAP = Maps.newHashMapWithExpectedSize(5);

    static {
        LOG_MAP.put(Level.TRACE, Loggers::trace);
        LOG_MAP.put(Level.DEBUG, Loggers::debug);
        LOG_MAP.put(Level.INFO, Loggers::info);
        LOG_MAP.put(Level.WARN, Loggers::warn);
        LOG_MAP.put(Level.ERROR, Loggers::error);
    }

    /**
     * <p>日志打印</p>
     *
     * @param log      日志对象
     * @param template 文本模板
     * @param level    日志级别
     * @param param    参数
     * @author guocq
     * @date 2023/1/5 10:35
     */
    public static void log(Logger log, String template, Level level, Object... param) {
        Assert.notNull(log, "日志对象不能为null");
        Assert.notNull(level, "日志级别不能为null");
        TernaryConsumer<Logger, String, Object[]> consumer = LOG_MAP.get(level);
        if (consumer != null) {
            consumer.accept(log, template, param);
        }
    }

    /**
     * <p>debug输出日志</p>
     *
     * @param log    日志对象
     * @param text   输出文本
     * @param params 参数
     * @author guocq
     * @date 2023/1/5 10:30
     */
    public static void debug(Logger log, String text, Object[] params) {
        if (ArrayUtil.isEmpty(params)) {
            log.debug(text);
            return;
        }
        if (log.isDebugEnabled()) {
            log.debug(text, params);
        }
    }

    /**
     * <p>info输出日志</p>
     *
     * @param log    日志对象
     * @param text   输出文本
     * @param params 参数
     * @author guocq
     * @date 2023/1/5 10:30
     */
    public static void info(Logger log, String text, Object[] params) {
        if (ArrayUtil.isEmpty(params)) {
            log.info(text);
            return;
        }
        log.info(text, params);
    }

    /**
     * <p>warn输出日志</p>
     *
     * @param log    日志对象
     * @param text   输出文本
     * @param params 参数
     * @author guocq
     * @date 2023/1/5 10:30
     */
    public static void warn(Logger log, String text, Object[] params) {
        if (ArrayUtil.isEmpty(params)) {
            log.warn(text);
            return;
        }
        log.warn(text, params);
    }

    /**
     * <p>error输出日志</p>
     *
     * @param log    日志对象
     * @param text   输出文本
     * @param params 参数
     * @author guocq
     * @date 2023/1/5 10:30
     */
    public static void error(Logger log, String text, Object[] params) {
        if (ArrayUtil.isEmpty(params)) {
            log.error(text);
            return;
        }
        log.error(text, params);
    }

    /**
     * <p>trace输出日志</p>
     *
     * @param log    日志对象
     * @param text   输出文本
     * @param params 参数
     * @author guocq
     * @date 2023/1/5 10:30
     */
    public static void trace(Logger log, String text, Object[] params) {
        if (ArrayUtil.isEmpty(params)) {
            log.trace(text);
            return;
        }
        if (log.isTraceEnabled()) {
            log.trace(text, params);
        }
    }

    /**
     * <p>aop输出日志信息</p>
     *
     * @param log             日志对象
     * @param level           日志级别
     * @param point           切入点
     * @param messageTemplate 消息模板
     * @author guocq
     * @date 2023/1/5 10:15
     */
    public static void aopLog(Logger log, Level level, ProceedingJoinPoint point, String messageTemplate) {
        Assert.notNull(point, "切面切入点不能为null");
        Assert.notNull(level, "日志级别不能为null");
        Assert.notNull(log, "日志对象不能为null");
        if (StrUtil.isBlank(messageTemplate)) {
            log.error("消息模板不能为空");
            return;
        }
        String className = point.getTarget().getClass().getName();
        String methodName = point.getSignature().getName();
        log(log, messageTemplate, level, StrUtil.format("[{}#{}]", className, methodName));
    }

    /**
     * <p>aop输出日志信息[默认为debug]</p>
     *
     * @param log             日志对象
     * @param point           切入点
     * @param messageTemplate 消息模板
     * @author guocq
     * @date 2023/1/5 10:15
     */
    public static void aopLog(Logger log, ProceedingJoinPoint point, String messageTemplate) {
        aopLog(log, Level.DEBUG, point, messageTemplate);
    }

    /**
     * <p>aop输出日志信息[默认为error]</p>
     *
     * @param log             日志对象
     * @param point           切入点
     * @param messageTemplate 消息模板
     * @author guocq
     * @date 2023/1/5 10:15
     */
    public static void aopErrorLog(Logger log, ProceedingJoinPoint point, String messageTemplate) {
        aopLog(log, Level.ERROR, point, messageTemplate);
    }
}
