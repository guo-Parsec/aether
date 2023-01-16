package top.finder.aether.data.core.support.aspect;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Sets;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import top.finder.aether.common.support.annotation.FeignApi;
import top.finder.aether.common.support.api.Apis;
import top.finder.aether.common.support.api.CommonHttpStatus;
import top.finder.aether.common.support.helper.CodeHelper;
import top.finder.aether.common.support.pool.CommonConstantPool;
import top.finder.aether.common.utils.AopLoggerUtil;
import top.finder.aether.data.core.support.runner.SystemSetting;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Set;

import static top.finder.aether.common.support.pool.CommonConstantPool.ALL_TEXT;
import static top.finder.aether.data.core.support.pool.SystemSettingConstantPool.FEIGN_SECRET;

/**
 * <p>feignApi校验切面 防止服务间调用避免暴露给外部</p>
 *
 * @author guocq
 * @since 2023/1/4
 */
@Aspect
@Component
public class FeignApiValidAspect {
    private static final Logger log = LoggerFactory.getLogger(FeignApiValidAspect.class);

    private final SystemSetting systemSetting;

    public FeignApiValidAspect(SystemSetting systemSetting) {
        this.systemSetting = systemSetting;
    }

    @Around("@annotation(top.finder.aether.common.support.annotation.FeignApi)")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        AopLoggerUtil.aopLog(log, point, "feignApi校验{}开始");
        Method method = ((MethodSignature) point.getSignature()).getMethod();
        FeignApi feignApi = AnnotationUtils.findAnnotation(method, FeignApi.class);
        if (feignApi != null && validCurrentAppIsAccessible(feignApi)) {
            log.debug("feignApi校验成功");
            AopLoggerUtil.aopLog(log, point, "feignApi校验{}开始");
            return point.proceed(point.getArgs());
        }
        return Apis.failed(CommonHttpStatus.ILLEGAL_REQUESTS);
    }

    /**
     * <p>验证当前app是否允许反复问</p>
     *
     * @param feignApi feignApi注解
     * @return boolean
     * @author guocq
     * @date 2023/1/4 17:16
     */
    private boolean validCurrentAppIsAccessible(FeignApi feignApi) {
        Set<String> appSet = Sets.newHashSet(feignApi.value());
        HttpServletRequest request = CodeHelper.getHttpServletRequest();
        String requestPath = request.getRequestURI();
        String sourceApp = request.getHeader(CommonConstantPool.FEIGN_SOURCE_APP_HEAD_KEY);
        if (StrUtil.isBlank(sourceApp)) {
            log.error("请求头中不存在[source-app]，验证失败");
            return false;
        }
        if (!appSet.contains(sourceApp) && !appSet.contains(ALL_TEXT)) {
            log.error("[{}]不允许访问当前接口[{}]，验证失败", sourceApp, requestPath);
            return false;
        }
        String headerFeignSecret = request.getHeader(FEIGN_SECRET);
        if (StrUtil.isBlank(headerFeignSecret)) {
            log.error("请求密钥为空，验证失败");
            return false;
        }
        String feignSecret = StrUtil.toStringOrNull(systemSetting.getAppSetting(sourceApp, FEIGN_SECRET));
        if (!headerFeignSecret.equals(feignSecret)) {
            log.error("请求密钥验证失败");
            return false;
        }
        return true;
    }
}
