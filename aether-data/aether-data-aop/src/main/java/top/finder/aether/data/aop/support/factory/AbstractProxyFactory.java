package top.finder.aether.data.aop.support.factory;

import cn.hutool.core.util.ReflectUtil;
import top.finder.aether.data.aop.core.AspectInterface;

import java.io.Serializable;

/**
 * <p>代理工厂</p>
 *
 * @author guocq
 * @since 2022/12/15
 */
public abstract class AbstractProxyFactory implements Serializable {
    private static final long serialVersionUID = -5914050424160446478L;

    /**
     * <p>创建代理</p>
     *
     * @param target      被代理对象
     * @param aspectClass 切面实现类，自动实例化
     * @return T 代理对象类型
     * @author guocq
     * @date 2022/12/15 10:17
     */
    public <T> T proxy(T target, Class<? extends AspectInterface> aspectClass) {
        return proxy(target, ReflectUtil.newInstanceIfPossible(aspectClass));
    }

    /**
     * <p>创建代理对象</p>
     *
     * @param target 被代理的目标对象
     * @param aspect 切面实现类
     * @return T
     * @author guocq
     * @date 2022/12/15 10:10
     */
    public abstract <T> T proxy(T target, AspectInterface aspect);

    /**
     * <p>创建代理对象</p>
     *
     * @param target      被代理的目标对象
     * @param aspectClass 切面实现类的class对象
     * @return T
     * @author guocq
     * @date 2022/12/15 10:27
     */
    public static <T> T createProxy(T target, Class<? extends AspectInterface> aspectClass) {
        return createProxy(target, ReflectUtil.newInstance(aspectClass));
    }

    /**
     * <p>创建代理</p>
     *
     * @param target 被代理的目标对象
     * @param aspect 切面实现类
     * @return T
     * @author guocq
     * @date 2022/12/15 10:26
     */
    public static <T> T createProxy(T target, AspectInterface aspect) {
        return create().proxy(target, aspect);
    }

    /**
     * <p>创建代理工厂</p>
     *
     * @return {@link AbstractProxyFactory}
     * @author guocq
     * @date 2022/12/15 10:23
     */
    public static AbstractProxyFactory create() {
        return create(CglibProxy.class);
    }

    /**
     * <p>创建代理工厂</p>
     *
     * @param factoryClass 工厂类型
     * @return {@link AbstractProxyFactory}
     * @author guocq
     * @date 2022/12/15 10:23
     */
    public static AbstractProxyFactory create(Class<? extends AbstractProxyFactory> factoryClass) {
        return ReflectUtil.newInstance(factoryClass);
    }
}
