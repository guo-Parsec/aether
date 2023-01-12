package top.finder.aether.base.core.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;
import top.finder.aether.data.common.entity.BaseDataEntity;
import top.finder.aether.data.core.model.ApiModel;

import java.util.List;
import java.util.Set;
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
     * 资源排序
     */
    private Integer resourceSort;

    /**
     * 资源描述
     */
    private String resourceDesc;

    /**
     * <p>apiModel转换</p>
     *
     * @param apiModel apiModel
     * @return {@link List}
     * @author guocq
     * @date 2023/1/10 11:43
     */
    public static List<SysResource> apiModelToResource(ApiModel apiModel) {
        Set<String> uri = apiModel.getUri();
        List<SysResource> sysResources = Lists.newArrayListWithCapacity(uri.size());
        SysResource sysResource = new SysResource();
        sysResource.setResourceTypeCode(apiModel.getResourceType().getCode());
        sysResource.setResourceTypeName(apiModel.getResourceType().getName());
        sysResource.setResourceCode(apiModel.getResourceCode());
        sysResource.setResourceName(apiModel.getResourceName());
        sysResource.setResourceSort(apiModel.getSort());
        sysResource.setResourceDesc(apiModel.getDesc());
        for (String url : uri) {
            sysResource.setResourceUrl(url);
            sysResources.add(sysResource);
        }
        return sysResources;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", SysResource.class.getSimpleName() + "[", "]")
                .add("resourceTypeCode='" + resourceTypeCode + "'")
                .add("resourceTypeName='" + resourceTypeName + "'")
                .add("resourceCode='" + resourceCode + "'")
                .add("resourceName='" + resourceName + "'")
                .add("resourceUrl='" + resourceUrl + "'")
                .add("resourceSort=" + resourceSort)
                .add("resourceDesc='" + resourceDesc + "'")
                .add("deleteAt=" + deleteAt)
                .add("gmtCreate=" + gmtCreate)
                .add("gmtModify=" + gmtModify)
                .add("id=" + id)
                .toString();
    }
}