package top.finder.aether.base.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import top.finder.aether.base.core.entity.SysResource;
import top.finder.aether.base.core.mapper.SysResourceMapper;
import top.finder.aether.base.core.service.SysResourceService;

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

    public SysResourceServiceImpl(SysResourceMapper mapper) {
        this.mapper = mapper;
    }
}
