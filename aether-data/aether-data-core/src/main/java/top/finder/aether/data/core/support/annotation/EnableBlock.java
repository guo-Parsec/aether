package top.finder.aether.data.core.support.annotation;

import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;
import top.finder.aether.data.core.config.EnableBlockAutoConfig;

import java.lang.annotation.*;

/**
 * <p>开启拦截</p>
 *
 * @author guocq
 * @since 2023/1/6
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Import(EnableBlockAutoConfig.class)
public @interface EnableBlock {
    @AliasFor("basePackages")
    String[] value() default {};
    @AliasFor("value")
    String[] basePackages() default {};
}
