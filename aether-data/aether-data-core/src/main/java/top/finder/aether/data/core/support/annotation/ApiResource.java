package top.finder.aether.data.core.support.annotation;

import top.finder.aether.data.core.support.enums.ResourceType;

import java.lang.annotation.*;

/**
 * <p>api资源注解</p>
 *
 * @author guocq
 * @since 2023/1/10
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface ApiResource {
    /**
     * 资源类型
     *
     * @return 资源类型
     */
    ResourceType resourceType() default ResourceType.AUTH;

    /**
     * 资源码
     *
     * @return 资源码
     */
    String code();

    /**
     * 资源名称
     *
     * @return 资源名称
     */
    String name();

    /**
     * 资源排序值
     *
     * @return 资源排序值
     */
    int sort();

    /**
     * 资源描述
     *
     * @return 资源描述
     */
    String desc();
}
