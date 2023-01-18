package top.finder.aether.system.core.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
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
import top.finder.aether.system.core.converter.SysMenuConverter;
import top.finder.aether.system.core.dto.SysMenuCreateDto;
import top.finder.aether.system.core.dto.SysMenuPageQueryDto;
import top.finder.aether.system.core.dto.SysMenuQueryDto;
import top.finder.aether.system.core.dto.SysMenuUpdateDto;
import top.finder.aether.system.core.entity.SysMenu;
import top.finder.aether.system.core.mapper.SysMenuMapper;
import top.finder.aether.system.core.service.SysMenuService;
import top.finder.aether.system.core.vo.SysMenuVo;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static top.finder.aether.system.api.support.pool.menu.MenuCacheConstantPool.M_VO_MENU;
import static top.finder.aether.system.api.support.pool.menu.MenuCacheConstantPool.P_MENU;

/**
 * <p>系统菜单服务接口实现类</p>
 *
 * @author guocq
 * @since 2023/01/18
 */
@Service(value = "sysMenuService")
public class SysMenuServiceImpl implements SysMenuService {
    private static final Logger log = LoggerFactory.getLogger(SysMenuServiceImpl.class);
    
    @Resource
    private SysMenuMapper mapper;

    /**
     * <p>新增：系统菜单</p>
     *
     * @param dto 新增入参
     * @author guocq
     * @date 2023/01/18 15:57
     */
    @Override
    @CacheEvict(cacheNames = {P_MENU}, allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void create(SysMenuCreateDto dto) {
        log.debug("新增系统菜单, 入参={}", dto);
        checkBeforeCreate(dto);
        SysMenu sysMenu = SysMenuConverter.createDtoToEntity(dto);
        mapper.insert(sysMenu);
        log.debug("新增系统菜单成功");
    }
    
    /**
     * <p>批量删除：系统菜单</p>
     *
     * @param idSet 主键集合
     * @author guocq
     * @date 2023/01/18 15:57
     */
    @Override
    @CacheEvict(cacheNames = {P_MENU}, allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<Long> idSet) {
        log.debug("删除系统菜单, 入参={}", idSet);
        checkBeforeDelete(idSet);
        mapper.logicBatchDeleteByIds(idSet, System.currentTimeMillis());
        log.debug("删除系统菜单成功");
    }
    
    /**
     * <p>更新：系统菜单</p>
     *
     * @param dto 更新入参
     * @author guocq
     * @date 2023/01/18 15:57
     */
    @Override
    @CacheEvict(cacheNames = {P_MENU}, allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update(SysMenuUpdateDto dto) {
        log.debug("更新系统菜单, 入参={}", dto);
        checkBeforeUpdate(dto);
        SysMenu sysMenu = SysMenuConverter.updateDtoToEntity(dto);
        mapper.insert(sysMenu);
        log.debug("更新系统菜单成功");
    }
    
    /**
     * <p>查询：系统菜单列表</p>
     *
     * @param dto 查询入参
     * @return {@link List}
     * @author guocq
     * @date 2023/01/18 15:57
     */
    @Override
    @Cacheable(cacheNames = M_VO_MENU, keyGenerator = "modelKeyGenerator", unless = "#result.isEmpty()")
    public List<SysMenuVo> listQuery(SysMenuQueryDto dto) {
        List<SysMenu> sysMenuList = mapper.selectList(dto.getCommonWrapper());
        return sysMenuList.stream().map(SysMenuConverter::entityToVo).collect(Collectors.toList());
    }
    
    /**
     * <p>分页查询：系统菜单</p>
     *
     * @param dto 参数
     * @return {@link IPage}
     * @author guocq
     * @date 2023/01/18 15:57
     */
    @Override
    public IPage<SysMenuVo> pageQuery(SysMenuPageQueryDto dto) {
        IPage<SysMenu> page = PageHelper.initPage(dto);
        page = mapper.selectPage(page, dto.getCommonWrapper());
        return page.convert(SysMenuConverter::entityToVo);
    }

    /**
     * <p>新增系统菜单前校验</p>
     *
     * @param dto 新增入参
     * @author guocq
     * @date 2023/01/18 15:57
     */
    private void checkBeforeCreate(SysMenuCreateDto dto) {
        String menuCode = dto.getMenuCode();
        LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<SysMenu>();
        wrapper.eq(SysMenu::getMenuCode, menuCode);
        boolean exists = mapper.exists(wrapper);
        if (exists) {
            throw LoggerUtil.logAetherValidError(log, "菜单码值为[menuCode={}]的数据已存在，不能重复新增", menuCode);
        }
    }
    
    /**
     * <p>删除系统菜单前校验</p>
     *
     * @param idSet 主键集合
     * @author guocq
     * @date 2023/01/18 15:57
     */
    private void checkBeforeDelete(Set<Long> idSet) {
        if (CollUtil.isEmpty(idSet)) {
            throw LoggerUtil.logAetherValidError(log, "删除时主键集合不能为空", idSet);
        }
        LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<SysMenu>()
                .in(SysMenu::getId, idSet);
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
     * <p>更新系统菜单前校验</p>
     *
     * @param dto 更新入参
     * @author guocq
     * @date 2023/01/18 15:57
     */
    private void checkBeforeUpdate(SysMenuUpdateDto dto) {
        Long id = dto.getId();
        LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<SysMenu>()
                .eq(SysMenu::getId, id);
        boolean exists = mapper.exists(wrapper);
        if (!exists) {
            throw LoggerUtil.logAetherValidError(log, "主键为[id={}]的数据不存在，不能进行更新维护", id);
        }
        String menuCode = dto.getMenuCode();
        wrapper = new LambdaQueryWrapper<SysMenu>();
        wrapper.ne(SysMenu::getId, id);
        boolean checkUnique = false;
        if (StrUtil.isNotBlank(menuCode)) {
            checkUnique = true;
            wrapper.eq(SysMenu::getMenuCode, menuCode);
        }
       if (checkUnique) {
            exists = mapper.exists(wrapper);
            if (exists) {
                throw LoggerUtil.logAetherValidError(log, "菜单码值为[menuCode={}]的数据已存在，不能重复更新", menuCode);
            }
       }     
    }
}
