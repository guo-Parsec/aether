package top.finder.aether.common.support.annotation;

import java.lang.annotation.*;

/**
 * <p>字典校验</p>
 *
 * @author guocq
 * @since 2023/01/05
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
public @interface DictValid {
    /**
     * 字典类型 根据该值进行转义
     */
    String type();
}
