package top.finder.aether.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

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
        "top.finder.aether.auth"
})
@EnableFeignClients(basePackages = {"top.finder.aether.**.api"})
@EnableDiscoveryClient
@SpringBootApplication
public class AuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }
}
