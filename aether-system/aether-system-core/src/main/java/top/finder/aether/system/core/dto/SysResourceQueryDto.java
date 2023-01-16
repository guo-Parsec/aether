package top.finder.aether.system.core.dto;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import top.finder.aether.common.model.IModel;
import top.finder.aether.system.core.entity.SysResource;

import java.util.StringJoiner;

/**
 * <p>系统资源查询参数</p>
 *
 * @author guocq
 * @since 2023/1/16
 */
@Setter
@Getter
@ApiModel("系统资源查询参数")
public class SysResourceQueryDto implements IModel {
    private static final long serialVersionUID = -4555993531920154670L;

    /**
     * 主键
     */
    @ApiModelProperty("主键")
    protected Long id;

    /**
     * 资源类别码集合
     */
    @ApiModelProperty(value = "资源类别码")
    protected String resourceTypeCode;

    /**
     * 资源类别名称
     */
    @ApiModelProperty(value = "资源类别名称")
    protected String resourceTypeName;

    /**
     * 权限码值
     */
    @ApiModelProperty(value = "权限码值")
    protected String resourceCode;

    /**
     * 权限名称
     */
    @ApiModelProperty(value = "权限名称")
    protected String resourceName;

    /**
     * 资源路径
     */
    @ApiModelProperty(value = "资源路径")
    protected String resourceUrl;

    /**
     * <p>获取查询通用wrapper</p>
     *
     * @return {@link LambdaQueryWrapper}
     * @author guocq
     * @date 2023/1/13 15:30
     */
    public LambdaQueryWrapper<SysResource> getCommonWrapper() {
        LambdaQueryWrapper<SysResource> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ObjectUtil.isNotNull(this.id), SysResource::getId, this.id)
                .eq(StrUtil.isNotBlank(this.resourceTypeCode), SysResource::getResourceTypeCode, this.resourceTypeCode)
                .like(StrUtil.isNotBlank(this.resourceTypeName), SysResource::getResourceTypeName, this.resourceTypeName)
                .eq(StrUtil.isNotBlank(this.resourceCode), SysResource::getResourceCode, this.resourceCode)
                .like(StrUtil.isNotBlank(this.resourceName), SysResource::getResourceName, this.resourceName)
                .like(ObjectUtil.isNotNull(this.resourceUrl), SysResource::getResourceUrl, this.resourceUrl)
                .orderByAsc(SysResource::getResourceTypeCode)
                .orderByDesc(SysResource::getGmtModify)
                .orderByDesc(SysResource::getGmtCreate);
        return wrapper;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", SysResourceQueryDto.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("resourceTypeCode='" + resourceTypeCode + "'")
                .add("resourceTypeName='" + resourceTypeName + "'")
                .add("resourceCode='" + resourceCode + "'")
                .add("resourceName='" + resourceName + "'")
                .add("resourceUrl='" + resourceUrl + "'")
                .toString();
    }
}
