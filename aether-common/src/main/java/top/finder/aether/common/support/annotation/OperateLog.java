package top.finder.aether.common.support.annotation;

import java.lang.annotation.*;

/**
 * <p>操作日志</p>
 *
 * @author guocq
 * @since 2022/12/30
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperateLog {
    /**
     * 是否需要用户登录
     */
    boolean value() default true;

    /**
     * 账户所在参数
     */
    String accountField() default "";
}
