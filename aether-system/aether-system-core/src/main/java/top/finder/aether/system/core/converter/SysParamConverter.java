package top.finder.aether.system.core.converter;

import top.finder.aether.common.support.helper.Converter;
import top.finder.aether.system.core.dto.SysParamCreateDto;
import top.finder.aether.system.core.dto.SysParamUpdateDto;
import top.finder.aether.system.core.entity.SysParam;
import top.finder.aether.system.core.vo.SysParamVo;

/**
 * <p>系统参数转换类</p>
 *
 * @author guocq
 * @since 2023/1/16
 */
public class SysParamConverter {
    /**
     * {@link SysParamCreateDto}转换为{@link SysParam}转换器
     */
    public static Converter<SysParamCreateDto, SysParam> createDtoToEntityConverter = source -> {
        if (source == null) {
            return null;
        }
        SysParam sysParam = new SysParam();
        sysParam.setParamTypeCode(source.getParamTypeCode());
        sysParam.setParamTypeName(source.getParamTypeName());
        sysParam.setParamName(source.getParamName());
        sysParam.setParamCode(source.getParamCode());
        sysParam.setParamValue(source.getParamValue());
        sysParam.setParamSort(source.getParamSort());
        return sysParam;
    };

    /**
     * {@link SysParamUpdateDto}转换为{@link SysParam}转换器
     */
    public static Converter<SysParamUpdateDto, SysParam> updateDtoToEntityConverter = source -> {
        if (source == null) {
            return null;
        }
        SysParam sysParam = new SysParam();
        sysParam.setId(source.getId());
        sysParam.setParamTypeCode(source.getParamTypeCode());
        sysParam.setParamTypeName(source.getParamTypeName());
        sysParam.setParamName(source.getParamName());
        sysParam.setParamCode(source.getParamCode());
        sysParam.setParamValue(source.getParamValue());
        sysParam.setParamSort(source.getParamSort());
        return sysParam;
    };

    /**
     * {@link SysParam}转换为{@link SysParamVo}转换器
     */
    public static Converter<SysParam, SysParamVo> entityToVoConverter = source -> {
        if (source == null) {
            return null;
        }
        SysParamVo sysParamVo = new SysParamVo();
        sysParamVo.setParamTypeCode(source.getParamTypeCode());
        sysParamVo.setParamTypeName(source.getParamTypeName());
        sysParamVo.setParamName(source.getParamName());
        sysParamVo.setParamCode(source.getParamCode());
        sysParamVo.setParamValue(source.getParamValue());
        sysParamVo.setParamSort(source.getParamSort());
        sysParamVo.setGmtCreate(source.getGmtCreate());
        sysParamVo.setGmtModify(source.getGmtModify());
        sysParamVo.setId(source.getId());
        return sysParamVo;
    };

    /**
     * <p>{@link SysParamCreateDto}转换为{@link SysParam}</p>
     *
     * @param dto {@link SysParamCreateDto} 入参
     * @return {@link SysParam}
     * @author guocq
     * @date 2023/1/16 10:25
     */
    public static SysParam createDtoToEntity(SysParamCreateDto dto) {
        return createDtoToEntityConverter.convert(dto);
    }

    /**
     * <p>{@link SysParamUpdateDto}转换为{@link SysParam}</p>
     *
     * @param dto {@link SysParamUpdateDto} 入参
     * @return {@link SysParam}
     * @author guocq
     * @date 2023/1/16 11:48
     */
    public static SysParam updateDtoToEntity(SysParamUpdateDto dto) {
        return updateDtoToEntityConverter.convert(dto);
    }

    /**
     * <p>{@link SysParam}转换为{@link SysParamVo}</p>
     *
     * @param entity {@link SysParam} 入参
     * @return {@link SysParamVo}
     * @author guocq
     * @date 2023/1/16 11:48
     */
    public static SysParamVo entityToVo(SysParam entity) {
        return entityToVoConverter.convert(entity);
    }
}
