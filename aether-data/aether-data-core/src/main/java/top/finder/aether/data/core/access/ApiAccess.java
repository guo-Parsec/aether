package top.finder.aether.data.core.access;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Sets;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import top.finder.aether.common.support.helper.UrlHelper;
import top.finder.aether.common.utils.LoggerUtil;
import top.finder.aether.data.core.entity.ResourceMapping;
import top.finder.aether.data.core.support.enums.ResourceType;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>API访问类</p>
 *
 * @author guocq
 * @since 2023/1/12
 */
@Component
public class ApiAccess {
    private static final Logger log = LoggerFactory.getLogger(ApiAccess.class);
    private final WebApplicationContext context;

    public ApiAccess(WebApplicationContext context) {
        this.context = context;
    }

    /**
     * <p>获取资源映射列表</p>
     *
     * @return {@link List}
     * @author guocq
     * @date 2023/1/12 10:53
     */
    public List<ResourceMapping> getResourceMappingList() {
        // 获取请求适配器
        RequestMappingHandlerMapping mapping = context.getBean("requestMappingHandlerMapping", RequestMappingHandlerMapping.class);
        // 从适配器中获取全部方法map
        Map<RequestMappingInfo, HandlerMethod> methodMap = mapping.getHandlerMethods();
        return methodMap.entrySet().stream().map(entry -> {
            RequestMappingInfo requestMappingInfo = entry.getKey();
            HandlerMethod handlerMethod = entry.getValue();
            Class<?> declaringClass = handlerMethod.getMethod().getDeclaringClass();
            String packageName = ClassUtil.getPackage(declaringClass);
            String controllerUrl = "top.finder.aether.**";
            if (!UrlHelper.isMatch(controllerUrl, packageName)) {
                return null;
            }
            ResourceMapping resourceMapping = initResourceMapping(handlerMethod);
            if (resourceMapping == null) {
                return null;
            }
            initUrls(requestMappingInfo, resourceMapping);
            String methodName = handlerMethod.getMethod().getName();
            log.trace("方法[{}]已被映射为资源映射类型{}", methodName, requestMappingInfo);
            return resourceMapping;
        }).filter(ObjectUtil::isNotEmpty).collect(Collectors.toList());
    }

    /**
     * <p>初始化资源url集合</p>
     *
     * @param requestMappingInfo 请求映射信息
     * @param resourceMapping    资源映射
     * @author guocq
     * @date 2023/1/12 10:32
     */
    private void initUrls(RequestMappingInfo requestMappingInfo, ResourceMapping resourceMapping) {
        Set<String> urlSet = Optional.ofNullable(requestMappingInfo.getPatternsCondition())
                .map(PatternsRequestCondition::getPatterns).orElse(Sets.newHashSet());
        resourceMapping.setResourceUrlSet(urlSet);
    }

    /**
     * <p>初始化资源映射</p>
     *
     * @param handlerMethod 处理方法
     * @author guocq
     * @date 2023/1/12 10:50
     */
    private ResourceMapping initResourceMapping(HandlerMethod handlerMethod) {
        ResourceMapping resourceMapping = new ResourceMapping();
        ApiOperation apiOperation = handlerMethod.getMethodAnnotation(ApiOperation.class);
        if (apiOperation != null) {
            if (apiOperation.hidden()) {
                return null;
            }
            resourceMapping.setResourceName(apiOperation.value());
            resourceMapping.setResourceDesc(apiOperation.notes());
            String nickname = apiOperation.nickname();
            if (StrUtil.isBlank(nickname)) {
                resourceMapping.setResourceType(ResourceType.AUTH);
                return resourceMapping;
            }
            resourceMapping.setResourceType(ResourceType.findByCode(nickname).orElse(ResourceType.AUTH));
            return resourceMapping;
        }
        throw LoggerUtil.logAetherError(log, "当前方法{}未被[ApiOperation]注解标识", handlerMethod.getMethod().getName());
    }
}
