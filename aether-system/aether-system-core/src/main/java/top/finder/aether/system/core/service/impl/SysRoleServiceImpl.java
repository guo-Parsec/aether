package top.finder.aether.system.core.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.finder.aether.common.utils.LoggerUtil;
import top.finder.aether.data.core.support.helper.PageHelper;
import top.finder.aether.system.core.converter.SysRoleConverter;
import top.finder.aether.system.core.dto.*;
import top.finder.aether.system.core.entity.SysResource;
import top.finder.aether.system.core.entity.SysRole;
import top.finder.aether.system.core.mapper.SysResourceMapper;
import top.finder.aether.system.core.mapper.SysRoleMapper;
import top.finder.aether.system.core.service.SysRoleService;
import top.finder.aether.system.core.vo.SysRoleVo;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static top.finder.aether.system.api.support.pool.role.RoleCacheConstantPool.M_VO_ROLE;
import static top.finder.aether.system.api.support.pool.role.RoleCacheConstantPool.P_ROLE;

/**
 * <p>系统角色业务接口实现类</p>
 *
 * @author guocq
 * @since 2022/12/27
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
    private static final Logger log = LoggerFactory.getLogger(SysRoleServiceImpl.class);

    @Resource
    private SysRoleMapper mapper;
    @Resource
    private SysResourceMapper sysResourceMapper;

    /**
     * <p>新增：角色信息</p>
     *
     * @param createDto 新增入参
     * @author guocq
     * @date 2022/12/27 9:25
     */
    @Override
    @CacheEvict(cacheNames = {P_ROLE}, allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void create(SysRoleCreateDto createDto) {
        log.debug("新增角色信息, 入参={}", createDto);
        checkBeforeCreate(createDto);
        SysRole sysRole = SysRoleConverter.createDtoToEntity(createDto);
        mapper.insert(sysRole);
        log.debug("新增角色成功");
    }

    /**
     * <p>批量删除：角色信息</p>
     *
     * @param idSet 主键集合
     * @author guocq
     * @date 2022/12/27 10:48
     */
    @Override
    @CacheEvict(cacheNames = {P_ROLE}, allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<Long> idSet) {
        log.debug("删除角色信息, 入参={}", idSet);
        checkBeforeDelete(idSet);
        mapper.logicBatchDeleteByIds(idSet, System.currentTimeMillis());
        log.debug("删除角色成功");
    }

    /**
     * <p>更新：角色信息</p>
     *
     * @param updateDto 更新入参
     * @author guocq
     * @date 2022/12/27 10:39
     */
    @Override
    @CacheEvict(cacheNames = {P_ROLE}, allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update(SysRoleUpdateDto updateDto) {
        log.debug("更新角色信息, 入参={}", updateDto);
        checkBeforeUpdate(updateDto);
        SysRole sysRole = SysRoleConverter.updateDtoToEntity(updateDto);
        mapper.updateById(sysRole);
        log.debug("更新角色成功");
    }

    /**
     * <p>查询：角色信息列表</p>
     *
     * @param dto 查询入参
     * @return {@link List}
     * @author guocq
     * @date 2022/12/27 11:01
     */
    @Cacheable(cacheNames = M_VO_ROLE, keyGenerator = "modelKeyGenerator", unless = "#result.isEmpty()")
    @Override
    public List<SysRoleVo> listQuery(SysRoleQueryDto dto) {
        List<SysRole> sysRoles = mapper.selectList(dto.getCommonWrapper());
        return sysRoles.stream().map(SysRoleConverter::entityToVo).collect(Collectors.toList());
    }

    /**
     * <p>分页查询角色信息</p>
     *
     * @param dto 参数
     * @return {@link IPage}
     * @author guocq
     * @date 2023/1/13 15:35
     */
    @Override
    public IPage<SysRoleVo> pageQuery(SysRolePageQueryDto dto) {
        IPage<SysRole> page = PageHelper.initPage(dto);
        page = mapper.selectPage(page, dto.getCommonWrapper());
        return page.convert(SysRoleConverter::entityToVo);
    }

    /**
     * <p>为角色赋予资源</p>
     *
     * @param dto 入参
     * @author guocq
     * @date 2023/1/9 10:14
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void grantResourceToRole(GrantResourceToRoleDto dto) {
        log.debug("为角色赋予资源,入参={}", dto);
        checkBeforeGrantResourceToRole(dto);
        Long id = dto.getId();
        mapper.unbindResourceOfRole(id);
        mapper.bindResourceOfUser(id, dto.getResourceId());
        log.debug("为角色赋予资源成功");
    }

    /**
     * <p>新增角色信息前校验</p>
     *
     * @param createDto 新增入参
     * @author guocq
     * @date 2022/12/27 10:30
     */
    private void checkBeforeCreate(SysRoleCreateDto createDto) {
        String roleCode = createDto.getRoleCode();
        Wrapper<SysRole> wrapper = new LambdaQueryWrapper<SysRole>()
                .eq(SysRole::getRoleCode, roleCode);
        boolean exists = mapper.exists(wrapper);
        if (exists) {
            throw LoggerUtil.logAetherValidError(log, "角色编码为[roleCode={}]的数据已存在，不能重复新增", roleCode);
        }
    }

    /**
     * <p>更新角色信息前校验</p>
     *
     * @param sysRoleUpdateDto 更新入参
     * @author guocq
     * @date 2022/12/27 10:46
     */
    private void checkBeforeUpdate(SysRoleUpdateDto sysRoleUpdateDto) {
        Long id = sysRoleUpdateDto.getId();
        Wrapper<SysRole> wrapper = new LambdaQueryWrapper<SysRole>()
                .eq(SysRole::getId, id);
        boolean exists = mapper.exists(wrapper);
        if (!exists) {
            throw LoggerUtil.logAetherValidError(log, "主键为[id={}]的数据不存在，不能进行更新维护", id);
        }
        String roleCode = sysRoleUpdateDto.getRoleCode();
        if (StrUtil.isNotBlank(roleCode)) {
            wrapper = new LambdaQueryWrapper<SysRole>()
                    .eq(SysRole::getRoleCode, roleCode)
                    .ne(SysRole::getId, id);
            exists = mapper.exists(wrapper);
            if (exists) {
                throw LoggerUtil.logAetherValidError(log, "角色编码为[roleCode={}]的数据已存在，不能重复更新", roleCode);
            }
        }
    }

    /**
     * <p>删除角色信息前校验</p>
     *
     * @param idSet 主键集合
     * @author guocq
     * @date 2022/12/27 10:54
     */
    private void checkBeforeDelete(Set<Long> idSet) {
        if (CollUtil.isEmpty(idSet)) {
            throw LoggerUtil.logAetherValidError(log, "删除时主键集合不能为空", idSet);
        }
        Wrapper<SysRole> wrapper = new LambdaQueryWrapper<SysRole>()
                .in(SysRole::getId, idSet);
        Long count = mapper.selectCount(wrapper);
        int size = idSet.size();
        if (count == 0) {
            throw LoggerUtil.logAetherValidError(log, "不存在需要删除的数据[idSet={}]", idSet);
        }
        if (count < size) {
            log.warn("待删除的idSet={}中部分主键不存在无法删除，系统将删除已存在的数据{}条", idSet, count);
        }
    }

    /**
     * <p>为角色赋资源前校验</p>
     *
     * @param dto 入参
     * @author guocq
     * @date 2023/1/9 10:27
     */
    private void checkBeforeGrantResourceToRole(GrantResourceToRoleDto dto) {
        Long id = dto.getId();
        SysRole sysRole = mapper.selectById(id);
        if (sysRole == null) {
            throw LoggerUtil.logAetherError(log, "角色[id={}]不存在", id);
        }
        Set<Long> resourceId = dto.getResourceId();
        Wrapper<SysResource> wrapper = new LambdaQueryWrapper<SysResource>()
                .select(SysResource::getId)
                .in(SysResource::getId, resourceId);
        Set<Long> resourceIdFormDb = sysResourceMapper.selectList(wrapper).stream().map(SysResource::getId).collect(Collectors.toSet());
        if (CollUtil.isEmpty(resourceIdFormDb)) {
            throw LoggerUtil.logAetherError(log, "资源[{}]不存在", resourceId);
        }
        if (resourceIdFormDb.size() < resourceId.size()) {
            log.warn("当前传入资源id列表[{}]有部分数据不存在，系统默认只添加已存在的资源[{}]", resourceId, resourceIdFormDb);
            dto.setResourceId(resourceIdFormDb);
        }
    }
}
