package top.finder.aether.base.core.transform;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.finder.aether.base.core.entity.SysResource;
import top.finder.aether.common.support.helper.Builder;
import top.finder.aether.common.utils.LoggerUtil;
import top.finder.aether.data.core.entity.ResourceMapping;

/**
 * <p>系统资源转换类</p>
 *
 * @author guocq
 * @see SysResource
 * @see ResourceMapping
 * @since 2023/1/12
 */
public class SysResourceTransform {
    private static final Logger log = LoggerFactory.getLogger(SysResourceTransform.class);

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
        return Builder.builder(SysResource::new)
                .enhanceWith(SysResource::setResourceCode, resourceMapping::getResourceCode)
                .enhanceWith(SysResource::setResourceName, resourceMapping::getResourceName)
                .enhanceWith(SysResource::setResourceTypeCode, () -> resourceMapping.getResourceType().getCode())
                .enhanceWith(SysResource::setResourceTypeName, () -> resourceMapping.getResourceType().getName())
                .enhanceWith(SysResource::setResourceDesc, resourceMapping::getResourceDesc)
                .enhanceWith(SysResource::setResourceUrl, resourceMapping::getResourceUrl)
                .build();
    }
}
