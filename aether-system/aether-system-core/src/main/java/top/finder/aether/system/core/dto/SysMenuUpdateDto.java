package top.finder.aether.system.core.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import top.finder.aether.common.model.IModel;

import javax.validation.constraints.NotNull;
import java.util.StringJoiner;

/**
 * <p>系统菜单数据修改入参</p>
 *
 * @author guocq
 * @since 2023/01/18
 */
@Getter
@Setter
@ApiModel("系统菜单数据修改入参")
public class SysMenuUpdateDto implements IModel {
    private static final long serialVersionUID = 901883282342149836L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @NotNull(message = "主键不能为空")
    private Long id;

    /**
     * 上级菜单id
     */
    @ApiModelProperty(value = "上级菜单id")
    private Long parentId;

    /**
     * 层级路径
     */
    @ApiModelProperty(value = "层级路径")
    private String absolutePath;

    /**
     * 菜单码值
     */
    @ApiModelProperty(value = "菜单码值")
    private String menuCode;

    /**
     * 菜单标题
     */
    @ApiModelProperty(value = "菜单标题")
    private String menuTitle;

    /**
     * 菜单图标
     */
    @ApiModelProperty(value = "菜单图标")
    private String menuIcon;

    /**
     * 菜单级别
     */
    @ApiModelProperty(value = "菜单级别")
    private Integer menuLevel;

    /**
     * 菜单排序
     */
    @ApiModelProperty(value = "菜单排序")
    private Integer menuSort;

    /**
     * 菜单路径
     */
    @ApiModelProperty(value = "菜单路径")
    private String menuUrl;

    /**
     * 前端组件
     */
    @ApiModelProperty(value = "前端组件")
    private String menuComponent;

    /**
     * 显示状态
     */
    @ApiModelProperty(value = "显示状态")
    private Integer menuDisplay;

    /**
     * 菜单描述
     */
    @ApiModelProperty(value = "菜单描述")
    private String menuDesc;

    @Override
    public String toString() {
        return new StringJoiner(", ", SysMenuUpdateDto.class.getSimpleName() + "[", "]")
                .add("id='" + id + "'")
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
