package top.finder.aether.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import top.finder.aether.data.common.utils.WebfluxUtil;
import top.finder.aether.gateway.properties.WhiteListProperties;
import top.finder.aether.security.api.facade.SecurityWebfluxFacade;

import java.util.List;

import static top.finder.aether.common.support.pool.CommonConstantPool.IS_FROM_GATEWAY;
/**
 * <p>安全认证过滤器</p>
 *
 * @author guocq
 * @since 2022/12/28
 */
@Component
public class SecurityFilter implements GlobalFilter, Ordered {
    private final WhiteListProperties whiteListProperties;

    public SecurityFilter(WhiteListProperties whiteListProperties) {
        this.whiteListProperties = whiteListProperties;
    }

    /**
     * <p>核心过滤器处理</p>
     *
     * @param exchange 当前请求上下文
     * @param chain    提供委托给下一个筛选器的方法
     * @return {@link Mono<Void>}
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        List<String> urls = whiteListProperties.getUrls();
        if (SecurityWebfluxFacade.isReleaseAllowed(exchange, urls)) {
            ServerHttpRequest newRequest = exchange.getRequest().mutate().header(IS_FROM_GATEWAY, Boolean.TRUE.toString()).build();
            exchange = exchange.mutate().request(newRequest).build();
            return chain.filter(exchange);
        }
        return WebfluxUtil.unauthorizedWrite(exchange.getResponse());
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
