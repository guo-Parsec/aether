package top.finder.aether.data.common.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import top.finder.aether.common.support.pool.CommonConstantPool;

/**
 * <p>雪花算法属性类</p>
 *
 * @author guocq
 * @since 2022/12/13
 */
@Component
@ConfigurationProperties(prefix = CommonConstantPool.APP_PROPERTIES_COMMON_PREFIX + "snowflake")
public class AetherSnowflakeProperties {
    /**
     * 数据中心ID
     */
    @Getter
    @Setter
    private long datacenterId = 1L;

    /**
     * 机器ID
     */
    @Getter
    @Setter
    private long machineId = 1L;
}
