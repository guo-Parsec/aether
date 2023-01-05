package top.finder.aether.common.support.annotation;

import java.lang.annotation.*;

/**
 * <p>app注解组</p>
 *
 * @author guocq
 * @since 2023/1/5
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface AppGroup {
    /**
     * 拦截组
     */
    AppBlocking[] blocking() default {};
}
