package top.finder.aether.system.core.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.finder.aether.common.support.helper.Converter;
import top.finder.aether.common.utils.LoggerUtil;
import top.finder.aether.data.core.entity.ResourceMapping;
import top.finder.aether.system.core.dto.SysResourceCreateDto;
import top.finder.aether.system.core.dto.SysResourceUpdateDto;
import top.finder.aether.system.core.entity.SysResource;
import top.finder.aether.system.api.vo.SysResourceVo;

/**
 * <p>系统资源转换类</p>
 *
 * @author guocq
 * @see SysResource
 * @see ResourceMapping
 * @since 2023/1/12
 */
public class SysResourceConverter {
    private static final Logger log = LoggerFactory.getLogger(SysResourceConverter.class);

    /**
     * 将类型为{@link ResourceMapping}的对象转化为{@link SysResource}类型对象的转换器
     */
    public static Converter<ResourceMapping, SysResource> resourceMappingToSysResourceConverter = source -> {
        if (source == null) {
            return null;
        }
        SysResource sysResource = new SysResource();
        sysResource.setResourceCode(source.getResourceCode());
        sysResource.setResourceName(source.getResourceName());
        sysResource.setResourceTypeCode(source.getResourceType().getCode());
        sysResource.setResourceTypeName(source.getResourceType().getName());
        sysResource.setResourceDesc(source.getResourceDesc());
        sysResource.setResourceUrl(source.getResourceUrl());
        return sysResource;
    };

    /**
     * 将类型为{@link SysResourceCreateDto}的对象转化为{@link SysResource}类型对象的转换器
     */
    public static Converter<SysResourceCreateDto, SysResource> createDtoToEntityConverter = source -> {
        if (source == null) {
            return null;
        }
        SysResource sysResource = new SysResource();
        sysResource.setResourceType(source.getResourceType());
        sysResource.setResourceCode(source.getResourceCode());
        sysResource.setResourceName(source.getResourceName());
        sysResource.setResourceUrl(source.getResourceUrl());
        sysResource.setResourceDesc(source.getResourceDesc());
        return sysResource;
    };

    /**
     * 将类型为{@link SysResourceUpdateDto}的对象转化为{@link SysResource}类型对象的转换器
     */
    public static Converter<SysResourceUpdateDto, SysResource> updateDtoToEntityConverter = source -> {
        if (source == null) {
            return null;
        }
        SysResource sysResource = new SysResource();
        sysResource.setId(source.getId());
        sysResource.setResourceType(source.getResourceType());
        sysResource.setResourceCode(source.getResourceCode());
        sysResource.setResourceName(source.getResourceName());
        sysResource.setResourceUrl(source.getResourceUrl());
        sysResource.setResourceDesc(source.getResourceDesc());
        return sysResource;
    };

    /**
     * 将类型为{@link SysResource}的对象转化为{@link SysResourceVo}类型对象的转换器
     */
    public static Converter<SysResource, SysResourceVo> entityToVoConverter = source -> {
        if (source == null) {
            return null;
        }
        SysResourceVo sysResourceVo = new SysResourceVo();
        sysResourceVo.setResourceTypeCode(source.getResourceTypeCode());
        sysResourceVo.setResourceTypeName(source.getResourceTypeName());
        sysResourceVo.setResourceCode(source.getResourceCode());
        sysResourceVo.setResourceName(source.getResourceName());
        sysResourceVo.setResourceUrl(source.getResourceUrl());
        sysResourceVo.setResourceDesc(source.getResourceDesc());
        sysResourceVo.setGmtCreate(source.getGmtCreate());
        sysResourceVo.setGmtModify(source.getGmtModify());
        sysResourceVo.setId(source.getId());
        return sysResourceVo;
    };

    /**
     * <p>将类型为{@link ResourceMapping}的对象转化为{@link SysResource}类型对象</p>
     *
     * @param resourceMapping 资源映射
     * @return {@link SysResource}
     * @author guocq
     * @date 2023/1/12 13:41
     */
    public static SysResource resourceMappingToSysResource(ResourceMapping resourceMapping) {
        if (resourceMapping == null) {
            throw LoggerUtil.logAetherError(log, "[ResourceMapping]转换为[SysResource]时，resourceMapping不能为空");
        }
        return resourceMappingToSysResourceConverter.convert(resourceMapping);
    }

    /**
     * <p>将类型为{@link SysResourceCreateDto}的对象转化为{@link SysResource}</p>
     *
     * @param dto {@link SysResourceCreateDto}
     * @return {@link SysResource}
     * @author guocq
     * @date 2023/1/16 15:17
     */
    public static SysResource createDtoToEntity(SysResourceCreateDto dto) {
        return createDtoToEntityConverter.convert(dto);
    }

    /**
     * <p>将类型为{@link SysResourceUpdateDto}的对象转化为{@link SysResource}</p>
     *
     * @param dto {@link SysResourceUpdateDto}
     * @return {@link SysResource}
     * @author guocq
     * @date 2023/1/16 15:17
     */
    public static SysResource updateDtoToEntity(SysResourceUpdateDto dto) {
        return updateDtoToEntityConverter.convert(dto);
    }

    /**
     * <p>将类型为{@link SysResource}的对象转化为{@link SysResourceVo}</p>
     *
     * @param entity {@link SysResource}
     * @return {@link SysResourceVo}
     * @author guocq
     * @date 2023/1/16 15:17
     */
    public static SysResourceVo entityToVo(SysResource entity) {
        return entityToVoConverter.convert(entity);
    }
}
