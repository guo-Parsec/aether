package top.finder.aether.base.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.finder.aether.base.core.entity.Resource;

/**
 * <p>系统资源业务接口</p>
 *
 * @author guocq
 * @since 2023/1/10
 */
public interface ResourceService extends IService<Resource> {
    /**
     * <p>自动生成资源</p>
     *
     * @param appName 应用名称
     * @author guocq
     * @date 2023/1/10 11:45
     */
    void autoGenerateResource(String appName);
}
