package top.finder.aether.data.aop.support.helper;

import top.finder.aether.data.aop.core.AspectInterface;
import top.finder.aether.data.aop.support.factory.AbstractProxyFactory;

/**
 * <p>代理辅助类</p>
 *
 * @author guocq
 * @since 2022/12/15
 */
public final class ProxyHelper {
    /**
     * <p>使用切面代理对象</p>
     *
     * @param target      目标对象
     * @param aspectClass 切面对象类
     * @return T
     * @author guocq
     * @date 2022/12/15 10:29
     */
    public static <T> T proxy(T target, Class<? extends AspectInterface> aspectClass) {
        return AbstractProxyFactory.createProxy(target, aspectClass);
    }

    /**
     * <p>使用切面代理对象</p>
     *
     * @param target 被代理对象
     * @param aspect 切面对象
     * @return T 被代理对象类型
     * @author guocq
     * @date 2022/12/15 10:30
     */
    public static <T> T proxy(T target, AspectInterface aspect) {
        return AbstractProxyFactory.createProxy(target, aspect);
    }
}
