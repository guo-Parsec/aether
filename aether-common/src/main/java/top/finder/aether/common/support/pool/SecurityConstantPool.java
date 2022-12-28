package top.finder.aether.common.support.pool;

/**
 * <p>安全认证类常量池</p>
 *
 * @author guocq
 * @since 2022/12/27
 */
public interface SecurityConstantPool {

    /**
     * 令牌存储在请求头的key名
     */
    String TOKEN_IN_HEAD_KEY = "Authorization";

    /**
     * 有效令牌文本前缀
     */
    String EFFECTIVE_TOKEN_PREFIX = "BEARER ";
}
