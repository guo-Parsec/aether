package top.finder.aether.security.api.aspect;

import cn.hutool.core.date.StopWatch;
import cn.hutool.extra.servlet.ServletUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import top.finder.aether.system.api.holders.SysOperateRecordHolder;
import top.finder.aether.common.support.exception.AetherException;
import top.finder.aether.common.support.helper.Builder;
import top.finder.aether.common.support.helper.CodeHelper;
import top.finder.aether.security.api.entity.SecuritySignature;
import top.finder.aether.security.api.facade.SecurityFacade;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Optional;

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

    @Pointcut("execution(public * top.finder.aether.*.*.controller.*Controller.*(..))")
    public void pointCut() {

    }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        LocalDateTime now = LocalDateTime.now();
        Object[] args = point.getArgs();
        StopWatch stopWatch = new StopWatch("operateRecord");
        stopWatch.start();
        Object result = null;
        AetherException error = null;
        try {
            result = point.proceed(args);
        } catch (AetherException e) {
            error = e;
        } catch (Throwable e) {
            error = new AetherException(e);
        }
        stopWatch.stop();
        long totalTimeMillis = stopWatch.getTotalTimeMillis();
        SysOperateRecordHolder holder = buildRecordHolder(error, now, totalTimeMillis, CodeHelper.getHttpServletRequest());
        return result;
    }

    private SysOperateRecordHolder buildRecordHolder(AetherException error, LocalDateTime operateTime, long timeSpent, HttpServletRequest request) {
        Optional<AetherException> optional = Optional.ofNullable(error);
        Builder<SysOperateRecordHolder> builder = Builder.builder(SysOperateRecordHolder::new)
                // 1 代表失败 0 代表成功
                .with(SysOperateRecordHolder::setOperateResult, optional.isPresent() ? 1 : 0)
                .with(SysOperateRecordHolder::setOperateCode, optional.isPresent() ? optional.get().getCode() : 200)
                .with(SysOperateRecordHolder::setErrorReason, optional.map(AetherException::getMessage).orElse("-"))
                .with(SysOperateRecordHolder::setOperateIp, ServletUtil.getClientIP(request))
                .with(SysOperateRecordHolder::setOperateTime, operateTime)
                .with(SysOperateRecordHolder::setTimeSpent, timeSpent)
                .enhanceWith(SysOperateRecordHolder::setOperateUri, request::getRequestURI)
                .enhanceWith(SysOperateRecordHolder::setOperateMethod, request::getMethod);
        try {
            SecuritySignature securitySignature = SecurityFacade.findSecuritySignature();
            if (securitySignature == null) {
                throw new AetherException("用户未登录");
            }
            return builder.enhanceWith(SysOperateRecordHolder::setOperateId, securitySignature::getId)
                    .enhanceWith(SysOperateRecordHolder::setOperateAccount, securitySignature::getAccount)
                    .build();
        } catch (Exception e) {
            log.warn("记录请求{}时用户未登录", request.getRequestURI());
            return builder.build();
        }
    }
}
