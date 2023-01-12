package top.finder.aether.data.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import top.finder.aether.data.core.support.enums.ResourceType;

import java.util.Set;
import java.util.StringJoiner;

/**
 * <p>资源映射</p>
 *
 * @author guocq
 * @since 2023/1/12
 */
@Setter
@Getter
public class ResourceMapping {
    /**
     * 资源类型
     */
    private ResourceType resourceType;

    /**
     * 资源标识
     */
    private String resourceCode;

    /**
     * 资源名称
     */
    private String resourceName;

    /**
     * 资源url集合
     */
    @JsonIgnore
    private transient Set<String> resourceUrlSet;

    /**
     * 资源描述
     */
    private String resourceDesc;

    /**
     * 资源路径
     */
    private String resourceUrl;

    @Override
    public String toString() {
        return new StringJoiner(", ", ResourceMapping.class.getSimpleName() + "[", "]")
                .add("resourceType=" + resourceType)
                .add("resourceCode='" + resourceCode + "'")
                .add("resourceName='" + resourceName + "'")
                .add("resourceUrlSet=" + resourceUrlSet)
                .add("resourceDesc='" + resourceDesc + "'")
                .toString();
    }
}
