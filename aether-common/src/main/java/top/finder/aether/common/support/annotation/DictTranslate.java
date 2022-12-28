package top.finder.aether.common.support.annotation;

import java.lang.annotation.*;

/**
 * <p>字典转义</p>
 *
 * @author guocq
 * @since 2022/12/13
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
public @interface DictTranslate {
    /**
     * 字典值代表的属性名称 指定根据该值进行转义
     *
     * @return
     */
    String value();

    /**
     * 字典类型 根据该值进行转义
     * @return
     */
    String type();
}
