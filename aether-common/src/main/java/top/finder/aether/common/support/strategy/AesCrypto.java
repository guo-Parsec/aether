package top.finder.aether.common.support.strategy;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.symmetric.AES;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import top.finder.aether.common.properties.AetherAesProperties;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * <p>aes加解密策略</p>
 *
 * @author guocq
 * @since 2022/12/28
 */
@Component(value = "aesCrypto")
public class AesCrypto implements CryptoStrategy {
    private static final Logger log = LoggerFactory.getLogger(AesCrypto.class);
    private final AetherAesProperties aetherAesProperties;

    private static final String DEFAULT_KEY = "abc123456";

    public AesCrypto(AetherAesProperties aetherAesProperties) {
        this.aetherAesProperties = aetherAesProperties;
    }

    /**
     * <p>加密实现方法</p>
     *
     * @param content 被加密内容
     * @return java.lang.String
     * @author guocq
     * @date 2022/12/28 13:50·
     */
    @Override
    public String encrypt(String content) {
        log.debug("使用aes对文本[{}]进行加密", content);
        if (StrUtil.isBlank(content)) {
            log.warn("被加密内容[content={}]为空", content);
            return StrUtil.EMPTY;
        }
        return getAes().encryptHex(content);
    }

    /**
     * <p>判断{@code plainText}与已经加密的密文{@code encryptText}是否匹配</p>
     *
     * @param plainText   待匹配字符串
     * @param encryptText 已经加密的密文
     * @return boolean
     * @author guocq
     * @date 2022/12/28 13:55
     */
    @Override
    public boolean match(String plainText, String encryptText) {
        log.debug("使用aes对待匹配字符串[{}]和已经加密的密文[{}]进行匹配", plainText, encryptText);
        if (StrUtil.hasBlank(plainText, encryptText)) {
            log.warn("待匹配字符串[plainText={}]和已经加密的密文[encryptText={}]为空", plainText, encryptText);
            return false;
        }
        return ObjectUtil.equals(decrypt(encryptText), plainText);
    }

    /**
     * <p>对{@code encryptText}解密</p>
     *
     * @param encryptText 已经加密的密文
     * @return java.lang.String
     * @author guocq
     * @date 2022/12/28 14:51
     */
    @Override
    public String decrypt(String encryptText) {
        log.debug("使用aes对已经加密的密文[{}]进行解密", encryptText);
        if (StrUtil.isBlank(encryptText)) {
            log.warn("已经加密的密文[encryptText={}]为空", encryptText);
            return StrUtil.EMPTY;
        }
        return getAes().decryptStr(encryptText, CharsetUtil.CHARSET_UTF_8);
    }

    /**
     * <p>获取aes</p>
     *
     * @return cn.hutool.crypto.symmetric.AES
     * @author guocq
     * @date 2022/12/28 14:53
     */
    AES getAes() {
        Mode mode = aetherAesProperties.getMode();
        Padding padding = aetherAesProperties.getPadding();
        log.debug("aes加密模式为[{}],补码方式为[{}]", mode, padding);
        String key = Optional.ofNullable(aetherAesProperties.getKey()).filter(text -> !StrUtil.isEmpty(text)).orElse(DEFAULT_KEY);
        log.debug("aes加密密钥为[{}]", key);
        String offset = aetherAesProperties.getOffset();
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        if (StrUtil.isEmpty(offset)) {
            log.debug("aes不使用偏移量");
            return new AES(mode, padding, keyBytes);
        }
        log.debug("aes加密偏移量为[{}]", offset);
        return new AES(mode, padding, keyBytes, offset.getBytes(StandardCharsets.UTF_8));
    }
}
