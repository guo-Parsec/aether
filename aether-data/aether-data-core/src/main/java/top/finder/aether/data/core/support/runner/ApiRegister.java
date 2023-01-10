package top.finder.aether.data.core.support.runner;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;
import top.finder.aether.common.support.pool.CommonConstantPool;
import top.finder.aether.data.core.model.ApiModel;
import top.finder.aether.data.core.support.annotation.ApiResource;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>全局API注册</p>
 *
 * @author guocq
 * @since 2023/1/10
 */
public class ApiRegister {
    private static final Logger log = LoggerFactory.getLogger(ApiRegister.class);

    /**
     * 待扫描的包
     */
    private static String[] basePackages;

    /**
     * apiModel存储map
     */
    private static Map<String, ApiModel> apiModelMap;

    /**
     * <p>注册API</p>
     *
     * @author guocq
     * @date 2023/1/6 10:19
     */
    public static void register() {
        if (ArrayUtil.isEmpty(basePackages)) {
            return;
        }
        log.info("api注册启动,待扫描的包为{}", Arrays.toString(basePackages));
        Set<Class<?>> apiClassSet = Sets.newHashSet();
        Arrays.stream(basePackages).forEach(basePackage -> {
            apiClassSet.addAll(ClassUtil.scanPackage(basePackage, clazz -> clazz.isAnnotationPresent(RestController.class)));
        });
        log.info("扫描到的api类共{}个", apiClassSet.size());
        apiModelMap = CollUtil.isEmpty(apiModelMap) ? Maps.newHashMap() : apiModelMap;
        apiClassSet.forEach(apiClass -> {
            List<Method> methods = ClassUtil.getPublicMethods(apiClass, method -> method.isAnnotationPresent(ApiResource.class));
            methods.forEach(method -> {
                ApiModel apiModel = ApiModel.of(apiClass, method);
                final String id = apiModel.getId();
                if (apiModelMap.containsKey(id)) {
                    log.error("当前API[{}]的id[{}]与已被注册的API[{}]重复，请确认API名称的唯一性", apiModel, id, apiModelMap.get(id));
                    throw new IllegalStateException("API已被注册");
                }
                log.info("api成功注册[{}]", apiModel);
                apiModelMap.put(id, apiModel);
            });
        });
        log.info("api注册完成，共注册成功{}个", apiModelMap.size());
    }

    /**
     * <p>设置基础扫描包</p>
     *
     * @param basePackages 基本扫描包
     * @author guocq
     * @date 2023/1/10 9:38
     */
    public static void setBasePackages(String[] basePackages) {
        ApiRegister.basePackages = basePackages;
    }

    /**
     * <p>获取全部api集合</p>
     *
     * @return {@link List}
     * @author guocq
     * @date 2023/1/10 11:25
     */
    public static List<ApiModel> collectApiModels() {
        String contextPath = SpringUtil.getProperty(CommonConstantPool.CONTEXT_PATH);
        List<ApiModel> list = Lists.newLinkedList();
        apiModelMap.forEach((id, apiModel) -> {
            list.add(apiModel.completeUri(contextPath));
        });
        return list;
    }
}
