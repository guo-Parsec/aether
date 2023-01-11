package top.finder.aether.gateway;

import cn.hutool.extra.spring.SpringUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;
import top.finder.aether.data.cache.config.RedisConfig;
import top.finder.aether.data.cache.support.helper.RedisHelper;

/**
 * <p>网关启动类</p>
 *
 * @author guocq
 * @since 2022/12/27
 */
@EnableDiscoveryClient
@SpringBootApplication
@Import(value = {RedisConfig.class, SpringUtil.class, RedisHelper.class})
public class GatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
}
