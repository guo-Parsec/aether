package top.finder.aether.system.core.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import top.finder.aether.common.model.IModel;
import top.finder.aether.common.support.annotation.DictValid;
import top.finder.aether.common.support.pool.RegexConstantPool;

import javax.validation.constraints.*;
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
    @Pattern(regexp = RegexConstantPool.STR_WORDS, message = "菜单码值" + RegexConstantPool.MSG_WORDS)
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
    @Min(value = 1, message = "菜单级别不能小于1")
    @ApiModelProperty(value = "菜单级别")
    private Integer menuLevel;

    /**
     * 菜单排序
     */
    @Min(value = 0, message = "菜单排序不能小于0")
    @Max(value = 99999, message = "菜单排序不能大于99999")
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
    @DictValid(type = "menu_display", emptyValid = false)
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
