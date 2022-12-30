package top.finder.aether.common.support.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import top.finder.aether.common.model.LogModel;
import top.finder.aether.common.support.annotation.OperateLog;
import top.finder.aether.common.support.api.Apis;
import top.finder.aether.common.support.exception.AetherException;
import top.finder.aether.common.support.helper.CodeHelper;
import top.finder.aether.common.support.helper.LogHelper;
import top.finder.aether.common.support.helper.SpringBeanHelper;
import top.finder.aether.common.support.listener.SysLogListener;
import top.finder.aether.common.support.pool.CommonConstantPool;

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
public class LogAspect {
    private static final Logger log = LoggerFactory.getLogger(LogAspect.class);
    private final ApplicationEventPublisher publisher;

    public LogAspect(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Around("@annotation(operateLog)")
    public Object around(ProceedingJoinPoint point, OperateLog operateLog) {
        LogModel logModel = new LogModel();
        logModel.setLogTime(LocalDateTime.now());
        String className = point.getTarget().getClass().getName();
        String methodName = point.getSignature().getName();
        log.debug("{}#{}正在记录操作日志", className, methodName);
        long startTime = System.currentTimeMillis();
        AetherException error;
        Object result;
        try {
            result = point.proceed();
            logModel.setResult(CommonConstantPool.SUCCESS);
        } catch (AetherException exception) {
            error = exception;
            result = Apis.failed(exception);
            logModel.error(error);
        } catch (Throwable exception) {
            error = new AetherException(exception, exception.getMessage());
            result = Apis.failed(error);
            logModel.error(error);
        }
        long endTime = System.currentTimeMillis();
        logModel.setTimeConsuming(endTime - startTime);
        LogHelper.buildLogModel(logModel);
        // 设置RequestAttributes线程共享，防止日志子线程异步调用时无法获得请求信息
        asyncShareRequest();
        SysLogListener bean = SpringBeanHelper.getBean(SysLogListener.class);
        Executor asyncTaskExecutor = SpringBeanHelper.getBean("asyncTaskExecutor", Executor.class);
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            Map<String, String> headers = CodeHelper.getHeaders();
            CompletableFuture.runAsync(() -> {
                CodeHelper.setHeadersToShare(headers);
                RequestContextHolder.setRequestAttributes(attributes);
                bean.saveOperateLog(logModel);
            }, asyncTaskExecutor);
        }
        return result;
    }

    private static void asyncShareRequest() {


    }

}
