package top.finder.aether.data.core.support.aspect;

import ch.qos.logback.classic.Level;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import top.finder.aether.common.support.annotation.AppBlocking;
import top.finder.aether.common.support.annotation.AppGroup;
import top.finder.aether.common.support.enums.AppBlockingType;
import top.finder.aether.common.support.helper.LoggerHelper;
import top.finder.aether.common.support.helper.ReflectHelper;
import top.finder.aether.common.support.helper.SpringBeanHelper;
import top.finder.aether.data.core.support.helper.AppHelper;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>应用拦截切面</p>
 *
 * @author guocq
 * @since 2023/1/5
 */
@Slf4j
@Aspect
@Component
public class AppBlockAspect {

    @Pointcut("@annotation(top.finder.aether.common.support.annotation.AppBlocking)")

    public void blockPointCut() {

    }

    @Pointcut("@annotation(top.finder.aether.common.support.annotation.AppGroup)")
    public void groupPointCut() {

    }

    @Around("@annotation(appBlocking)")
    public Object appBlockingAround(ProceedingJoinPoint point, AppBlocking appBlocking) throws Throwable {
        LoggerHelper.aopLog(log, Level.INFO, point, "{}appBlocking拦截开始");
        Object[] args = point.getArgs();
        AppBlockingType value = appBlocking.value();
        boolean allowIntercept = allowIntercept(appBlocking);
        if (allowIntercept && AppBlockingType.BEFORE.equals(value)) {
            before(appBlocking, args);
        }
        Object result = point.proceed(args);
        if (allowIntercept && AppBlockingType.AFTER.equals(value)) {
            after(appBlocking, args, result);
        }
        return result;
    }

    @Around("@annotation(appGroup)")
    public Object appGroupAround(ProceedingJoinPoint point, AppGroup appGroup) throws Throwable {
        AppBlocking[] blocking = appGroup.blocking();
        LoggerHelper.aopLog(log, Level.INFO, point, "{}appGroup拦截开始");
        if (ArrayUtil.isEmpty(blocking)) {
            LoggerHelper.aopLog(log, point, "{}appGroup拦截获取AppBlocking为空，将不再进行拦截");
            return point.proceed();
        }
        Object[] args = point.getArgs();
        Map<AppBlockingType, List<AppBlocking>> blockMap = Arrays.stream(blocking)
                .filter(this::allowIntercept)
                .collect(Collectors.groupingBy(AppBlocking::value));
        List<AppBlocking> before = blockMap.get(AppBlockingType.BEFORE);
        if (CollUtil.isNotEmpty(before)) {
            before.forEach(appBlocking -> before(appBlocking, args));
        }
        Object result = point.proceed(args);
        List<AppBlocking> after = blockMap.get(AppBlockingType.AFTER);
        if (CollUtil.isNotEmpty(after)) {
            after.forEach(appBlocking -> after(appBlocking, args, result));
        }
        return result;
    }

    /**
     * <p>判断是否允许拦截</p>
     *
     * @param appBlocking 拦截注解
     * @return boolean
     * @author guocq
     * @date 2023/1/5 15:52
     */
    private boolean allowIntercept(AppBlocking appBlocking) {
        Set<String> appNameSet = Sets.newHashSet(appBlocking.appNames());
        String sourceApp = AppHelper.findCurrentRequestSourceApp();
        boolean contains = appNameSet.contains(sourceApp);
        boolean intercept = appBlocking.isIntercept();
        return (contains && intercept) || (!contains && !intercept);
    }

    /**
     * <p>前置操作</p>
     *
     * @param appBlocking 拦截注解
     * @param args        参数列表
     * @author guocq
     * @date 2023/1/5 15:25
     */
    private static void before(AppBlocking appBlocking, Object[] args) {
        Class<?> beanClass = appBlocking.execBean();
        String methodName = appBlocking.execMethod();
        Object bean = SpringBeanHelper.getBean(beanClass);
        ReflectHelper.invoke(bean, methodName, args);
    }

    /**
     * <p>后置操作</p>
     *
     * @param appBlocking 拦截注解
     * @param args        参数列表
     * @param result      返回值
     * @author guocq
     * @date 2023/1/5 15:30
     */
    private static void after(AppBlocking appBlocking, Object[] args, Object result) {
        Class<?> beanClass = appBlocking.execBean();
        String methodName = appBlocking.execMethod();
        Set<Object> argSet = Sets.newHashSet(args);
        argSet.add(result);
        Object bean = SpringBeanHelper.getBean(beanClass);
        ReflectHelper.invoke(bean, methodName, ArrayUtil.toArray(argSet, Object.class));
    }
}
