package top.finder.aether.data.aop.interceptor;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import top.finder.aether.data.aop.core.AspectInterface;
import top.finder.aether.data.aop.model.BeforeProcessModel;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * <p>Cglib代理拦截器</p>
 *
 * @author guocq
 * @since 2022/12/14
 */
public class CglibProxyInterceptor implements MethodInterceptor, Serializable {
    private static final long serialVersionUID = 1L;

    private final Object target;
    private final AspectInterface aspectInterface;

    public CglibProxyInterceptor(Object target, AspectInterface aspectInterface) {
        this.target = target;
        this.aspectInterface = aspectInterface;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        Object result = beforeProcessor(method, args, proxy);
        return afterProcessor(method, args, result);
    }

    /**
     * <p>前置处理</p>
     *
     * @param method 代理方法
     * @param args   代理方法参数
     * @param proxy  方法代理对象
     * @return java.lang.Object
     * @author guocq
     * @date 2022/12/14 19:07
     */
    private Object beforeProcessor(Method method, Object[] args, MethodProxy proxy) throws Throwable {
        BeforeProcessModel before = aspectInterface.before(target, method, args);
        if (before.isProcessed()) {
            return proxy.invoke(target, args);
        }
        return before.getResult();
    }

    /**
     * <p>后置处理</p>
     *
     * @param method 代理方法
     * @param args   代理方法参数
     * @param result 代理方法返回结果
     * @return java.lang.Object
     * @author guocq
     * @date 2022/12/14 19:18
     */
    private Object afterProcessor(Method method, Object[] args, Object result) {
        return aspectInterface.after(target, method, args, result).isProcessed() ? result : null;
    }
}
