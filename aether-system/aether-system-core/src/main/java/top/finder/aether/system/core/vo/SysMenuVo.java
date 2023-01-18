package top.finder.aether.system.core.vo;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import top.finder.aether.data.common.vo.BaseDataVo;

import java.util.StringJoiner;

/**
 * <p>系统菜单数据对外展示层</p>
 *
 * @author guocq
 * @since 2023/01/18
 */
@Getter
@Setter
@ApiModel("系统菜单数据对外展示层")
public class SysMenuVo extends BaseDataVo {
    private static final long serialVersionUID = 800620701351653272L;

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
        return new StringJoiner(", ", SysMenuVo.class.getSimpleName() + "[", "]")
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
                .add("gmtCreate='" + gmtCreate + "'")
                .add("gmtModify='" + gmtModify + "'")
                .toString();
    }
}
