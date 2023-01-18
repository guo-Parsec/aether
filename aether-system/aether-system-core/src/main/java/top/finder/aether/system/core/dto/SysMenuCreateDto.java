package top.finder.aether.system.core.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import top.finder.aether.common.model.IModel;

import javax.validation.constraints.NotEmpty;
import java.util.StringJoiner;

/**
 * <p>系统菜单数据创建入参</p>
 *
 * @author guocq
 * @since 2023/01/18
 */
@Getter
@Setter
@ApiModel("系统菜单数据创建入参")
public class SysMenuCreateDto implements IModel {
    private static final long serialVersionUID = 207630848706745258L;

    /**
     * 上级菜单id false false
     */
    @ApiModelProperty(value = "上级菜单id")
    private Long parentId;

    /**
     * 层级路径 false false
     */
    @ApiModelProperty(value = "层级路径")
    private String absolutePath;

    /**
     * 菜单码值 false true
     */
    @ApiModelProperty(value = "菜单码值")
    @NotEmpty(message = "菜单码值不能为空")
    private String menuCode;

    /**
     * 菜单标题 false false
     */
    @ApiModelProperty(value = "菜单标题")
    private String menuTitle;

    /**
     * 菜单图标 false false
     */
    @ApiModelProperty(value = "菜单图标")
    private String menuIcon;

    /**
     * 菜单级别 false false
     */
    @ApiModelProperty(value = "菜单级别")
    private Integer menuLevel;

    /**
     * 菜单排序 false false
     */
    @ApiModelProperty(value = "菜单排序")
    private Integer menuSort;

    /**
     * 菜单路径 false false
     */
    @ApiModelProperty(value = "菜单路径")
    private String menuUrl;

    /**
     * 前端组件 false false
     */
    @ApiModelProperty(value = "前端组件")
    private String menuComponent;

    /**
     * 显示状态 false false
     */
    @ApiModelProperty(value = "显示状态")
    private Integer menuDisplay;

    /**
     * 菜单描述 false false
     */
    @ApiModelProperty(value = "菜单描述")
    private String menuDesc;

    @Override
    public String toString() {
        return new StringJoiner(", ", SysMenuCreateDto.class.getSimpleName() + "[", "]")
                .add("parentId='" + parentId + "'")
                .add("absolutePath='" + absolutePath + "'")
                .add("menuCode='" + menuCode + "'")
                .add("menuTitle='" + menuTitle + "'")
                .add("menuIcon='" + menuIcon + "'")
                .add("menuLevel='" + menuLevel + "'")
                .add("menuSort='" + menuSort + "'")
                .add("menuUrl='" + menuUrl + "'")
                .add("menuComponent='" + menuComponent + "'")
                .add("menuDisplay='" + menuDisplay + "'")
                .add("menuDesc='" + menuDesc + "'")
                .toString();
    }
}
