package top.finder.aether.common.support.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * <p>拦截的bean</p>
 *
 * @author guocq
 * @since 2023/1/6
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface BlockBean {
    /**
     * bean的名称 同name
     */
    @AliasFor("name")
    String value() default "";

    /**
     * bean的名称 同value
     */
    @AliasFor("value")
    String name() default "";
}
