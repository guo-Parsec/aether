package top.finder.aether.common.properties;

import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import top.finder.aether.common.support.pool.CommonConstantPool;

/**
 * <p>AES加解密属性类</p>
 *
 * @author guocq
 * @since 2022/12/28
 */
@Component
@ConfigurationProperties(prefix = CommonConstantPool.APP_PROPERTIES_COMMON_PREFIX + "crypto.aes")
public class AetherAesProperties {
    /**
     * 加密算法模式
     *
     * @see Mode
     */
    @Setter
    @Getter
    private Mode mode = Mode.ECB;

    /**
     * 补码方式
     *
     * @see Padding
     */
    @Setter
    @Getter
    private Padding padding = Padding.PKCS5Padding;

    /**
     * 密钥
     */
    @Setter
    @Getter
    private String key;

    /**
     * 偏移量
     */
    @Setter
    @Getter
    private String offset;
}
