package top.finder.aether.gateway.config;

import com.google.common.collect.Lists;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.support.NameUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.List;

/**
 * <p>聚合swagger配置类</p>
 *
 * @author guocq
 * @since 2022/12/27
 */
@Primary
@Component
public class GatewaySwaggerResourceSupport implements SwaggerResourcesProvider {
    /**
     * swagger默认的url后缀
     */
    private static final String API_URI = "/v3/api-docs";
    /**
     * 网关配置项，对应配置文件中配置的spring.cloud.gateway所有子项
     */
    private final GatewayProperties gatewayProperties;

    /**
     * 网关路由
     */
    private final RouteLocator routeLocator;

    public GatewaySwaggerResourceSupport(GatewayProperties gatewayProperties, RouteLocator routeLocator) {
        this.gatewayProperties = gatewayProperties;
        this.routeLocator = routeLocator;
    }

    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = Lists.newArrayList();
        List<String> routes = Lists.newArrayList();
        routeLocator.getRoutes().subscribe(route -> routes.add(route.getId()));
        // 遍历配置文件中配置的所有服务
        gatewayProperties.getRoutes().stream()
                // 过滤同名服务
                .filter(routeDefinition -> routes.contains(routeDefinition.getId()))
                .forEach(route -> route.getPredicates().stream()
                        // 忽略配置文件中断言中配置的Path为空的配置项
                        .filter(predicateDefinition -> ("Path").equalsIgnoreCase(predicateDefinition.getName()))
                        // 将Path中的路由地址由**改为v2/api-docs，swagger就是通过这个地址来获取接口文档数据的，可以通过访问：ip:port/v2/api-docs来体会接口数据
                        .forEach(predicateDefinition -> resources
                                .add(swaggerResource(route.getId(), predicateDefinition.getArgs()
                                        .get(NameUtils.GENERATED_NAME_PREFIX + "0").replace("**", API_URI)))));
        return resources;
    }

    private SwaggerResource swaggerResource(String name, String location) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion("3.0");
        return swaggerResource;
    }
}
