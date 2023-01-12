package top.finder.aether.data.core.facade;

import top.finder.aether.data.core.entity.ResourceMapping;

import java.util.List;

/**
 * <p>资源映射Facade</p>
 *
 * @author guocq
 * @since 2023/1/12
 */
public interface ResourceMappingFacade {
    /**
     * <p>获取资源映射列表</p>
     *
     * @return {@link List}
     * @author guocq
     * @date 2023/1/12 10:53
     */
    List<ResourceMapping> getResourceMappingList();
}
