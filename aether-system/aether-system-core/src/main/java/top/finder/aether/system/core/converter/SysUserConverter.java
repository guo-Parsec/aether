package top.finder.aether.system.core.converter;

import top.finder.aether.common.support.helper.Converter;
import top.finder.aether.data.core.entity.UserDetails;
import top.finder.aether.system.api.dto.SysUserCreateDto;
import top.finder.aether.system.api.vo.SysUserVo;
import top.finder.aether.system.core.dto.SysUserUpdateDto;
import top.finder.aether.system.core.entity.SysUser;

/**
 * <p>系统用户转换类</p>
 *
 * @author guocq
 * @since 2023/1/16
 */
public class SysUserConverter {
    /**
     * <p>{@link SysUserCreateDto}转换为{@link SysUser}转换器</p>
     */
    public static Converter<SysUserCreateDto, SysUser> createDtoToEntityConverter = source -> {
        if (source == null) {
            return null;
        }
        SysUser sysUser = new SysUser();
        sysUser.setAccount(source.getAccount());
        sysUser.setPassword(source.getPassword());
        sysUser.setNickname(source.getNickname());
        sysUser.setSex(source.getSex());
        sysUser.setAvatarUrl(source.getAvatarUrl());
        sysUser.setBirthday(source.getBirthday());
        sysUser.setUserType(source.getUserType());
        return sysUser;
    };

    /**
     * <p>{@link SysUserUpdateDto}转换为{@link SysUser}转换器</p>
     */
    public static Converter<SysUserUpdateDto, SysUser> updateDtoToEntityConverter = source -> {
        if (source == null) {
            return null;
        }
        SysUser sysUser = new SysUser();
        sysUser.setId(source.getId());
        sysUser.setAccount(source.getAccount());
        sysUser.setPassword(source.getPassword());
        sysUser.setNickname(source.getNickname());
        sysUser.setSex(source.getSex());
        sysUser.setAvatarUrl(source.getAvatarUrl());
        sysUser.setBirthday(source.getBirthday());
        sysUser.setUserType(source.getUserType());
        return sysUser;
    };

    /**
     * <p>{@link SysUser}转换为{@link SysUserVo}转换器</p>
     */
    public static Converter<SysUser, SysUserVo> entityToVoConverter = source -> {
        if (source == null) {
            return null;
        }
        SysUserVo sysUserVo = new SysUserVo();
        sysUserVo.setAccount(source.getAccount());
        sysUserVo.setPassword(source.getPassword());
        sysUserVo.setNickname(source.getNickname());
        sysUserVo.setSex(source.getSex());
        sysUserVo.setAvatarUrl(source.getAvatarUrl());
        sysUserVo.setBirthday(source.getBirthday());
        sysUserVo.setUserType(source.getUserType());
        sysUserVo.setEnableStatus(source.getEnableStatus());
        sysUserVo.setGmtCreate(source.getGmtCreate());
        sysUserVo.setGmtModify(source.getGmtModify());
        sysUserVo.setId(source.getId());
        return sysUserVo;
    };

    /**
     * <p>{@link SysUser}转换为{@link UserDetails}的转换器</p>
     */
    public static Converter<SysUser, UserDetails> sysUserToUserDetailsConverter = source -> {
        if (source == null) {
            return null;
        }
        UserDetails userDetails = new UserDetails();
        userDetails.setAccount(source.getAccount());
        userDetails.setPassword(source.getPassword());
        userDetails.setNickname(source.getNickname());
        userDetails.setSex(source.getSex());
        userDetails.setAvatarUrl(source.getAvatarUrl());
        userDetails.setBirthday(source.getBirthday());
        userDetails.setUserType(source.getUserType());
        userDetails.setEnableStatus(source.getEnableStatus());
        userDetails.setId(source.getId());
        return userDetails;
    };
    
    /**
     * <p>{@link SysUserCreateDto}转换为{@link SysUser}</p>
     *
     * @param dto {@link SysUserCreateDto} 入参
     * @return {@link SysUser}
     * @author guocq
     * @date 2023/1/16 13:48
     */
    public static SysUser createDtoToEntity(SysUserCreateDto dto) {
        return createDtoToEntityConverter.convert(dto);
    }

    /**
     * <p>{@link SysUserUpdateDto}转换为{@link SysUser}</p>
     *
     * @param dto {@link SysUserUpdateDto} 入参
     * @return {@link SysUser}
     * @author guocq
     * @date 2023/1/16 13:48
     */
    public static SysUser updateDtoToEntity(SysUserUpdateDto dto) {
        return updateDtoToEntityConverter.convert(dto);
    }

    /**
     * <p>{@link SysUser}转换为{@link SysUserVo}</p>
     *
     * @param entity {@link SysUser} 入参
     * @return {@link SysUserVo}
     * @author guocq
     * @date 2023/1/16 13:48
     */
    public static SysUserVo entityToVo(SysUser entity) {
        return entityToVoConverter.convert(entity);
    }
}
