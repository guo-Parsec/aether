package top.finder.aether.security.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskDecorator;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import top.finder.aether.security.api.SecurityContext;
import top.finder.aether.security.api.entity.SecuritySignature;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * <p>异步线程配置类</p>
 *
 * @author guocq
 * @since 2022/12/30
 */
@Configuration
@EnableAsync
public class SecurityAsyncThreadConfig {
    @Bean("recordAsyncTaskExecutor")
    public ThreadPoolTaskExecutor securityAsyncTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 核心线程数10
        executor.setCorePoolSize(3);
        // 最大线程数20
        executor.setMaxPoolSize(5);
        // 缓冲队列200
        executor.setQueueCapacity(20);
        // 允许线程的空闲时间60秒
        executor.setKeepAliveSeconds(60);
        // 线程池名的前缀
        executor.setThreadNamePrefix("recordExecutor-");
        // 拒绝策略
        executor.setTaskDecorator(new SecurityContextTaskDecorator());
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        // 等所有任务结束后再关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(true);
        return executor;
    }

}

class SecurityContextTaskDecorator implements TaskDecorator {    
    @Override
    public Runnable decorate(Runnable runnable) {
        SecuritySignature securitySignature = SecurityContext.init();
        return () -> {
            try {
                SecurityContext.put(securitySignature);
                runnable.run();
            } finally {
                SecurityContext.destroy();
            }
        };
    }
}
