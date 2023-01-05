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

    /**
     * 是否需要非空校验 true：字段需要非空校验，当字段为空时则抛出异常 false：字段不需要非空校验，当字段为空时则不进行字段字典校验
     */
    boolean emptyValid() default true;
}
