package top.finder.aether.common.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import top.finder.aether.common.support.api.Apis;
import top.finder.aether.common.support.exception.AetherException;

/**
 * <p>全局异常配置</p>
 *
 * @author guocq
 * @since 2022/12/27
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionConfig {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionConfig.class);

    /**
     * <p>参数校验失败异常</p>
     *
     * @param exception 参数校验异常
     * @return {@link Apis <Void>}
     * @author guocq
     * @date 2022/12/27 10:17
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public Apis<Void> validException(MethodArgumentNotValidException exception) {
        ObjectError objectError = exception.getBindingResult().getAllErrors().get(0);
        log.error("参数校验失败", exception);
        return Apis.validateFailed(objectError.getDefaultMessage());
    }

    /**
     * <p>参数校验失败异常处理</p>
     *
     * @param exception 异常
     * @return {@link Apis <Void>}
     * @author guocq
     * @date 2022/12/6 14:04
     */
    @ExceptionHandler({BindException.class})
    public Apis<Void> bindException(BindException exception) {
        ObjectError objectError = exception.getBindingResult().getAllErrors().get(0);
        log.error("参数校验失败", exception);
        return Apis.validateFailed(objectError.getDefaultMessage());
    }

    /**
     * <p>业务异常处理</p>
     *
     * @param exception 业务异常
     * @return {@link Apis <Void>}
     * @author guocq
     * @date 2022/12/27 10:17
     */
    @ExceptionHandler({AetherException.class})
    public Apis<Void> coreExceptionHandler(AetherException exception) {
        log.error("应用执行异常处理[AetherException]", exception);
        return Apis.failed(exception);
    }

    /**
     * <p>普通异常统一处理</p>
     *
     * @param exception 异常
     * @return {@link Apis <Void>}
     * @author guocq
     * @date 2022/12/27 10:18
     */
    @ExceptionHandler({Exception.class})
    public Apis<Void> defaultExceptionHandler(Exception exception) {
        log.error("应用执行异常处理[Exception]", exception);
        return Apis.failed(exception);
    }
}
