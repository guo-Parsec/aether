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

    /**
     * <p>对当前对象处理层级路径</p>
     *
     * @param isAppendSeparatorToTail 是否追加到末尾
     * @return java.lang.String
     * @author guocq
     * @date 2023/1/19 10:04
     */
    public String handleAbsolutePath(boolean isAppendSeparatorToTail) {
        return handleAbsolutePath(this.absolutePath, this.id, isAppendSeparatorToTail);
    }

    /**
     * <p>对当前对象处理层级路径[不追加路径分隔符到末尾]</p>
     *
     * @return java.lang.String
     * @author guocq
     * @date 2023/1/19 10:04
     */
    public String handleAbsolutePath() {
        return handleAbsolutePath(this.absolutePath, this.id);
    }

    /**
     * <p>路径层级处理[不追加路径分隔符到末尾]</p>
     *
     * @param rawAbsolutePath 原始路径层级
     * @param appendId        追加的id
     * @return java.lang.String
     * @author guocq
     * @date 2023/1/19 10:02
     */
    public static String handleAbsolutePath(String rawAbsolutePath, Long appendId) {
        return handleAbsolutePath(rawAbsolutePath, appendId, false);
    }

    /**
     * <p>路径层级处理</p>
     *
     * @param rawAbsolutePath         原始路径层级
     * @param appendId                追加的id
     * @param isAppendSeparatorToTail 是否追加分隔符到末尾
     * @return java.lang.String
     * @author guocq
     * @date 2023/1/19 10:01
     */
    public static String handleAbsolutePath(String rawAbsolutePath, Long appendId, boolean isAppendSeparatorToTail) {
        final String pathSeparator = "/";
        String temp = rawAbsolutePath.endsWith(pathSeparator) ? rawAbsolutePath : rawAbsolutePath + pathSeparator;
        return isAppendSeparatorToTail ? temp + appendId + pathSeparator : temp + appendId;
    }
}