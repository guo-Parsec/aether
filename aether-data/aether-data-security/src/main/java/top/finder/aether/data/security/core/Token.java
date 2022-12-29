package top.finder.aether.data.security.core;

import lombok.Getter;
import lombok.Setter;
import top.finder.aether.common.support.pool.SecurityConstantPool;

/**
 * <p>令牌存储信息</p>
 *
 * @author guocq
 * @since 2022/12/28
 */
@Setter
@Getter
public class Token {
    /**
     * 令牌id
     */
    private String id;

    /**
     * 令牌名称
     */
    private String name;

    /**
     * 令牌内容
     */
    private String content;

    Token(String id, String name) {
        this.id = id;
        this.name = name;
        if (id != null) {
            this.content = SecurityConstantPool.EFFECTIVE_TOKEN_PREFIX + id;
        }
    }

    public static Token of(String id, String name) {
        return new Token(id, name);
    }

    public static Token ofId(String id) {
        Token of = of(id, null);
        of.setContent(SecurityConstantPool.EFFECTIVE_TOKEN_PREFIX + id);
        return of;
    }

    public static Token ofName(String name) {
        return of(null, name);
    }
}
