package top.finder.aether.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import top.finder.aether.data.security.support.helper.WebfluxHelper;
import top.finder.aether.data.security.support.webflux.SecurityWebfluxContext;
import top.finder.aether.gateway.properties.WhiteListProperties;

import java.util.List;

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
        if (SecurityWebfluxContext.isReleaseAllowed(exchange, urls)) {
            return chain.filter(exchange);
        }
        return WebfluxHelper.unauthorizedWrite(exchange.getResponse());
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
