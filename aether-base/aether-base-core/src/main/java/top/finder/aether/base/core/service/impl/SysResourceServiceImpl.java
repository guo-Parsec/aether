package top.finder.aether.base.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.finder.aether.base.core.entity.SysResource;
import top.finder.aether.base.core.mapper.SysResourceMapper;
import top.finder.aether.base.core.service.SysResourceService;
import top.finder.aether.data.core.model.ApiModel;
import top.finder.aether.data.core.support.pool.SystemSettingConstantPool;
import top.finder.aether.data.core.support.runner.SystemSetting;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>系统资源业务接口实现类</p>
 *
 * @author guocq
 * @since 2023/1/10
 */
@Service(value = "resourceService")
public class SysResourceServiceImpl extends ServiceImpl<SysResourceMapper, SysResource> implements SysResourceService {
    private static final Logger log = LoggerFactory.getLogger(SysResourceServiceImpl.class);

    private final SysResourceMapper mapper;
    private SystemSetting systemSetting;

    public SysResourceServiceImpl(SysResourceMapper mapper) {
        this.mapper = mapper;
    }

    @Autowired
    public void setSystemSetting(SystemSetting systemSetting) {
        this.systemSetting = systemSetting;
    }

    /**
     * <p>自动生成资源</p>
     *
     * @param appName 应用名称
     * @author guocq
     * @date 2023/1/10 11:45
     */
    @Override
    public void autoGenerateResource(String appName) {
        @SuppressWarnings("unchecked")
        List<ApiModel> apiModels = (List<ApiModel>) systemSetting.getAppSetting(appName, SystemSettingConstantPool.API_MODELS);
        List<SysResource> sysResourceList = Lists.newArrayList();
        apiModels.forEach(apiModel -> {
            sysResourceList.addAll(SysResource.apiModelToResource(apiModel));
        });
        sysResourceList.sort(Comparator.comparingInt(SysResource::getResourceSort));
        Set<String> resourceCodeSet = sysResourceList.stream().map(SysResource::getResourceCode).collect(Collectors.toSet());
        mapper.deleteWithResourceCode(resourceCodeSet);
        this.saveBatch(sysResourceList);
    }
}
