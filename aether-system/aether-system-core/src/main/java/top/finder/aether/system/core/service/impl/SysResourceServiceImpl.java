package top.finder.aether.system.core.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.finder.aether.common.utils.LoggerUtil;
import top.finder.aether.data.core.support.helper.PageHelper;
import top.finder.aether.system.core.converter.SysResourceConverter;
import top.finder.aether.system.core.dto.SysResourceCreateDto;
import top.finder.aether.system.core.dto.SysResourcePageQueryDto;
import top.finder.aether.system.core.dto.SysResourceQueryDto;
import top.finder.aether.system.core.dto.SysResourceUpdateDto;
import top.finder.aether.system.core.entity.SysResource;
import top.finder.aether.system.core.mapper.SysResourceMapper;
import top.finder.aether.system.core.service.SysResourceService;
import top.finder.aether.system.api.vo.SysResourceVo;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static top.finder.aether.system.api.support.pool.resource.ResourceCacheConstantPool.M_VO_RESOURCE;
import static top.finder.aether.system.api.support.pool.resource.ResourceCacheConstantPool.P_RESOURCE;

/**
 * <p>系统资源业务接口实现类</p>
 *
 * @author guocq
 * @since 2023/1/10
 */
@Service(value = "resourceService")
public class SysResourceServiceImpl implements SysResourceService {
    private static final Logger log = LoggerFactory.getLogger(SysResourceServiceImpl.class);

    private final SysResourceMapper mapper;

    public SysResourceServiceImpl(SysResourceMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * <p>创建系统资源信息</p>
     *
     * @param dto 新增参数
     * @author guocq
     * @date 2023/1/16 15:20
     */
    @Override
    @CacheEvict(cacheNames = {P_RESOURCE}, allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void create(SysResourceCreateDto dto) {
        log.debug("新增资源信息, 入参={}", dto);
        checkBeforeCreate(dto);
        SysResource sysResource = SysResourceConverter.createDtoToEntity(dto);
        mapper.insert(sysResource);
        log.debug("新增资源成功");
    }

    /**
     * <p>更新系统资源信息</p>
     *
     * @param dto 新增参数
     * @author guocq
     * @date 2023/1/16 15:20
     */
    @Override
    @CacheEvict(cacheNames = {P_RESOURCE}, allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update(SysResourceUpdateDto dto) {
        log.debug("更新资源信息, 入参={}", dto);
        checkBeforeUpdate(dto);
        SysResource sysResource = SysResourceConverter.updateDtoToEntity(dto);
        mapper.updateById(sysResource);
        log.debug("更新资源成功");
    }

    /**
     * <p>批量删除：资源信息</p>
     *
     * @param idSet 主键集合
     * @author guocq
     * @date 2023/1/16 15:21
     */
    @Override
    @CacheEvict(cacheNames = {P_RESOURCE}, allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<Long> idSet) {
        log.debug("删除资源信息, 入参={}", idSet);
        checkBeforeDelete(idSet);
        mapper.logicBatchDeleteByIds(idSet, System.currentTimeMillis());
        log.debug("删除资源成功");
    }

    /**
     * <p>查询：系统资源信息列表</p>
     *
     * @param dto 查询入参
     * @return {@link List}
     * @author guocq
     * @date 2022/12/27 11:01
     */
    @Override
    @Cacheable(cacheNames = M_VO_RESOURCE, keyGenerator = "modelKeyGenerator", unless = "#result.isEmpty()")
    public List<SysResourceVo> listQuery(SysResourceQueryDto dto) {
        List<SysResource> sysResources = mapper.selectList(dto.getCommonWrapper());
        return sysResources.stream().map(SysResourceConverter::entityToVo).collect(Collectors.toList());
    }

    /**
     * <p>分页查询系统资源信息</p>
     *
     * @param dto 参数
     * @return {@link IPage}
     * @author guocq
     * @date 2023/1/13 15:35
     */
    @Override
    public IPage<SysResourceVo> pageQuery(SysResourcePageQueryDto dto) {
        IPage<SysResource> page = PageHelper.initPage(dto);
        page = mapper.selectPage(page, dto.getCommonWrapper());
        return page.convert(SysResourceConverter::entityToVo);
    }

    /**
     * <p>新增资源信息前校验</p>
     *
     * @param dto 新增入参
     * @author guocq
     * @date 2022/12/27 10:30
     */
    private void checkBeforeCreate(SysResourceCreateDto dto) {
        String resourceCode = dto.getResourceCode();
        String resourceUrl = dto.getResourceUrl();
        Wrapper<SysResource> wrapper = new LambdaQueryWrapper<SysResource>()
                .eq(SysResource::getResourceCode, resourceCode)
                .eq(SysResource::getResourceUrl, resourceUrl);
        boolean exists = mapper.exists(wrapper);
        if (exists) {
            throw LoggerUtil.logAetherValidError(log, "资源编码为[resourceCode={}]以及资源路径为[resourceUrl={}]的数据已存在，不能重复新增", resourceCode, resourceUrl);
        }
    }

    /**
     * <p>更新资源信息前校验</p>
     *
     * @param dto 更新入参
     * @author guocq
     * @date 2022/12/27 10:46
     */
    private void checkBeforeUpdate(SysResourceUpdateDto dto) {
        Long id = dto.getId();
        LambdaQueryWrapper<SysResource> wrapper = new LambdaQueryWrapper<SysResource>()
                .eq(SysResource::getId, id);
        boolean exists = mapper.exists(wrapper);
        if (!exists) {
            throw LoggerUtil.logAetherValidError(log, "主键为[id={}]的数据不存在，不能进行更新维护", id);
        }
        String resourceCode = dto.getResourceCode();
        String resourceUrl = dto.getResourceUrl();
        boolean checkUnique = false;
        wrapper = new LambdaQueryWrapper<>();
        wrapper.ne(SysResource::getId, id);
        if (StrUtil.isNotBlank(resourceCode)) {
            checkUnique = true;
            wrapper.eq(SysResource::getResourceCode, resourceCode);
        }
        if (StrUtil.isNotBlank(resourceUrl)) {
            checkUnique = true;
            wrapper.eq(SysResource::getResourceUrl, resourceUrl);
        }
        if (checkUnique) {
            exists = mapper.exists(wrapper);
            if (exists) {
                throw LoggerUtil.logAetherValidError(log, "资源编码为[resourceCode={}]以及资源路径为[resourceUrl={}]的数据已存在，不能重复更新", resourceCode);
            }
        }
    }

    /**
     * <p>删除资源信息前校验</p>
     *
     * @param idSet 主键集合
     * @author guocq
     * @date 2022/12/27 10:54
     */
    private void checkBeforeDelete(Set<Long> idSet) {
        if (CollUtil.isEmpty(idSet)) {
            throw LoggerUtil.logAetherValidError(log, "删除时主键集合不能为空", idSet);
        }
        Wrapper<SysResource> wrapper = new LambdaQueryWrapper<SysResource>()
                .in(SysResource::getId, idSet);
        Long count = mapper.selectCount(wrapper);
        int size = idSet.size();
        if (count == 0) {
            throw LoggerUtil.logAetherValidError(log, "不存在需要删除的数据[idSet={}]", idSet);
        }
        if (count < size) {
            log.warn("待删除的idSet={}中部分主键不存在无法删除，系统将删除已存在的数据{}条", idSet, count);
        }
    }
}
