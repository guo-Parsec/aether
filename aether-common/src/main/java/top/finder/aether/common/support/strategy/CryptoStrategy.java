package top.finder.aether.common.support.strategy;

import java.util.Map;

/**
 * <p>加解密策略器</p>
 *
 * @author guocq
 * @since 2022/12/28
 */
public interface CryptoStrategy {

    /**
     * <p>判断是否需要额外选项</p>
     *
     * @return boolean
     * @author guocq
     * @date 2022/12/28 15:06
     */
    default boolean isOptionNeed() {
        return false;
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
    default String encrypt(String content, Map<String, Object> option) {
        throw new UnsupportedOperationException("当前加解密策略不支持额外选项，请使用[encrypt(String)]");
    }

    /**
     * <p>加密实现方法</p>
     *
     * @param content 被加密内容
     * @return java.lang.String
     * @author guocq
     * @date 2022/12/28 13:50
     */
    String encrypt(String content);

    /**
     * <p>判断{@code plainText}与已经加密的密文{@code encryptText}是否匹配</p>
     *
     * @param plainText   待匹配字符串
     * @param encryptText 已经加密的密文
     * @return boolean
     * @author guocq
     * @date 2022/12/28 13:55
     */
    boolean match(String plainText, String encryptText);

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
    default boolean match(String plainText, String encryptText, Map<String, Object> option) {
        throw new UnsupportedOperationException("当前加解密策略不支持额外选项，请使用[match(String, String)]");
    }

    /**
     * <p>对{@code encryptText}解密</p>
     *
     * @param encryptText 已经加密的密文
     * @return java.lang.String
     * @author guocq
     * @date 2022/12/28 14:51
     */
    default String decrypt(String encryptText) {
        throw new UnsupportedOperationException("当前加解密策略不支持额外选项，请使用[match(String, String)]");
    }
}
