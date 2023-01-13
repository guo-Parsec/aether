package top.finder.aether.system.core.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.finder.aether.system.core.entity.SysResource;
import top.finder.aether.system.core.mapper.SysResourceMapper;
import top.finder.aether.system.core.service.SysResourceService;
import top.finder.aether.system.core.transform.SysResourceTransform;
import top.finder.aether.data.core.entity.ResourceMapping;
import top.finder.aether.data.core.support.runner.SystemSetting;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static top.finder.aether.common.support.helper.CodeHelper.castToList;
import static top.finder.aether.data.core.support.pool.SystemSettingConstantPool.RESOURCE_MAPPING;

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
     * <p>根据{@code applicationName}自动生成不同应用的系统资源数据</p>
     *
     * @param applicationName 指定应用名称
     * @author guocq
     * @date 2023/1/12 13:43
     */
    @Override
    public void autoGenerateSysResource(String applicationName) {
        log.info("根据[applicationName={}]自动生成不同应用的系统资源数据", applicationName);
        Object appSetting = systemSetting.getAppSetting(applicationName, RESOURCE_MAPPING);
        if (appSetting == null) {
            log.debug("获取系统[{}]的资源数据为空", applicationName);
            return;
        }
        List<ResourceMapping> resourceMappings = castToList(appSetting, ResourceMapping.class);
        if (CollUtil.isEmpty(resourceMappings)) {
            log.debug("获取系统[{}]的资源数据为空", applicationName);
            return;
        }
        List<SysResource> resourceList = findRealGenerateResourceList(resourceMappings);
        if (CollUtil.isEmpty(resourceList)) {
            log.debug("没有需要提交的数据");
            return;
        }
        this.saveBatch(resourceList);
        log.debug("使用批量提交数据{}条成功", resourceList.size());
    }

    /**
     * <p>获取最终需要创建的资源</p>
     *
     * @param resourceMappings 资源映射
     * @return {@link List}
     * @author guocq
     * @date 2023/1/12 14:47
     */
    private List<SysResource> findRealGenerateResourceList(List<ResourceMapping> resourceMappings) {
        Set<String> resourceCodes = resourceMappings.stream().map(ResourceMapping::getResourceCode).collect(Collectors.toSet());
        Set<String> existResourceCode = findExistResourceCode(resourceCodes);
        log.debug("从数据库中查询到已存在的资源数据{}条", existResourceCode.size());
        Iterator<ResourceMapping> iterator = resourceMappings.iterator();
        while (iterator.hasNext()) {
            ResourceMapping next = iterator.next();
            if (existResourceCode.contains(next.getResourceCode())) {
                log.trace("{}已存在将忽略添加", next.getResourceCode());
                iterator.remove();
            }
        }
        return resourceMappings.stream()
                .map(SysResourceTransform::resourceMappingToSysResource)
                .collect(Collectors.toList());
    }

    /**
     * <p>获取已存在的资源码，为防止in参数过大，将in参数超过1000的参数进行分割，批量查询</p>
     *
     * @param resourceCodes 资源码
     * @return java.util.Set<java.lang.String>
     * @author guocq
     * @date 2023/1/12 14:45
     */
    private Set<String> findExistResourceCode(Set<String> resourceCodes) {
        int size = resourceCodes.size();
        log.debug("待新增的数据共有{}条", size);
        Set<String> existResourceCode = Sets.newHashSet();
        LambdaQueryWrapper<SysResource> wrapper = getFindResourceCodeWrapper();
        final int batchSize = 10;
        if (size >= batchSize) {
            int splitSize = batchSize - 1;
            List<List<String>> codeGroups = CollUtil.split(resourceCodes, splitSize);
            log.debug("需要查询验证的总条数{}大于最大显示条数{},需要分割为大小为{}的集合{}个", size, batchSize, splitSize, codeGroups.size());
            for (List<String> codes : codeGroups) {
                wrapper = getFindResourceCodeWrapper().in(SysResource::getResourceCode, codes);
                existResourceCode.addAll(mapper.selectList(wrapper).stream().map(SysResource::getResourceCode).collect(Collectors.toSet()));
            }
            return existResourceCode;
        }
        wrapper.in(SysResource::getResourceCode, resourceCodes);
        return mapper.selectList(wrapper).stream().map(SysResource::getResourceCode).collect(Collectors.toSet());
    }

    /**
     * <p>获取查询资源码的{@link LambdaQueryWrapper}</p>
     *
     * @return {@link LambdaQueryWrapper}
     * @author guocq
     * @date 2023/1/12 15:12
     */
    private LambdaQueryWrapper<SysResource> getFindResourceCodeWrapper() {
        return new LambdaQueryWrapper<SysResource>()
                .select(SysResource::getResourceCode);
    }
}
