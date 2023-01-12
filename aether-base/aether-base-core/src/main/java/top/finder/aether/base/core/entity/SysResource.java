package top.finder.aether.base.core.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import top.finder.aether.data.common.entity.BaseDataEntity;

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