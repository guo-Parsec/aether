package top.finder.aether.security.api.support.aspect;

import cn.hutool.core.date.StopWatch;
import io.swagger.annotations.ApiOperation;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.finder.aether.common.support.api.Apis;
import top.finder.aether.common.support.exception.AetherException;
import top.finder.aether.common.support.helper.CodeHelper;
import top.finder.aether.common.utils.AopLoggerUtils;
import top.finder.aether.data.core.support.enums.ResourceType;
import top.finder.aether.data.core.support.helper.AppHelper;
import top.finder.aether.security.api.support.async.OperateRecordAsync;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * <p>操作记录切面</p>
 *
 * @author guocq
 * @since 2023/1/12
 */
@Aspect
@Component
public class OperateRecordAspect {
    private static final Logger log = LoggerFactory.getLogger(OperateRecordAspect.class);

    /**
     * 操作记录异步服务
     */
    private final OperateRecordAsync async;

    public OperateRecordAspect(OperateRecordAsync async) {
        this.async = async;
    }

    @Around("execution(public * top.finder.aether.*.*.controller.*Controller.*(..))")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        LocalDateTime recordTime = LocalDateTime.now();
        Object[] args = point.getArgs();
        if (!allowRecord(point)) {
            return point.proceed(args);
        }
        AopLoggerUtils.aopLog(log, point, "{}开始记录日志");
        Object result = null;
        AetherException error = null;
        StopWatch stopWatch = new StopWatch("operateRecord");
        stopWatch.start();
        try {
            result = point.proceed(args);
        } catch (AetherException e) {
            error = e;
            result = Apis.failed(error);
        } catch (Throwable e) {
            error = new AetherException(e);
            result = Apis.failed(error);
        }
        stopWatch.stop();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            RequestContextHolder.setRequestAttributes(attributes, true);
            async.execSaveOperateRecord(error, recordTime, stopWatch.getTotalTimeMillis());
        }
        return result;
    }

    /**
     * <p>判断当前请求是否允许记录日志</p>
     *
     * @param point 切入点
     * @return boolean true 允许记录日志 false不允许记录日志
     * @author guocq
     * @date 2023/1/13 14:12
     */
    private boolean allowRecord(ProceedingJoinPoint point) {
        HttpServletRequest request = CodeHelper.getHttpServletRequest();
        MethodSignature signature = (MethodSignature) point.getSignature();
        ApiOperation apiOperation = signature.getMethod().getAnnotation(ApiOperation.class);
        // 匿名可访问的请求以及不对外暴露的请求不允许记录日志
        if (apiOperation == null || apiOperation.hidden() || ResourceType.equals(ResourceType.ANON, apiOperation.nickname())) {
            return false;
        }
        String method = request.getMethod();
        // get请求或服务间调用请求不记录日志
        return !RequestMethod.GET.name().equals(method) && !AppHelper.isFeignRequest(request);
    }
}
