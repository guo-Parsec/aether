package top.finder.aether.data.aop.model;

import lombok.Getter;

import java.lang.reflect.Method;

/**
 * <p>后置处理模型</p>
 *
 * @author guocq
 * @since 2022/12/14
 */
@Getter
public class AfterProcessModel implements ProcessModel {
    private static final long serialVersionUID = -4932525737153108063L;

    private boolean processed;

    private Method method;


    private AfterProcessModel() {
    }

    private AfterProcessModel(boolean processed, Method method) {
        this.processed = processed;
        this.method = method;
    }

    /**
     * <p>继续加载后置处理</p>
     *
     * @param method 代理方法
     * @return {@link AfterProcessModel}
     * @author guocq
     * @date 2022/12/14 18:53
     */
    public static AfterProcessModel process(Method method) {
        return new AfterProcessModel(Boolean.TRUE, method);
    }
}
