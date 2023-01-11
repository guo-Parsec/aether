package top.finder.aether.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import top.finder.aether.data.core.support.annotation.ApiScan;
import top.finder.aether.data.core.support.annotation.EnableBlock;

/**
 * <p>认证中心启动类</p>
 *
 * @author guocq
 * @since 2022/12/28
 */
@ComponentScan({
        "top.finder.aether.common",
        "top.finder.aether.data",
        "top.finder.aether.base",
        "top.finder.aether.security"
})
@ApiScan({"top.finder.aether.security.core.controller"})
@EnableBlock({"top.finder.aether.security.core.service"})
@EnableFeignClients(basePackages = {"top.finder.aether.**.api"})
@EnableDiscoveryClient
@SpringBootApplication
public class SecurityApplication {
    public static void main(String[] args) {
        SpringApplication.run(SecurityApplication.class, args);
    }
}
