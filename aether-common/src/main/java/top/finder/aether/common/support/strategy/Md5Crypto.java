package top.finder.aether.common.support.strategy;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * <p>Md5加解密策略</p>
 *
 * @author guocq
 * @since 2022/12/28
 */
@Component(value = "md5Crypto")
public class Md5Crypto implements CryptoStrategy {
    private static final Logger log = LoggerFactory.getLogger(Md5Crypto.class);

    /**
     * <p>加密实现方法</p>
     *
     * @param content 被加密内容
     * @return java.lang.String
     * @author guocq
     * @date 2022/12/28 13:50
     */
    @Override
    public String encrypt(String content) {
        log.debug("使用md5加盐对文本[{}]进行加密", content);
        if (StrUtil.isBlank(content)) {
            log.warn("被加密内容[content={}]为空", content);
            return StrUtil.EMPTY;
        }
        return Md5SaltCrypto.getDigester().digestHex(content);
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
        log.debug("使用md5对待匹配字符串[{}]和已经加密的密文[{}]进行匹配", plainText, encryptText);
        if (StrUtil.hasBlank(plainText, encryptText)) {
            log.warn("待匹配字符串[plainText={}]和已经加密的密文[encryptText={}]为空", plainText, encryptText);
            return false;
        }
        return ObjectUtil.equals(Md5SaltCrypto.getDigester().digestHex(plainText), encryptText);
    }
}
