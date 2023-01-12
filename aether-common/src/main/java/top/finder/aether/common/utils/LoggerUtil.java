package top.finder.aether.common.utils;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.finder.aether.common.support.api.CommonHttpStatus;
import top.finder.aether.common.support.api.IHttpStatus;
import top.finder.aether.common.support.exception.AetherException;
import top.finder.aether.common.support.exception.AetherValidException;

/**
 * <p>日志工具类</p>
 *
 * @author guocq
 * @since 2023/1/11
 */
public final class LoggerUtil extends Loggers {
    /**
     * <p>打印错误日志信息并返回Runtime异常</p>
     *
     * @param logger 日志对象
     * @param format 格式化语句
     * @param args   参数
     * @return java.lang.RuntimeException
     * @author guocq
     * @date 2023/1/11 17:17
     */
    public static RuntimeException logRuntimeError(Logger logger, String format, Object... args) {
        if (ArrayUtil.isEmpty(args)) {
            logger.error(format);
            return new RuntimeException(format);
        }
        String message = StrUtil.format(format, args);
        logger.error(message);
        return new RuntimeException(message);
    }

    /**
     * <p>打印错误日志信息并返回Runtime异常</p>
     *
     * @param clazz  类对象
     * @param format 格式化语句
     * @param args   参数
     * @return java.lang.RuntimeException
     * @author guocq
     * @date 2023/1/11 17:17
     */
    public static RuntimeException logRuntimeError(Class<?> clazz, String format, Object... args) {
        Logger logger = LoggerFactory.getLogger(clazz);
        return logRuntimeError(logger, format, args);
    }

    /**
     * <p>打印错误日志信息并返回异常</p>
     *
     * @param logger     日志对象
     * @param throwable  错误异常
     * @param httpStatus 状态码
     * @param format     格式化语句
     * @param args       参数
     * @return {@link AetherException}
     * @author guocq
     * @date 2023/1/11 17:13
     */
    public static AetherException logAetherError(Logger logger, Throwable throwable, IHttpStatus httpStatus, String format, Object... args) {
        if (ArrayUtil.isEmpty(args)) {
            logger.error(format, throwable);
            return new AetherException(httpStatus, format);
        }
        String message = StrUtil.format(format, args);
        logger.error(message, throwable);
        return new AetherException(httpStatus, message);
    }

    /**
     * <p>打印错误日志信息并返回异常</p>
     *
     * @param logger    日志对象
     * @param throwable 错误异常
     * @param format    格式化语句
     * @param args      参数
     * @return {@link AetherException}
     * @author guocq
     * @date 2023/1/11 17:13
     */
    public static AetherException logAetherError(Logger logger, Throwable throwable, String format, Object... args) {
        return logAetherError(logger, throwable, CommonHttpStatus.FAILED, format, args);
    }

    /**
     * <p>打印错误日志信息并返回异常</p>
     *
     * @param logger     日志对象
     * @param throwable  错误异常
     * @param httpStatus 状态码
     * @return {@link AetherException}
     * @author guocq
     * @date 2023/1/11 17:13
     */
    public static AetherException logAetherError(Logger logger, Throwable throwable, IHttpStatus httpStatus) {
        return logAetherError(logger, throwable, httpStatus, throwable.getMessage());
    }

    /**
     * <p>打印错误日志信息并返回异常</p>
     *
     * @param logger    日志对象
     * @param throwable 错误异常
     * @return {@link AetherException}
     * @author guocq
     * @date 2023/1/11 17:13
     */
    public static AetherException logAetherError(Logger logger, Throwable throwable) {
        return logAetherError(logger, throwable, CommonHttpStatus.FAILED, throwable.getMessage());
    }

    /**
     * <p>打印错误日志信息并返回异常</p>
     *
     * @param logger     日志对象
     * @param httpStatus 状态码
     * @param format     格式化语句
     * @param args       参数
     * @return {@link AetherException}
     * @author guocq
     * @date 2023/1/11 17:13
     */
    public static AetherException logAetherError(Logger logger, IHttpStatus httpStatus, String format, Object... args) {
        if (ArrayUtil.isEmpty(args)) {
            logger.error(format);
            return new AetherException(httpStatus, format);
        }
        String message = StrUtil.format(format, args);
        logger.error(message);
        return new AetherException(httpStatus, message);
    }

    /**
     * <p>打印错误日志信息并返回异常</p>
     *
     * @param logger 日志对象
     * @param format 格式化语句
     * @param args   参数
     * @return {@link AetherException}
     * @author guocq
     * @date 2023/1/11 17:13
     */
    public static AetherException logAetherError(Logger logger, String format, Object... args) {
        return logAetherError(logger, CommonHttpStatus.FAILED, format, args);
    }

    /**
     * <p>打印错误日志信息并返回异常</p>
     *
     * @param clazz      类对象
     * @param httpStatus 状态码
     * @param format     格式化语句
     * @param args       参数
     * @return {@link AetherException}
     * @author guocq
     * @date 2023/1/11 17:16
     */
    public static AetherException logAetherError(Class<?> clazz, IHttpStatus httpStatus, String format, Object... args) {
        Logger logger = LoggerFactory.getLogger(clazz);
        return logAetherError(logger, httpStatus, format, args);
    }

    /**
     * <p>打印错误日志信息并返回异常</p>
     *
     * @param clazz  类对象
     * @param format 格式化语句
     * @param args   参数
     * @return {@link AetherException}
     * @author guocq
     * @date 2023/1/11 17:13
     */
    public static AetherException logAetherError(Class<?> clazz, String format, Object... args) {
        return logAetherError(clazz, CommonHttpStatus.FAILED, format, args);
    }

    /**
     * <p>打印错误日志信息并返回异常</p>
     *
     * @param logger     日志对象
     * @param httpStatus 状态
     * @param message    错误语句
     * @return {@link AetherException}
     * @author guocq
     * @date 2023/1/12 9:06
     */
    public static AetherException logAetherError(Logger logger, IHttpStatus httpStatus, String message) {
        logger.error(message);
        throw new AetherException(httpStatus, message);
    }

    /**
     * <p>打印错误日志信息并返回异常</p>
     *
     * @param logger     日志对象
     * @param message    错误语句
     * @return {@link AetherException}
     * @author guocq
     * @date 2023/1/12 9:06
     */
    public static AetherException logAetherError(Logger logger, String message) {
        logger.error(message);
        throw new AetherException(message);
    }

    /**
     * <p>打印错误日志信息并返回验证异常</p>
     *
     * @param logger 日志对象
     * @param format 格式化语句
     * @param args   参数
     * @return {@link AetherValidException}
     * @author guocq
     * @date 2023/1/11 17:22
     */
    public static AetherValidException logAetherValidError(Logger logger, String format, Object... args) {
        if (ArrayUtil.isEmpty(args)) {
            logger.error(format);
            return new AetherValidException(format);
        }
        String message = StrUtil.format(format, args);
        logger.error(message);
        return new AetherValidException(message);
    }

    /**
     * <p>打印错误日志信息并返回验证异常</p>
     *
     * @param clazz  类对象
     * @param format 格式化语句
     * @param args   参数
     * @return {@link AetherValidException}
     * @author guocq
     * @date 2023/1/11 17:22
     */
    public static AetherValidException logAetherValidError(Class<?> clazz, String format, Object... args) {
        Logger logger = LoggerFactory.getLogger(clazz);
        return logAetherValidError(logger, format, args);
    }
}
