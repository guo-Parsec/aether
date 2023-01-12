package top.finder.aether.data.core.support.runner;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ClassUtil;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import top.finder.aether.common.support.annotation.BlockBean;
import top.finder.aether.common.support.annotation.BlockMethod;
import top.finder.aether.data.core.model.Blocker;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static top.finder.aether.common.utils.LoggerUtil.logAetherError;

/**
 * <p>拦截器注册</p>
 *
 * @author guocq
 * @since 2023/1/6
 */
@Component
public class BlockRegister implements ApplicationRunner {
    private static final Logger log = LoggerFactory.getLogger(BlockRegister.class);
    private static String[] basePackages;

    private static Map<String, Blocker> blockerMap;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        register();
    }

    /**
     * <p>注册拦截器</p>
     *
     * @author guocq
     * @date 2023/1/6 10:19
     */
    private void register() {
        if (ArrayUtil.isEmpty(basePackages)) {
            return;
        }
        log.info("拦截器注册启动,待扫描的包为{}", Arrays.toString(basePackages));
        Set<Class<?>> blockBeanClassSet = Sets.newHashSet();
        Arrays.stream(basePackages).forEach(basePackage -> {
            blockBeanClassSet.addAll(ClassUtil.scanPackage(basePackage, clazz -> clazz.isAnnotationPresent(BlockBean.class)));
        });
        log.debug("拦截器扫描到被[BlockBean]注解标识的类共{}个", blockBeanClassSet.size());
        blockerMap = CollUtil.isEmpty(blockerMap) ? Maps.newHashMap() : blockerMap;
        blockBeanClassSet.forEach(clazz -> {
            List<Method> methods = ClassUtil.getPublicMethods(clazz, method -> method.isAnnotationPresent(BlockMethod.class));
            methods.forEach(method -> {
                Blocker blocker = Blocker.of(clazz, method);
                final String blockId = blocker.getBlockId();
                if (blockerMap.containsKey(blockId)) {
                    throw logAetherError(log, "当前拦截器[{}]的拦截id[{}]与已被注册的拦截器[{}]重复，请确认拦截器bean名称与method名称的唯一性", blocker, blockId, blockerMap.get(blockId));
                }
                log.trace("bean[{}]中的[{}]被成功注册为拦截器, 拦截器id为{}", blocker.getBeanName(), blocker.getMethodName(), blockId);
                blockerMap.put(blockId, blocker);
            });
        });
        log.info("拦截器注册完成，共注册成功{}个", blockerMap.size());
    }

    public static void setBasePackages(String[] basePackages) {
        BlockRegister.basePackages = basePackages;
    }

    /**
     * <p>根据拦截器id获取拦截器</p>
     *
     * @param blockerId 拦截器id
     * @return {@link Blocker}
     * @author guocq
     * @date 2023/1/6 10:51
     */
    public static Blocker findBlocker(String blockerId) {
        Assert.notNull(blockerId, "拦截器id不能为空");
        if (CollUtil.isEmpty(blockerMap) || !blockerMap.containsKey(blockerId)) {
            throw logAetherError(log, "获取失败,未找到id为{}的拦截器", blockerId);
        }
        return blockerMap.get(blockerId);
    }

    /**
     * <p>反射实现方法</p>
     *
     * @param blockerId 拦截器id
     * @param args      参数列表
     * @author guocq
     * @date 2023/1/6 11:00
     */
    public static void invoke(String blockerId, Object... args) {
        Blocker blocker = findBlocker(blockerId);
        Method method = blocker.getMethod();
        Class<?> beanClass = blocker.getBeanClass();
        Object bean = blocker.getBean();
        try {
            method.invoke(bean, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw logAetherError(log, e);
        }
    }
}
