package top.finder.aether.data.security.core;

import top.finder.aether.common.support.pool.SecurityConstantPool;

import java.io.Serializable;
import java.util.Optional;
import java.util.Set;

/**
 * <p>安全认证主体接口</p>
 *
 * @author guocq
 * @since 2022/12/28
 */
public interface ISecuritySubject<U extends Serializable> extends Serializable {
    /**
     * <p>获取用户主体信息</p>
     *
     * @return U 用户主体信息
     * @author guocq
     * @date 2022/12/28 9:54
     */
    U getSubject();

    /**
     * <p>获取用户主键信息</p>
     *
     * @return java.io.Serializable
     * @author guocq
     * @date 2022/12/28 9:57
     */
    Long getId();

    /**
     * <p>获取账户信息</p>
     *
     * @return java.lang.String
     * @author guocq
     * @date 2022/12/28 9:57
     */
    String getAccount();

    /**
     * <p>获取用户所拥有的角色码</p>
     *
     * @return {@link Set<String>}
     * @author guocq
     * @date 2022/12/28 9:48
     */
    Set<String> getRoles();

    /**
     * <p>获取用户所拥有的权限码</p>
     *
     * @return {@link Set<String>}
     * @author guocq
     * @date 2022/12/28 9:48
     */
    Set<String> getPermissions();

    /**
     * <p>获取用户所拥有的请求路径</p>
     *
     * @return {@link Set<String>}
     * @author guocq
     * @date 2022/12/28 9:48
     */
    Set<String> getUrls();

    /**
     * <p>获取令牌信息</p>
     *
     * @return {@link Token}
     * @author guocq
     * @date 2022/12/28 9:52
     */
    Token getToken();

    /**
     * <p>设置令牌信息</p>
     *
     * @param token 令牌信息
     * @author guocq
     * @date 2022/12/28 10:03
     */
    void setToken(Token token);

    /**
     * <p>注入令牌id信息</p>
     *
     * @param tokenId 令牌id
     * @author guocq
     * @date 2022/12/28 10:03
     */
    default void setTokenId(String tokenId) {
        this.setToken(Optional.ofNullable(this.getToken()).map(token -> {
            token.setId(tokenId);
            token.setContent(SecurityConstantPool.EFFECTIVE_TOKEN_PREFIX + tokenId);
            return token;
        }).orElse(Token.ofId(tokenId)));
    }
}
