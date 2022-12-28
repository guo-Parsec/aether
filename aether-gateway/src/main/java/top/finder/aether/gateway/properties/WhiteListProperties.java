package top.finder.aether.gateway.properties;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import top.finder.aether.common.support.pool.CommonConstantPool;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>白名单URL配置类</p>
 *
 * @author guocq
 * @since 2022/12/27
 */
@Configuration
@RefreshScope
@ConfigurationProperties(prefix = CommonConstantPool.APP_PROPERTIES_COMMON_PREFIX + "gateway.white")
public class WhiteListProperties {
    /**
     * 放行URL配置
     */
    @Setter
    @Getter
    private List<String> urls = new ArrayList<>();
}
