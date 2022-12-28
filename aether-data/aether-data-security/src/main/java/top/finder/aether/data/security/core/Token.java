package top.finder.aether.data.security.core;

import lombok.Getter;
import lombok.Setter;

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

    Token() {
    }

    Token(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Token of(String id, String name) {
        return new Token(id, name);
    }

    public static Token ofId(String id) {
        return of(id ,null);
    }

    public static Token ofName(String name) {
        return of(null ,name);
    }
}
