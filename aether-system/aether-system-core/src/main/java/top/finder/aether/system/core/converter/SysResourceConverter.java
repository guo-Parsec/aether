package top.finder.aether.system.core.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.finder.aether.common.support.helper.Converter;
import top.finder.aether.common.utils.LoggerUtil;
import top.finder.aether.data.core.entity.ResourceMapping;
import top.finder.aether.system.core.entity.SysResource;

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
}
