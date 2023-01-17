package top.finder.aether.system.core.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import top.finder.aether.data.common.entity.BaseDataEntity;

import java.util.StringJoiner;

/**
 * <p>系统菜单表</p>
 *
 * @author guocq
 * @since 2023/1/16
 */
@Getter
@Setter
@TableName(value = "sys_menu")
public class SysMenu extends BaseDataEntity {
    private static final long serialVersionUID = -1562680095426199971L;
    /**
     * 上级菜单id
     */
    private Long parentId;

    /**
     * 层级路径
     */
    private String absolutePath;

    /**
     * 菜单码值
     */
    private String menuCode;

    /**
     * 菜单标题
     */
    private String menuTitle;

    /**
     * 菜单图标
     */
    private String menuIcon;

    /**
     * 菜单级别
     */
    private Integer menuLevel;

    /**
     * 菜单排序
     */
    private Integer menuSort;

    /**
     * 菜单路径
     */
    private String menuUrl;

    /**
     * 前端组件
     */
    private String menuComponent;

    /**
     * 显示状态
     */
    private Integer menuDisplay;

    /**
     * 菜单描述
     */
    private String menuDesc;

    @Override
    public String toString() {
        return new StringJoiner(", ", SysMenu.class.getSimpleName() + "[", "]")
                .add("parentId=" + parentId)
                .add("absolutePath='" + absolutePath + "'")
                .add("menuCode='" + menuCode + "'")
                .add("menuTitle='" + menuTitle + "'")
                .add("menuIcon='" + menuIcon + "'")
                .add("menuLevel=" + menuLevel)
                .add("menuSort=" + menuSort)
                .add("menuUrl='" + menuUrl + "'")
                .add("menuComponent='" + menuComponent + "'")
                .add("menuDisplay=" + menuDisplay)
                .add("menuDesc='" + menuDesc + "'")
                .add("deleteAt=" + deleteAt)
                .add("gmtCreate=" + gmtCreate)
                .add("gmtModify=" + gmtModify)
                .add("id=" + id)
                .toString();
    }
}