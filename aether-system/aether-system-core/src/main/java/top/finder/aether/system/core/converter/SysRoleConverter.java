package top.finder.aether.system.core.converter;

import top.finder.aether.common.support.helper.Converter;
import top.finder.aether.system.core.dto.SysRoleCreateDto;
import top.finder.aether.system.core.dto.SysRoleUpdateDto;
import top.finder.aether.system.core.entity.SysRole;
import top.finder.aether.system.core.vo.SysRoleVo;

/**
 * <p>系统角色转换类</p>
 *
 * @author guocq
 * @since 2023/1/16
 */
public class SysRoleConverter {
    /**
     * <p>{@link SysRoleCreateDto}转换为{@link SysRole}转换器</p>
     */
    public static Converter<SysRoleCreateDto, SysRole> createDtoToEntityConverter = source -> {
        if (source == null) {
            return null;
        }
        SysRole sysRole = new SysRole();
        sysRole.setRoleCode(source.getRoleCode());
        sysRole.setRoleName(source.getRoleName());
        sysRole.setRoleSort(source.getRoleSort());
        sysRole.setRoleDesc(source.getRoleDesc());
        return sysRole;
    };

    /**
     * <p>{@link SysRoleUpdateDto}转换为{@link SysRole}转换器</p>
     */
    public static Converter<SysRoleUpdateDto, SysRole> updateDtoToEntityConverter = source -> {
        if (source == null) {
            return null;
        }
        SysRole sysRole = new SysRole();
        sysRole.setId(source.getId());
        sysRole.setRoleCode(source.getRoleCode());
        sysRole.setRoleName(source.getRoleName());
        sysRole.setRoleSort(source.getRoleSort());
        sysRole.setRoleDesc(source.getRoleDesc());
        return sysRole;
    };

    /**
     * <p>{@link SysRole}转换为{@link SysRoleVo}转换器</p>
     */
    public static Converter<SysRole, SysRoleVo> entityToVoConverter = source -> {
        if (source == null) {
            return null;
        }
        SysRoleVo sysRoleVo = new SysRoleVo();
        sysRoleVo.setRoleCode(source.getRoleCode());
        sysRoleVo.setRoleName(source.getRoleName());
        sysRoleVo.setRoleSort(source.getRoleSort());
        sysRoleVo.setRoleDesc(source.getRoleDesc());
        sysRoleVo.setGmtCreate(source.getGmtCreate());
        sysRoleVo.setGmtModify(source.getGmtModify());
        sysRoleVo.setId(source.getId());
        return sysRoleVo;
    };

    /**
     * <p>{@link SysRoleCreateDto}转换为{@link SysRole}</p>
     *
     * @param dto {@link SysRoleCreateDto} 入参
     * @return {@link SysRole}
     * @author guocq
     * @date 2023/1/16 13:44
     */
    public static SysRole createDtoToEntity(SysRoleCreateDto dto) {
        return createDtoToEntityConverter.convert(dto);
    }

    /**
     * <p>{@link SysRoleUpdateDto}转换为{@link SysRole}</p>
     *
     * @param dto {@link SysRoleUpdateDto} 入参
     * @return {@link SysRole}
     * @author guocq
     * @date 2023/1/16 13:44
     */
    public static SysRole updateDtoToEntity(SysRoleUpdateDto dto) {
        return updateDtoToEntityConverter.convert(dto);
    }

    /**
     * <p>{@link SysRole}转换为{@link SysRoleVo}</p>
     *
     * @param entity {@link SysRole} 入参
     * @return {@link SysRoleVo}
     * @author guocq
     * @date 2023/1/16 13:44
     */
    public static SysRoleVo entityToVo(SysRole entity) {
        return entityToVoConverter.convert(entity);
    }
}
