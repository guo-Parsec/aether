package top.finder.aether.common.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import top.finder.aether.common.support.pool.CommonConstantPool;

/**
 * <p>Swagger属性类</p>
 *
 * @author guocq
 * @since 2022/12/27
 */
@Component
@ConfigurationProperties(prefix = CommonConstantPool.APP_PROPERTIES_COMMON_PREFIX + "swagger")
public class AetherSwaggerProperties {
    /**
     * 是否开启swagger
     */
    @Setter
    @Getter
    private Boolean enable;
}
