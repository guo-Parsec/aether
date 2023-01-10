package top.finder.aether.base.core.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import top.finder.aether.data.common.entity.BaseDataEntity;

import java.util.List;
import java.util.Set;
import java.util.StringJoiner;

/**
 * <p>系统资源表</p>
 *
 * @author guocq
 * @since 2023/1/10
 */
@ApiModel(value = "系统资源表")
@Getter
@Setter
@TableName(value = "ams_resource")
public class Resource extends BaseDataEntity {
    private static final long serialVersionUID = 7796147232012279094L;
    /**
     * 资源类别码值
     */
    @ApiModelProperty(value = "资源类别码值")
    private String resourceTypeCode;

    /**
     * 资源类别名称
     */
    @ApiModelProperty(value = "资源类别名称")
    private String resourceTypeName;

    /**
     * 权限码值
     */
    @ApiModelProperty(value = "权限码值")
    private String resourceCode;

    /**
     * 权限名称
     */
    @ApiModelProperty(value = "权限名称")
    private String resourceName;

    /**
     * 资源路径
     */
    @ApiModelProperty(value = "资源路径")
    private String resourceUrl;

    /**
     * 资源排序
     */
    @ApiModelProperty(value = "资源排序")
    private Integer resourceSort;

    /**
     * 资源描述
     */
    @ApiModelProperty(value = "资源描述")
    private String resourceDesc;

    /**
     * <p>apiModel转换</p>
     *
     * @param apiModel apiModel
     * @return {@link List}
     * @author guocq
     * @date 2023/1/10 11:43
     */
    public static List<Resource> apiModelToResource(top.finder.aether.data.core.model.ApiModel apiModel) {
        Set<String> uri = apiModel.getUri();
        List<Resource> resources = Lists.newArrayListWithCapacity(uri.size());
        Resource resource = new Resource();
        resource.setResourceTypeCode(apiModel.getResourceType().getCode());
        resource.setResourceTypeName(apiModel.getResourceType().getName());
        resource.setResourceCode(apiModel.getResourceCode());
        resource.setResourceName(apiModel.getResourceName());
        resource.setResourceSort(apiModel.getSort());
        resource.setResourceDesc(apiModel.getDesc());
        for (String url : uri) {
            resource.setResourceUrl(url);
            resources.add(resource);
        }
        return resources;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Resource.class.getSimpleName() + "[", "]")
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