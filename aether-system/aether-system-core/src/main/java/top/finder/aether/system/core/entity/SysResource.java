package top.finder.aether.system.core.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import top.finder.aether.data.common.entity.BaseDataEntity;
import top.finder.aether.data.core.support.enums.ResourceType;

import java.util.StringJoiner;

/**
 * <p>系统资源表</p>
 *
 * @author guocq
 * @since 2023/1/10
 */
@Getter
@Setter
@TableName(value = "sys_resource")
public class SysResource extends BaseDataEntity {
    private static final long serialVersionUID = 7796147232012279094L;
    /**
     * 资源类别码值
     */
    private String resourceTypeCode;

    /**
     * 资源类别名称
     */
    private String resourceTypeName;

    /**
     * 权限码值
     */
    private String resourceCode;

    /**
     * 权限名称
     */
    private String resourceName;

    /**
     * 资源路径
     */
    private String resourceUrl;

    /**
     * 资源描述
     */
    private String resourceDesc;

    /**
     * <p>设置资源类型</p>
     *
     * @param resourceType 资源类型
     * @author guocq
     * @date 2023/1/16 15:14
     */
    public void setResourceType(ResourceType resourceType) {
        if (resourceType == null) {
            resourceType = ResourceType.AUTH;
        }
        this.resourceTypeCode = resourceType.getCode();
        this.resourceTypeName = resourceType.getName();
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", SysResource.class.getSimpleName() + "[", "]")
                .add("resourceTypeCode='" + resourceTypeCode + "'")
                .add("resourceTypeName='" + resourceTypeName + "'")
                .add("resourceCode='" + resourceCode + "'")
                .add("resourceName='" + resourceName + "'")
                .add("resourceUrl='" + resourceUrl + "'")
                .add("resourceDesc='" + resourceDesc + "'")
                .add("deleteAt=" + deleteAt)
                .add("gmtCreate=" + gmtCreate)
                .add("gmtModify=" + gmtModify)
                .add("id=" + id)
                .toString();
    }
}