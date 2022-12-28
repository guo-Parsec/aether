package top.finder.aether.data.aop.support.factory;

import net.sf.cglib.proxy.Enhancer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.finder.aether.data.aop.core.AspectInterface;
import top.finder.aether.data.aop.interceptor.CglibProxyInterceptor;

/**
 * <p>cglib代理实现</p>
 *
 * @author guocq
 * @since 2022/12/15
 */
public class CglibProxy extends AbstractProxyFactory {
    private static final long serialVersionUID = 3873069697456281284L;

    private static final Logger log = LoggerFactory.getLogger(CglibProxy.class);

    /**
     * <p>创建代理对象</p>
     *
     * @param target 被代理的目标对象
     * @param aspect 切面实现类
     * @return T
     * @author guocq
     * @date 2022/12/15 10:10
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T> T proxy(T target, AspectInterface aspect) {
        log.debug("使用[cglib]代理对象");
        final Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback(new CglibProxyInterceptor(target, aspect));
        return (T) enhancer.create();
    }
}
