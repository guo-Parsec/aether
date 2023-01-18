package top.finder.aether.system.core.dto;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import top.finder.aether.common.model.IModel;
import top.finder.aether.system.core.entity.SysMenu;

import java.util.Set;
import java.util.StringJoiner;

/**
 * <p>系统菜单数据查询入参</p>
 *
 * @author guocq
 * @since 2023/01/18
 */
@Getter
@Setter
@ApiModel("系统菜单数据查询入参")
public class SysMenuQueryDto implements IModel {
    private static final long serialVersionUID = 330580635829852387L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    protected Long id;

    /**
     * 上级菜单id
     */
    @ApiModelProperty(value = "上级菜单id")
    protected Long parentId;

    /**
     * 层级路径
     */
    @ApiModelProperty(value = "层级路径")
    protected String absolutePath;

    /**
     * 菜单码值
     */
    @ApiModelProperty(value = "菜单码值")
    protected String menuCode;

    /**
     * 菜单标题
     */
    @ApiModelProperty(value = "菜单标题")
    protected String menuTitle;

    /**
     * 菜单级别
     */
    @ApiModelProperty(value = "菜单级别")
    protected Set<Integer> menuLevelSet;

    /**
     * 显示状态
     */
    @ApiModelProperty(value = "显示状态")
    protected Set<Integer> menuDisplaySet;

    /**
     * 菜单描述
     */
    @ApiModelProperty(value = "菜单描述")
    protected String menuDesc;

    /**
     * <p>获取查询通用wrapper</p>
     *
     * @return {@link LambdaQueryWrapper}
     * @author guocq
     * @date 2023/01/18 15:46
     */
    public LambdaQueryWrapper<SysMenu> getCommonWrapper() {
        LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ObjectUtil.isNotNull(this.id), SysMenu::getId, this.id);
        wrapper.eq(ObjectUtil.isNotNull(this.parentId), SysMenu::getParentId, this.parentId);
        wrapper.eq(StrUtil.isNotBlank(this.menuCode), SysMenu::getMenuCode, this.menuCode);
        wrapper.in(CollUtil.isNotEmpty(this.menuLevelSet), SysMenu::getMenuLevel, this.menuLevelSet);
        wrapper.in(CollUtil.isNotEmpty(this.menuDisplaySet), SysMenu::getMenuDisplay, this.menuDisplaySet);
        return wrapper;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", SysMenuQueryDto.class.getSimpleName() + "[", "]")
                .add("id='" + id + "'")
                .add("parentId='" + parentId + "'")
                .add("absolutePath='" + absolutePath + "'")
                .add("menuCode='" + menuCode + "'")
                .add("menuTitle='" + menuTitle + "'")
                .add("menuLevelSet='" + menuLevelSet + "'")
                .add("menuDisplaySet='" + menuDisplaySet + "'")
                .add("menuDesc='" + menuDesc + "'")
                .toString();
    }
}
