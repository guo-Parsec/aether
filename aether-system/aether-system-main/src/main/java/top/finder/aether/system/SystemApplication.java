package top.finder.aether.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import top.finder.aether.data.core.support.annotation.EnableBlock;

/**
 * <p>基础服务启动类</p>
 *
 * @author guocq
 * @since 2022/12/13
 */
@ComponentScan({
        "top.finder.aether.common",
        "top.finder.aether.data",
        "top.finder.aether.system",
        "top.finder.aether.security.api",
})
@EnableBlock({"top.finder.aether.system.core.service"})
@EnableFeignClients(basePackages = {"top.finder.aether.**.api"})
@EnableDiscoveryClient
@SpringBootApplication
public class SystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(SystemApplication.class, args);
    }
}
