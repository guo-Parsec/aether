package top.finder.aether.data.core.support.enums;

import lombok.Getter;

/**
 * <p>资源类型</p>
 *
 * @author guocq
 * @since 2023/1/10
 */
public enum ResourceType {
    /**
     * 匿名可访问资源
     */
    ANON("anon", "匿名可访问资源"),

    /**
     * 登录可访问资源
     */
    LOGIN("login", "登录可访问资源"),

    /**
     * 认证权限可访问资源
     */
    AUTH("auth", "认证权限可访问资源"),
    ;
    @Getter
    private final String code;
    @Getter
    private final String name;

    ResourceType(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
