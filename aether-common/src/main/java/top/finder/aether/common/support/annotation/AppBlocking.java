package top.finder.aether.common.support.annotation;

import top.finder.aether.common.support.enums.AppBlockingType;

import java.lang.annotation.*;

/**
 * <p>应用拦截注解</p>
 *
 * @author guocq
 * @since 2023/1/5
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface AppBlocking {
    /**
     * 拦截的应用名称
     */
    String[] appNames();

    /**
     * 拦截时执行的bean
     */
    Class<?> execBean();

    /**
     * 拦截时执行的方法名称
     */
    String execMethod();

    /**
     * 拦截类型 默认前置拦截
     */
    AppBlockingType value() default AppBlockingType.BEFORE;

    /**
     * 是否拦截 为true时 拦截到appNames的app才会执行方法；为false时 拦截到不时appNames的app才会执行方法;
     */
    boolean isIntercept() default true;
}
