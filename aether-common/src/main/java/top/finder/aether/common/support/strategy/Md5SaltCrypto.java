package top.finder.aether.common.support.strategy;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * <p>md5加盐加解密策略</p>
 *
 * @author guocq
 * @since 2022/12/28
 */
@Component(value = "md5SaltCrypto")
public class Md5SaltCrypto implements CryptoStrategy {
    private static final Logger log = LoggerFactory.getLogger(Md5SaltCrypto.class);

    /**
     * 额外选项key - 盐
     */
    public static final String OPTION_KEY_SALT = "salt";

    private static final String DEFAULT_SALT = "AETHER";

    /**
     * <p>判断是否需要额外选项</p>
     *
     * @return boolean
     * @author guocq
     * @date 2022/12/28 15:06
     */
    @Override
    public boolean isOptionNeed() {
        return true;
    }

    /**
     * <p>通过额外选项实现加密</p>
     *
     * @param content 被加密内容
     * @param option  额外选项
     * @return java.lang.String
     * @author guocq
     * @date 2022/12/28 14:06
     */
    @Override
    public String encrypt(String content, Map<String, Object> option) {
        log.debug("使用md5加盐对文本[{}]进行加密", content);
        if (StrUtil.isBlank(content)) {
            log.warn("被加密内容[content={}]为空", content);
            return StrUtil.EMPTY;
        }
        return getDigester(option).digestHex(content);
    }

    /**
     * <p>通过{@code option}判断{@code plainText}与已经加密的密文{@code encryptText}是否匹配</p>
     *
     * @param plainText   待匹配字符串
     * @param encryptText 已经加密的密文
     * @param option      额外选项
     * @return java.lang.String
     * @author guocq
     * @date 2022/12/28 14:06
     */
    @Override
    public boolean match(String plainText, String encryptText, Map<String, Object> option) {
        log.debug("使用md5加盐对待匹配字符串[{}]和已经加密的密文[{}]进行匹配", plainText, encryptText);
        if (StrUtil.hasBlank(plainText, encryptText)) {
            log.warn("待匹配字符串[plainText={}]和已经加密的密文[encryptText={}]为空", plainText, encryptText);
            return false;
        }
        return ObjectUtil.equals(getDigester(option).digestHex(plainText), encryptText);
    }

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
        return encrypt(content, null);
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
        return match(plainText, encryptText, null);
    }

    /**
     * <p>获取Digester(携带盐)</p>
     *
     * @param option 额外选项
     * @return cn.hutool.crypto.digest.Digester
     * @author guocq
     * @date 2022/12/28 14:20
     */
    static Digester getDigester(Map<String, Object> option) {
        return getDigester(true, option);
    }

    /**
     * <p>获取Digester(不携带盐)</p>
     *
     * @return cn.hutool.crypto.digest.Digester
     * @author guocq
     * @date 2022/12/28 14:20
     */
    static Digester getDigester() {
        return getDigester(false, null);
    }

    /**
     * <p>获取Digester</p>
     *
     * @param option      额外选项
     * @param isCarrySalt 是否携带盐
     * @return cn.hutool.crypto.digest.Digester
     * @author guocq
     * @date 2022/12/28 14:20
     */
    static Digester getDigester(final boolean isCarrySalt, Map<String, Object> option) {
        Digester md5 = new Digester(DigestAlgorithm.MD5);
        if (!isCarrySalt) {
            return md5;
        }
        if (MapUtil.isEmpty(option) || !option.containsKey(OPTION_KEY_SALT)) {
            log.warn("option内未传入key为[{}]的值，系统将使用默认盐[{}]", OPTION_KEY_SALT, DEFAULT_SALT);
            option.put(OPTION_KEY_SALT, DEFAULT_SALT);
        }
        String salt = StrUtil.toStringOrNull(option.get(OPTION_KEY_SALT));
        log.warn("MD5加盐加密使用盐为[{}]", salt);
        md5.setSalt(salt.getBytes(StandardCharsets.UTF_8));
        return md5;
    }
}
