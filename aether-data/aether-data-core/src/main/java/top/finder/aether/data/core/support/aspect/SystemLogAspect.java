package top.finder.aether.data.core.support.aspect;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import lombok.Getter;
import lombok.Setter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import top.finder.aether.common.facade.SystemLogFacade;
import top.finder.aether.common.model.SystemLogInfo;
import top.finder.aether.common.support.annotation.LoginLog;
import top.finder.aether.common.support.annotation.OperateLog;
import top.finder.aether.common.support.api.Apis;
import top.finder.aether.common.support.exception.AetherException;
import top.finder.aether.common.support.helper.CodeHelper;
import top.finder.aether.common.support.pool.CommonConstantPool;
import top.finder.aether.common.utils.SystemLogUtil;
import top.finder.aether.data.core.support.helper.AppHelper;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

/**
 * <p>日志切面</p>
 *
 * @author guocq
 * @since 2022/12/30
 */
@Aspect
@Component
public class SystemLogAspect {
    private static final Logger log = LoggerFactory.getLogger(SystemLogAspect.class);

    /**
     * <p>操作日志记录环绕方法</p>
     *
     * @param point      切入点
     * @param operateLog 操作日志注解
     * @return java.lang.Object
     * @author guocq
     * @date 2023/1/4 10:32
     */
    @Around("@annotation(operateLog)")
    public Object around(ProceedingJoinPoint point, OperateLog operateLog) throws Throwable {
        if (AppHelper.isFeignRequest()) {
            return point.proceed(point.getArgs());
        }
        SystemLogInfo systemLogInfo = beforeProcess(point);
        ProcessResult processed = processed(point, systemLogInfo);
        Object result = processed.getResult();
        SystemLogUtil.buildLogModel(systemLogInfo);
        operateLogAfterProcess(systemLogInfo);
        return result;
    }

    /**
     * <p>登录日志记录环绕方法</p>
     *
     * @param point    切入点
     * @param loginLog 登录日志注解
     * @return java.lang.Object
     * @author guocq
     * @date 2023/1/4 10:33
     */
    @Around("@annotation(loginLog)")
    public Object around(ProceedingJoinPoint point, LoginLog loginLog) {
        SystemLogInfo systemLogInfo = beforeProcess(point);
        ProcessResult processed = processed(point, systemLogInfo);
        Object result = processed.getResult();
        SystemLogUtil.buildLogModel(systemLogInfo);
        Object[] args = point.getArgs();
        if (ArrayUtil.isNotEmpty(args)) {
            Object account = args[loginLog.index()];
            systemLogInfo.setUserAccount(StrUtil.toStringOrNull(account));
            loginLogAfterProcess(systemLogInfo);
        }
        return result;
    }

    /**
     * <p>前置处理</p>
     *
     * @param point 切入点
     * @return {@link SystemLogInfo}
     * @author guocq
     * @date 2023/1/4 10:09
     */
    private SystemLogInfo beforeProcess(ProceedingJoinPoint point) {
        SystemLogInfo systemLogInfo = new SystemLogInfo();
        systemLogInfo.setLogTime(LocalDateTime.now());
        String className = point.getTarget().getClass().getName();
        String methodName = point.getSignature().getName();
        log.debug("{}#{}正在记录日志", className, methodName);
        return systemLogInfo;
    }

    /**
     * <p>核心处理</p>
     *
     * @param point    切入点
     * @param systemLogInfo 日志模型
     * @return {@link ProcessResult}
     * @author guocq
     * @date 2023/1/4 9:53
     */
    private ProcessResult processed(ProceedingJoinPoint point, SystemLogInfo systemLogInfo) {
        Object result;
        AetherException error = null;
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("记录请求时间");
        try {
            result = point.proceed();
            systemLogInfo.setResult(CommonConstantPool.SUCCESS);
        } catch (AetherException exception) {
            error = exception;
            result = Apis.failed(exception);
            systemLogInfo.error(error);
        } catch (Throwable exception) {
            error = new AetherException(exception, exception.getMessage());
            result = Apis.failed(error);
            systemLogInfo.error(error);
        }
        stopWatch.stop();
        long totalTimeMillis = stopWatch.getTotalTimeMillis();
        systemLogInfo.setTimeConsuming(totalTimeMillis);
        return new ProcessResult(error, result);
    }

    /**
     * <p>操作日志后置处理</p>
     *
     * @param systemLogInfo 日志模型
     * @author guocq
     * @date 2023/1/4 10:08
     */
    private void operateLogAfterProcess(SystemLogInfo systemLogInfo) {
        SystemLogFacade systemLogFacade = SpringUtil.getBean(SystemLogFacade.class);
        // 获取异步线程池
        Executor asyncTaskExecutor = SpringUtil.getBean("asyncTaskExecutor", Executor.class);
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            Map<String, String> headers = CodeHelper.getHeaders();
            // 异步执行 防止阻碍主线程
            CompletableFuture.runAsync(() -> {
                CodeHelper.setHeadersToShare(headers);
                RequestContextHolder.setRequestAttributes(attributes);
                systemLogFacade.saveOperateLog(systemLogInfo);
            }, asyncTaskExecutor);
        }
    }

    /**
     * <p>登录日志后置处理</p>
     *
     * @param systemLogInfo 日志模型
     * @author guocq
     * @date 2023/1/4 10:08
     */
    private void loginLogAfterProcess(SystemLogInfo systemLogInfo) {
        if (StrUtil.isBlank(systemLogInfo.getUserAccount())) {
            return;
        }
        SystemLogFacade systemLogFacade = SpringUtil.getBean(SystemLogFacade.class);
        // 获取异步线程池
        Executor asyncTaskExecutor = SpringUtil.getBean("asyncTaskExecutor", Executor.class);
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            Map<String, String> headers = CodeHelper.getHeaders();
            // 异步执行 防止阻碍主线程
            CompletableFuture.runAsync(() -> {
                CodeHelper.setHeadersToShare(headers);
                RequestContextHolder.setRequestAttributes(attributes);
                systemLogFacade.saveLoginLog(systemLogInfo);
            }, asyncTaskExecutor);
        }
    }

}

@Setter
@Getter
class ProcessResult {
    private static final Logger log = LoggerFactory.getLogger(ProcessResult.class);

    /**
     * 异常信息
     */
    AetherException error;

    /**
     * 返回结果
     */
    Object result;

    /**
     * 是否成功
     */
    boolean success;

    ProcessResult(AetherException error, Object result) {
        this.error = error;
        this.result = result;
        this.success = error == null || error.getCode() == null;
        this.log();
    }

    /**
     * <p>输出日志</p>
     *
     * @author guocq
     * @date 2023/1/4 9:45
     */
    public void log() {
        log(log);
    }

    /**
     * <p>输出日志</p>
     *
     * @param logger 日志对象
     * @author guocq
     * @date 2023/1/4 9:45
     */
    public void log(Logger logger) {
        if (logger == null) {
            logger = log;
        }
        boolean debugEnabled = logger.isDebugEnabled();
        if (!debugEnabled) {
            return;
        }
        if (!this.success) {
            logger.error(error.getMessage(), error);
            return;
        }
        logger.debug("操作成功");
        return;
    }
}
