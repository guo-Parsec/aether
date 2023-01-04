package top.finder.aether.common.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import top.finder.aether.common.support.pool.CommonConstantPool;

/**
 * <p>系统属性类</p>
 *
 * @author guocq
 * @since 2022/12/28
 */
@Component
@ConfigurationProperties(prefix = CommonConstantPool.APP_PROPERTIES_COMMON_PREFIX + "system")
public class AetherSystemProperties {
    /**
     * 是否允许非网关请求
     */
    @Setter
    @Getter
    private Boolean allowNonGatewayRequest = Boolean.FALSE;
}
