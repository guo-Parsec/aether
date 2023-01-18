package top.finder.aether.system.core.converter;

import top.finder.aether.common.support.helper.Converter;
import top.finder.aether.system.core.entity.SysMenu;
import top.finder.aether.system.core.dto.SysMenuCreateDto;
import top.finder.aether.system.core.dto.SysMenuUpdateDto;
import top.finder.aether.system.core.vo.SysMenuVo;

/**
 * <p>系统菜单数据转换类</p>
 *
 * @author guocq
 * @since 2023/01/18
 */
public class SysMenuConverter {
    /**
     * <p>{@link SysMenuCreateDto}转换为{@link SysMenu}转换器</p>
     */
    public static Converter<SysMenuCreateDto, SysMenu> createDtoToEntityConverter = source -> {
        if (source == null) {
            return null;
        }
        SysMenu sysMenu = new SysMenu();
        sysMenu.setParentId(source.getParentId());
        sysMenu.setAbsolutePath(source.getAbsolutePath());
        sysMenu.setMenuCode(source.getMenuCode());
        sysMenu.setMenuTitle(source.getMenuTitle());
        sysMenu.setMenuIcon(source.getMenuIcon());
        sysMenu.setMenuLevel(source.getMenuLevel());
        sysMenu.setMenuSort(source.getMenuSort());
        sysMenu.setMenuUrl(source.getMenuUrl());
        sysMenu.setMenuComponent(source.getMenuComponent());
        sysMenu.setMenuDisplay(source.getMenuDisplay());
        sysMenu.setMenuDesc(source.getMenuDesc());
        return sysMenu;
    };

    /**
     * <p>{@link SysMenuUpdateDto}转换为{@link SysMenu}转换器</p>
     */
    public static Converter<SysMenuUpdateDto, SysMenu> updateDtoToEntityConverter = source -> {
        if (source == null) {
            return null;
        }
        SysMenu sysMenu = new SysMenu();
        sysMenu.setId(source.getId());
        sysMenu.setParentId(source.getParentId());
        sysMenu.setAbsolutePath(source.getAbsolutePath());
        sysMenu.setMenuCode(source.getMenuCode());
        sysMenu.setMenuTitle(source.getMenuTitle());
        sysMenu.setMenuIcon(source.getMenuIcon());
        sysMenu.setMenuLevel(source.getMenuLevel());
        sysMenu.setMenuSort(source.getMenuSort());
        sysMenu.setMenuUrl(source.getMenuUrl());
        sysMenu.setMenuComponent(source.getMenuComponent());
        sysMenu.setMenuDisplay(source.getMenuDisplay());
        sysMenu.setMenuDesc(source.getMenuDesc());
        return sysMenu;
    };

    /**
     * <p>{@link SysMenu}转换为{@link SysMenuVo}转换器</p>
     */
    public static Converter<SysMenu, SysMenuVo> entityToVoConverter = source -> {
        if (source == null) {
            return null;
        }
        SysMenuVo sysMenuVo = new SysMenuVo();
        sysMenuVo.setId(source.getId());
        sysMenuVo.setParentId(source.getParentId());
        sysMenuVo.setAbsolutePath(source.getAbsolutePath());
        sysMenuVo.setMenuCode(source.getMenuCode());
        sysMenuVo.setMenuTitle(source.getMenuTitle());
        sysMenuVo.setMenuIcon(source.getMenuIcon());
        sysMenuVo.setMenuLevel(source.getMenuLevel());
        sysMenuVo.setMenuSort(source.getMenuSort());
        sysMenuVo.setMenuUrl(source.getMenuUrl());
        sysMenuVo.setMenuComponent(source.getMenuComponent());
        sysMenuVo.setMenuDisplay(source.getMenuDisplay());
        sysMenuVo.setMenuDesc(source.getMenuDesc());
        sysMenuVo.setGmtCreate(source.getGmtCreate());
        sysMenuVo.setGmtModify(source.getGmtModify());
        return sysMenuVo;
    };

    /**
     * <p>{@link SysMenuCreateDto}转换为{@link SysMenu}</p>
     *
     * @param dto {@link SysMenuCreateDto} 入参
     * @return {@link SysMenu}
     * @author guocq
     * @date 2023/01/18 15:52
     */
    public static SysMenu createDtoToEntity(SysMenuCreateDto dto) {
        return createDtoToEntityConverter.convert(dto);
    }

    /**
     * <p>{@link SysMenuUpdateDto}转换为{@link SysMenu}</p>
     *
     * @param dto {@link SysMenuUpdateDto} 入参
     * @return {@link SysMenu}
     * @author guocq
     * @date 2023/01/18 15:52
     */
    public static SysMenu updateDtoToEntity(SysMenuUpdateDto dto) {
        return updateDtoToEntityConverter.convert(dto);
    }

    /**
     * <p>{@link SysMenu}转换为{@link SysMenuVo}</p>
     *
     * @param entity {@link SysMenu} 入参
     * @return {@link SysMenuVo}
     * @author guocq
     * @date 2023/01/18 15:52
     */
    public static SysMenuVo entityToVo(SysMenu entity) {
        return entityToVoConverter.convert(entity);
    }
}
