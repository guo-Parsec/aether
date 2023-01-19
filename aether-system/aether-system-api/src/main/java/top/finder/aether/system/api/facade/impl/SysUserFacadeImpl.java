package top.finder.aether.system.api.facade.impl;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import top.finder.aether.common.utils.LoggerUtil;
import top.finder.aether.system.api.facade.SysUserFacade;
import top.finder.aether.system.api.repository.SysResourceRepository;
import top.finder.aether.system.api.repository.SysRoleRepository;
import top.finder.aether.system.api.repository.SysUserRepository;
import top.finder.aether.system.api.vo.SysResourceVo;
import top.finder.aether.system.api.vo.SysRoleVo;
import top.finder.aether.system.api.vo.SysUserVo;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static top.finder.aether.system.api.support.pool.SystemConstantPool.*;

/**
 * <p>系统用户Facade接口实现</p>
 *
 * @author guocq
 * @since 2023/1/19
 */
@Component(value = "sysUserFacade")
public class SysUserFacadeImpl implements SysUserFacade {
    private static final Logger log = LoggerFactory.getLogger(SysUserFacadeImpl.class);

    private static final Map<Long, Map<String, Set<String>>> USER_SECURITY_DETAILS = Maps.newHashMap();

    private final SysUserRepository repository;

    private SysRoleRepository roleRepository;

    private SysResourceRepository resourceRepository;

    public SysUserFacadeImpl(SysUserRepository repository) {
        this.repository = repository;
    }

    @Autowired
    public void setRoleRepository(SysRoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Autowired
    public void setResourceRepository(SysResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    /**
     * <p>根据用户id查询用户相关安全详情</p>
     *
     * @param userId 用户id
     * @return java.util.Map<java.lang.String, java.util.Set < java.lang.String>>
     * @author guocq
     * @date 2023/1/19 14:16
     */
    @Transactional(readOnly = true)
    @Override
    public Map<String, Set<String>> findSecurityDetailsByUserId(Long userId) {
        log.debug("根据[userId={}]查询安全详情信息", userId);
        if (USER_SECURITY_DETAILS.containsKey(userId)) {
            return USER_SECURITY_DETAILS.get(userId);
        }
        checkBeforeFindSecurityDetails(userId);
        List<SysRoleVo> roleList = roleRepository.findRoleCodeByUserId(userId);
        Set<Long> roleIds = Sets.newHashSet();
        Set<String> roles = Sets.newHashSet();
        roleList.forEach(role -> {
            roleIds.add(Long.valueOf(role.getId()));
            roles.add(role.getRoleCode());
        });
        List<SysResourceVo> resourceList = resourceRepository.findResourceUrlByRoleIds(roleIds);
        Set<String> resourceUrls = Sets.newHashSet();
        Set<String> permissions = Sets.newHashSet();
        resourceList.forEach(resource -> {
            resourceUrls.add(resource.getResourceUrl());
            permissions.add(resource.getResourceCode());
        });
        Map<String, Set<String>> securityDetailsMap = Maps.newHashMapWithExpectedSize(3);
        securityDetailsMap.put(SECURITY_DETAILS_KEY_ROLES, roles);
        securityDetailsMap.put(SECURITY_DETAILS_KEY_PERMISSIONS, permissions);
        securityDetailsMap.put(SECURITY_DETAILS_KEY_RESOURCE_URLS, resourceUrls);
        USER_SECURITY_DETAILS.put(userId, securityDetailsMap);
        return securityDetailsMap;
    }

    /**
     * <p>清楚指定缓存</p>
     *
     * @param userId 用户id
     * @author guocq
     * @date 2023/1/19 15:17
     */
    @Override
    public void clearCache(Long userId) {
        if (userId == null) {
            log.info("清除[USER_SECURITY_DETAILS]中的所有缓存");
            USER_SECURITY_DETAILS.clear();
            return;
        }
        log.info("清除[USER_SECURITY_DETAILS]中的缓存， key={}", userId);
        USER_SECURITY_DETAILS.remove(userId);
    }

    /**
     * <p>根据用户id查询安全详情信息前校验</p>
     *
     * @param userId 用户id
     * @author guocq
     * @date 2023/1/19 14:20
     */
    private void checkBeforeFindSecurityDetails(Long userId) {
        SysUserVo userVo = repository.findById(userId);
        if (userVo == null || userVo.getId() == null) {
            throw LoggerUtil.logAetherValidError(log, "用户id[userId={}]的用户信息不存在，无法查询安全详情信息", userId);
        }
    }

}
