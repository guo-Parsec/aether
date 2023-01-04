package top.finder.aether.common.support.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

import static top.finder.aether.common.support.pool.CommonConstantPool.ALL_TEXT;

/**
 * <p>服务间调用对外api</p>
 *
 * @author guocq
 * @since 2023/1/4
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface FeignApi {
    @AliasFor("accessAppNames")
    String[] value() default {ALL_TEXT};

    @AliasFor("value")
    String[] accessAppNames() default {ALL_TEXT};
}
