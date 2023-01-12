package top.finder.aether.base.core.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.finder.aether.base.api.tools.DictTool;
import top.finder.aether.base.core.dto.RoleCreateDto;
import top.finder.aether.base.core.dto.RoleUpdateDto;
import top.finder.aether.base.core.entity.Role;
import top.finder.aether.base.core.mapper.RoleMapper;
import top.finder.aether.base.core.service.RoleService;
import top.finder.aether.base.core.vo.RoleVo;
import top.finder.aether.common.support.helper.TransformerHelper;
import top.finder.aether.common.utils.LoggerUtil;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>系统角色业务接口实现类</p>
 *
 * @author guocq
 * @since 2022/12/27
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    private static final Logger log = LoggerFactory.getLogger(RoleServiceImpl.class);

    @Resource
    private RoleMapper roleMapper;

    /**
     * <p>新增：角色信息</p>
     *
     * @param createDto 新增入参
     * @author guocq
     * @date 2022/12/27 9:25
     */
    @Override
    @CacheEvict(cacheNames = {"AMS:ROLE:SINGLE", "AMS:ROLE:LIST"}, allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void create(RoleCreateDto createDto) {
        log.debug("新增角色信息, 入参={}", createDto);
        checkBeforeCreate(createDto);
        Role role = TransformerHelper.transformer(createDto, Role.class);
        roleMapper.insert(role);
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
    @CacheEvict(cacheNames = {"AMS:ROLE:SINGLE", "AMS:ROLE:LIST"}, allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<Long> idSet) {
        log.debug("删除角色信息, 入参={}", idSet);
        checkBeforeDelete(idSet);
        roleMapper.logicBatchDeleteByIds(idSet, System.currentTimeMillis());
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
    @CacheEvict(cacheNames = {"AMS:ROLE:SINGLE", "AMS:ROLE:LIST"}, allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update(RoleUpdateDto updateDto) {
        log.debug("更新角色信息, 入参={}", updateDto);
        checkBeforeUpdate(updateDto);
        Role role = TransformerHelper.transformer(updateDto, Role.class);
        roleMapper.updateById(role);
        log.debug("更新角色成功");
    }

    /**
     * <p>查询：角色信息列表</p>
     *
     * @param role 查询入参
     * @return {@link List < RoleVo >}
     * @author guocq
     * @date 2022/12/27 11:01
     */
    @Cacheable(cacheNames = "AMS:ROLE:LIST", keyGenerator = "modelKeyGenerator", unless = "#result.isEmpty()")
    @Override
    public List<RoleVo> listQuery(Role role) {
        Wrapper<Role> wrapper = new LambdaQueryWrapper<Role>()
                .eq(ObjectUtil.isNotEmpty(role.getId()), Role::getId, role.getId())
                .eq(StrUtil.isNotBlank(role.getRoleCode()), Role::getRoleCode, role.getRoleCode())
                .like(StrUtil.isNotBlank(role.getRoleName()), Role::getRoleName, role.getRoleName());
        List<Role> roles = roleMapper.selectList(wrapper);
        return roles.stream().map(ele -> TransformerHelper.transformer(ele, RoleVo.class))
                .peek(DictTool::translate).collect(Collectors.toList());
    }

    /**
     * <p>新增角色信息前校验</p>
     *
     * @param createDto 新增入参
     * @author guocq
     * @date 2022/12/27 10:30
     */
    private void checkBeforeCreate(RoleCreateDto createDto) {
        String roleCode = createDto.getRoleCode();
        Wrapper<Role> wrapper = new LambdaQueryWrapper<Role>()
                .eq(Role::getRoleCode, roleCode);
        boolean exists = roleMapper.exists(wrapper);
        if (exists) {
            throw LoggerUtil.logAetherValidError(log, "角色编码为[roleCode={}]的数据已存在，不能重复新增", roleCode);
        }
    }

    /**
     * <p>更新角色信息前校验</p>
     *
     * @param roleUpdateDto 更新入参
     * @author guocq
     * @date 2022/12/27 10:46
     */
    private void checkBeforeUpdate(RoleUpdateDto roleUpdateDto) {
        Long id = roleUpdateDto.getId();
        Wrapper<Role> wrapper = new LambdaQueryWrapper<Role>()
                .eq(Role::getId, id);
        boolean exists = roleMapper.exists(wrapper);
        if (!exists) {
            throw LoggerUtil.logAetherValidError(log, "主键为[id={}]的数据不存在，不能进行更新维护", id);
        }
        String roleCode = roleUpdateDto.getRoleCode();
        if (StrUtil.isNotBlank(roleCode)) {
            wrapper = new LambdaQueryWrapper<Role>()
                    .eq(Role::getRoleCode, roleCode)
                    .ne(Role::getId, id);
            exists = roleMapper.exists(wrapper);
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
        Wrapper<Role> wrapper = new LambdaQueryWrapper<Role>()
                .in(Role::getId, idSet);
        Long count = roleMapper.selectCount(wrapper);
        int size = idSet.size();
        if (count < size) {
            log.warn("待删除的idSet={}中部分主键不存在无法删除，系统将删除已存在的数据{}条", idSet, count);
        }
    }
}
