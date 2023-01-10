package top.finder.aether.data.core.support.annotation;

import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;
import top.finder.aether.data.core.config.ApiScanConfig;

import java.lang.annotation.*;

/**
 * <p>Api扫描注解</p>
 *
 * @author guocq
 * @since 2023/1/10
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Import(ApiScanConfig.class)
public @interface ApiScan {
    @AliasFor("basePackages")
    String[] value() default {};
    @AliasFor("value")
    String[] basePackages() default {};
}
