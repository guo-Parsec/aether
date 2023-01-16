package top.finder.aether.system.core.converter;

import top.finder.aether.common.support.helper.Converter;
import top.finder.aether.system.core.dto.SysDictCreateDto;
import top.finder.aether.system.core.dto.SysDictUpdateDto;
import top.finder.aether.system.core.entity.SysDict;
import top.finder.aether.system.core.vo.SysDictVo;

/**
 * <p>数据字典转换类</p>
 *
 * @author guocq
 * @since 2023/1/16
 */
public class SysDictConverter {
    /**
     * <p>{@link SysDictCreateDto}转换为{@link SysDict}的转换器</p>
     */
    public static Converter<SysDictCreateDto, SysDict> createDtoToEntityConverter = source -> {
        if (source == null) {
            return null;
        }
        SysDict sysDict = new SysDict();
        sysDict.setDictTypeCode(source.getDictTypeCode());
        sysDict.setDictTypeName(source.getDictTypeName());
        sysDict.setDictName(source.getDictName());
        sysDict.setDictCode(source.getDictCode());
        sysDict.setDictSort(source.getDictSort());
        return sysDict;
    };

    /**
     * <p>{@link SysDictUpdateDto}转换为{@link SysDict}的转换器</p>
     */
    public static Converter<SysDictUpdateDto, SysDict> updateDtoToEntityConverter = source -> {
        if (source == null) {
            return null;
        }
        SysDict sysDict = new SysDict();
        sysDict.setId(source.getId());
        sysDict.setDictTypeCode(source.getDictTypeCode());
        sysDict.setDictTypeName(source.getDictTypeName());
        sysDict.setDictName(source.getDictName());
        sysDict.setDictCode(source.getDictCode());
        sysDict.setDictSort(source.getDictSort());
        return sysDict;
    };

    /**
     * <p>{@link SysDict}转换为{@link SysDictVo}的转换器</p>
     */
    public static Converter<SysDict, SysDictVo> entityToVoConverter = source -> {
        if (source == null) {
            return null;
        }
        SysDictVo sysDictVo = new SysDictVo();
        sysDictVo.setDictTypeCode(source.getDictTypeCode());
        sysDictVo.setDictTypeName(source.getDictTypeName());
        sysDictVo.setDictName(source.getDictName());
        sysDictVo.setDictCode(source.getDictCode());
        sysDictVo.setDictSort(source.getDictSort());
        sysDictVo.setGmtCreate(source.getGmtCreate());
        sysDictVo.setGmtModify(source.getGmtModify());
        sysDictVo.setId(source.getId());
        return sysDictVo;
    };

    /**
     * <p>{@link SysDictCreateDto}转换为{@link SysDict}</p>
     *
     * @param dto {@link SysDictCreateDto} 入参
     * @return {@link SysDict}
     * @author guocq
     * @date 2023/1/16 10:25
     */
    public static SysDict createDtoToEntity(SysDictCreateDto dto) {
        return createDtoToEntityConverter.convert(dto);
    }

    /**
     * <p>{@link SysDictUpdateDto}转换为{@link SysDict}</p>
     *
     * @param dto {@link SysDictUpdateDto} 入参
     * @return {@link SysDict}
     * @author guocq
     * @date 2023/1/16 10:25
     */
    public static SysDict updateDtoToEntity(SysDictUpdateDto dto) {
        return updateDtoToEntityConverter.convert(dto);
    }    

    /**
     * <p>{@link SysDict}转换为{@link SysDictVo}</p>
     *
     * @param entity {@link SysDict} 入参
     * @return {@link SysDict}
     * @author guocq
     * @date 2023/1/16 10:25
     */
    public static SysDictVo entityToVo(SysDict entity) {
        return entityToVoConverter.convert(entity);
    }
}
