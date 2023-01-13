package top.finder.aether.data.core.support.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;

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

    /**
     * <p>根据code查询资源类型枚举</p>
     *
     * @param code 资源类型码
     * @return {@link Optional}
     * @author guocq
     * @date 2023/1/12 10:41
     */
    public static Optional<ResourceType> findByCode(String code) {
        final String lowerCaseCode = code.toLowerCase(Locale.ROOT);
        return Arrays.stream(ResourceType.values()).filter(resourceType -> resourceType.code.equals(lowerCaseCode))
                .findFirst();
    }
    
    public static boolean equals(ResourceType resourceType, String code) {
        Optional<ResourceType> optional = findByCode(code);
        return optional.filter(resourceType::equals).isPresent();
    }
}
