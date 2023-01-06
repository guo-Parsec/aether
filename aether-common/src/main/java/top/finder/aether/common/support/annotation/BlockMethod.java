package top.finder.aether.common.support.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * <p>拦截的方法</p>
 *
 * @author guocq
 * @since 2023/1/6
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface BlockMethod {
    /**
     * 方法名称 同name 为空时默认取 {@code method.getName()} 转首字母小写的驼峰命名
     */
    @AliasFor("name")
    String value() default "";

    /**
     * 方法名称 同value 为空时默认取 {@code method.getName()} 转首字母小写的驼峰命名
     */
    @AliasFor("value")
    String name() default "";
}
