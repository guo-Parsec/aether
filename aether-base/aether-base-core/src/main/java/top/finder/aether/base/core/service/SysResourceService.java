package top.finder.aether.base.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.finder.aether.base.core.entity.SysResource;

/**
 * <p>系统资源业务接口</p>
 *
 * @author guocq
 * @since 2023/1/10
 */
public interface SysResourceService extends IService<SysResource> {
    /**
     * <p>根据{@code applicationName}自动生成不同应用的系统资源数据</p>
     *
     * @param applicationName 指定应用名称
     * @author guocq
     * @date 2023/1/12 13:43
     */
    void autoGenerateSysResource(String applicationName);
}
