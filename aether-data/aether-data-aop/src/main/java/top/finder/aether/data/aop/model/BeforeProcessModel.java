package top.finder.aether.data.aop.model;

import lombok.Getter;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * <p>前置处理模型</p>
 *
 * @author guocq
 * @since 2022/12/14
 */
@Getter
public class BeforeProcessModel implements ProcessModel {
    private static final long serialVersionUID = -6532152076521869492L;

    private boolean processed;

    private Method method;

    private Object result;

    private BeforeProcessModel() {
    }

    private BeforeProcessModel(boolean processed, Method method, Object result) {
        this.processed = processed;
        this.method = method;
        boolean isVoidMethod = VOID_METHOD_RETURN_TYPE.equals(method.getReturnType().getName());
        if (!processed && Objects.isNull(result) && !isVoidMethod) {
            throw new IllegalArgumentException("前置处理模型中如果不继续后置处理并且方法返回值不为null时,方法返回结果[result]不能为空");
        }
        this.result = result;
    }

    /**
     * <p>继续加载后置处理</p>
     *
     * @param method 当前操作方法
     * @param result 返回结果
     * @return {@link BeforeProcessModel}
     * @author guocq
     * @date 2022/12/14 18:53
     */
    public static BeforeProcessModel process(Method method, Object result) {
        return new BeforeProcessModel(Boolean.TRUE, method, result);
    }

    /**
     * <p>继续加载后置处理</p>
     *
     * @param method 当前操作方法
     * @return {@link BeforeProcessModel}
     * @author guocq
     * @date 2022/12/14 18:53
     */
    public static BeforeProcessModel process(Method method) {
        return BeforeProcessModel.process(method, null);
    }

    /**
     * <p>停止加载后置处理</p>
     *
     * @param method 当前操作方法
     * @param result
     * @return {@link BeforeProcessModel}
     * @author guocq
     * @date 2022/12/14 18:53
     */
    public static BeforeProcessModel stopProcess(Method method, Object result) {
        return new BeforeProcessModel(Boolean.FALSE, method, result);
    }
}
