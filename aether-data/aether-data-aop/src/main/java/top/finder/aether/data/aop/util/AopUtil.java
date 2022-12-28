package top.finder.aether.data.aop.util;

import net.sf.cglib.proxy.Enhancer;
import top.finder.aether.data.aop.interceptor.CglibProxyInterceptor;
import top.finder.aether.data.aop.core.AspectInterface;

/**
 * <p>AOP工具类</p>
 *
 * @author guocq
 * @since 2022/12/14
 */
public class AopUtil {
    @SuppressWarnings("unchecked")
    public static <T> T proxy(T target, AspectInterface aspectInterface) {
        final Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback(new CglibProxyInterceptor(target, aspectInterface));
        return (T) enhancer.create();
    }
}
