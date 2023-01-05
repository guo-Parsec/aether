package top.finder.aether.common.support.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import top.finder.aether.common.properties.AetherSystemProperties;
import top.finder.aether.common.support.api.Apis;
import top.finder.aether.common.support.api.CommonHttpStatus;
import top.finder.aether.common.support.helper.CodeHelper;
import top.finder.aether.common.support.helper.LoggerHelper;
import top.finder.aether.common.support.pool.CommonConstantPool;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>网关校验</p>
 *
 * @author guocq
 * @since 2023/1/4
 */
@Aspect
@Component
public class GatewayValidAspect {
    private static final Logger log = LoggerFactory.getLogger(GatewayValidAspect.class);

    private final AetherSystemProperties aetherSystemProperties;

    public GatewayValidAspect(AetherSystemProperties aetherSystemProperties) {
        this.aetherSystemProperties = aetherSystemProperties;
    }

    @Pointcut("execution(* top.finder.aether..controller..*Controller.*(..))")
    public void pointCut() {

    }

    @Around("pointCut()")
    public Object valid(ProceedingJoinPoint point) throws Throwable {
        if (aetherSystemProperties.getAllowNonGatewayRequest()) {
            log.debug("系统不设置网关校验限制，直接放行");
            return point.proceed();
        }
        LoggerHelper.aopLog(log, point, "网关校验{}开始");
        HttpServletRequest request = CodeHelper.getHttpServletRequest();
        if (!Boolean.parseBoolean(request.getHeader(CommonConstantPool.IS_FROM_GATEWAY))) {
            LoggerHelper.aopLog(log, point, "{}不是来自于网关, 将限制访问");
            return Apis.failed(CommonHttpStatus.ILLEGAL_REQUESTS);
        }
        LoggerHelper.aopLog(log, point, "网关校验{}成功");
        return point.proceed();
    }
}
