package top.finder.aether.data.aop.core;

import top.finder.aether.data.aop.model.AfterProcessModel;
import top.finder.aether.data.aop.model.BeforeProcessModel;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * <p>通用切面类</p>
 *
 * @author guocq
 * @since 2022/12/14
 */
public class CommonAspect implements AspectInterface, Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * <p>目标方法执行前的操作</p>
     *
     * @param target 目标对象
     * @param method 目标方法
     * @param args   参数
     * @return {@link BeforeProcessModel} 前置处理模型
     * @author guocq
     * @date 2022/12/14 18:57
     */
    @Override
    public BeforeProcessModel before(Object target, Method method, Object[] args) {
        return BeforeProcessModel.process(method);
    }

    /**
     * <p>目标方法执行后的操作</p>
     *
     * @param target 目标对象
     * @param method 目标方法
     * @param args   参数
     * @param result 目标方法执行返回值
     * @return {@link AfterProcessModel} 后置处理模型
     * @author guocq
     * @date 2022/12/14 19:13
     */
    @Override
    public AfterProcessModel after(Object target, Method method, Object[] args, Object result) {
        return AfterProcessModel.process(method);
    }
}
