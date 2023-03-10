package top.finder.aether.system.core.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.finder.aether.common.support.annotation.BlockBean;
import top.finder.aether.common.support.annotation.BlockMethod;
import top.finder.aether.common.support.strategy.CryptoStrategy;
import top.finder.aether.common.support.strategy.Md5SaltCrypto;
import top.finder.aether.common.utils.LoggerUtil;
import top.finder.aether.data.core.entity.UserDetails;
import top.finder.aether.data.core.support.helper.PageHelper;
import top.finder.aether.security.api.facade.SecurityFacade;
import top.finder.aether.system.api.dto.SysUserCreateDto;
import top.finder.aether.system.api.facade.SysParamFacade;
import top.finder.aether.system.api.facade.SysUserFacade;
import top.finder.aether.system.api.holders.SysParamHolders;
import top.finder.aether.system.api.support.pool.SystemConstantPool;
import top.finder.aether.system.api.tools.DictTool;
import top.finder.aether.system.api.vo.SysUserVo;
import top.finder.aether.system.core.converter.SysUserConverter;
import top.finder.aether.system.core.dto.GrantRoleToUserDto;
import top.finder.aether.system.core.dto.SysUserChangePasswordDto;
import top.finder.aether.system.core.dto.SysUserPageQueryDto;
import top.finder.aether.system.core.dto.SysUserUpdateDto;
import top.finder.aether.system.core.entity.SysRole;
import top.finder.aether.system.core.entity.SysUser;
import top.finder.aether.system.core.mapper.SysRoleMapper;
import top.finder.aether.system.core.mapper.SysUserMapper;
import top.finder.aether.system.core.service.SysUserService;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static top.finder.aether.system.api.support.pool.user.UserCacheConstantPool.P_USER;
import static top.finder.aether.system.api.support.pool.user.UserCacheConstantPool.S_UD_USER;
import static top.finder.aether.system.core.converter.SysUserConverter.entityToVoConverter;
import static top.finder.aether.system.core.converter.SysUserConverter.sysUserToUserDetailsConverter;

/**
 * <p>???????????????????????????</p>
 *
 * @author guocq
 * @since 2022/12/14
 */
@Service(value = "userService")
@BlockBean(value = "userService")
public class SysUserServiceImpl implements SysUserService {
    private static final Logger log = LoggerFactory.getLogger(SysUserServiceImpl.class);

    @Resource
    private SysUserMapper mapper;
    @Resource
    private SysRoleMapper sysRoleMapper;
    private CryptoStrategy defaultCryptoStrategy;
    private SysParamFacade sysParamFacade;
    private SysUserFacade userFacade;

    @Autowired
    public void setCryptoStrategy(@Qualifier("md5SaltCrypto") CryptoStrategy cryptoStrategy) {
        this.defaultCryptoStrategy = cryptoStrategy;
    }

    @Autowired
    public void setParamFacade(SysParamFacade sysParamFacade) {
        this.sysParamFacade = sysParamFacade;
    }

    @Autowired
    public void setUserFacade(SysUserFacade userFacade) {
        this.userFacade = userFacade;
    }

    /**
     * <p>??????????????????????????????</p>
     *
     * @param account  ????????????
     * @param password ???????????????????????????
     * @return {@link UserDetails}
     * @author guocq
     * @date 2022/12/28 14:55
     */
    @Override
    public UserDetails loadUser(String account, String password) {
        // ??????????????????????????????
        UserDetails userDetails = SpringUtil.getBean(SysUserService.class).findUserByAccount(account);
        if (userDetails == null) {
            return UserDetails.emptyUser(account);
        }
        boolean match = match(account, password, userDetails.getPassword());
        userDetails.setCertified(match);
        userDetails.setPassword(null);
        return userDetails;
    }

    /**
     * <p>???????????????????????????</p>
     *
     * @param account ?????????
     * @return {@link SysUserVo }
     * @author guocq
     * @date 2022/12/28 16:08
     */
    @Cacheable(cacheNames = S_UD_USER, key = "'ACCOUNT:' + #account")
    public UserDetails findUserByAccount(String account) {
        log.debug("??????[account={}]??????????????????", account);
        Wrapper<SysUser> wrapper = new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getAccount, account);
        SysUser sysUser = mapper.selectOne(wrapper);
        if (ObjectUtil.isNull(sysUser) || ObjectUtil.isNull(sysUser.getId())) {
            log.error("???????????????account={}", account);
            return null;
        }
        return DictTool.convertAndTranslate(sysUser, sysUserToUserDetailsConverter);
    }

    /**
     * <p>?????????????????????</p>
     *
     * @param dto ????????????
     * @author guocq
     * @date 2023/1/5 11:02
     */
    @Override
    @CacheEvict(cacheNames = {P_USER}, allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void create(SysUserCreateDto dto) {
        log.debug("??????????????????, ??????={}", dto);
        checkBeforeCreate(dto);
        SysUser sysUser = SysUserConverter.createDtoToEntity(dto);
        sysUser.setPassword(encrypt(sysUser.getAccount(), dto.getPassword()));
        mapper.insert(sysUser);
        // todo ??????????????????
        log.debug("??????????????????");
    }

    /**
     * <p>?????????????????????</p>
     *
     * @param dto ????????????
     * @author guocq
     * @date 2023/1/5 11:02
     */
    @Override
    @CacheEvict(cacheNames = {P_USER}, allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update(SysUserUpdateDto dto) {
        log.debug("??????????????????, ??????={}", dto);
        checkBeforeUpdate(dto);
        SysUser sysUser = SysUserConverter.updateDtoToEntity(dto);
        mapper.updateById(sysUser);
        log.debug("????????????????????????");
    }

    /**
     * <p>???????????????????????????</p>
     *
     * @param idSet ????????????
     * @author guocq
     * @date 2023/1/5 11:02
     */
    @Override
    @CacheEvict(cacheNames = {P_USER}, allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<Long> idSet) {
        log.debug("??????????????????, ??????={}", idSet);
        checkBeforeDelete(idSet);
        mapper.logicBatchDeleteByIds(idSet, System.currentTimeMillis());
        log.debug("????????????????????????");
    }

    /**
     * <p>??????????????????</p>
     *
     * @param dto ????????????
     * @return {@link IPage <UserVo>}
     * @author guocq
     * @date 2023/1/5 11:04
     */
    @Override
    public IPage<SysUserVo> pageQuery(SysUserPageQueryDto dto) {
        log.debug("??????????????????,??????={}", dto);
        IPage<SysUser> page = PageHelper.initPage(dto);
        Wrapper<SysUser> wrapper = new LambdaQueryWrapper<SysUser>()
                .eq(ObjectUtil.isNotNull(dto.getId()), SysUser::getId, dto.getId())
                .like(StrUtil.isNotBlank(dto.getAccount()), SysUser::getAccount, dto.getAccount())
                .like(StrUtil.isNotBlank(dto.getNickname()), SysUser::getNickname, dto.getNickname())
                .in(CollUtil.isNotEmpty(dto.getSexSet()), SysUser::getSex, dto.getSexSet())
                .ge(ObjectUtil.isNotEmpty(dto.getBirthdayStarter()), SysUser::getBirthday, dto.getBirthdayStarter())
                .le(ObjectUtil.isNotEmpty(dto.getBirthdayEnd()), SysUser::getBirthday, dto.getBirthdayEnd())
                .in(CollUtil.isNotEmpty(dto.getUserTypeSet()), SysUser::getUserType, dto.getUserTypeSet())
                .orderByDesc(SysUser::getGmtModify)
                .orderByDesc(SysUser::getGmtCreate);
        IPage<SysUser> rawPage = mapper.selectPage(page, wrapper);
        return rawPage.convert(record -> DictTool.convertAndTranslate(record, entityToVoConverter));
    }

    /**
     * <p>??????????????????</p>
     *
     * @param dto ??????
     * @author guocq
     * @date 2023/1/9 9:24
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void changePassword(SysUserChangePasswordDto dto) {
        log.debug("??????????????????,??????={}", dto);
        SysUser sysUser = checkBeforeChangePassword(dto);
        String account = dto.getAccount();
        String password = dto.getPassword();
        password = encrypt(account, password);
        Wrapper<SysUser> wrapper = new LambdaUpdateWrapper<SysUser>()
                .eq(SysUser::getAccount, account)
                .set(SysUser::getPassword, password);
        mapper.update(new SysUser(account, password), wrapper);
        log.debug("????????????????????????");
        SecurityFacade.kickOut(sysUser.getId());
    }

    /**
     * <p>????????????</p>
     *
     * @param account ????????????
     * @author guocq
     * @date 2023/1/9 9:41
     */
    @Override
    @CacheEvict(cacheNames = {P_USER}, allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void resetUser(String account) {
        log.debug("????????????, ??????={}", account);
        SysUser sysUser = checkAccountIsExist(account);
        sysUser.setPassword(encrypt(account, getDefaultPassword()));
        sysUser.setEnableStatus(SystemConstantPool.ENABLE_STATUS_ENABLE);
        Wrapper<SysUser> wrapper = new LambdaUpdateWrapper<SysUser>()
                .eq(SysUser::getAccount, account)
                .set(SysUser::getPassword, sysUser.getPassword())
                .set(SysUser::getEnableStatus, SystemConstantPool.ENABLE_STATUS_ENABLE);
        mapper.update(sysUser, wrapper);
        log.debug("??????????????????");
        SecurityFacade.kickOut(sysUser.getId());
    }

    /**
     * <p>????????????????????????</p>
     *
     * @param account      ??????
     * @param enableStatus ????????????
     * @author guocq
     * @date 2023/1/9 9:50
     */
    @Override
    @CacheEvict(cacheNames = {P_USER}, allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void changeUserEnableStatus(String account, Integer enableStatus) {
        String typeMessage = null;
        if (SystemConstantPool.ENABLE_STATUS_ENABLE.equals(enableStatus)) {
            typeMessage = "??????";
        }
        if (SystemConstantPool.ENABLE_STATUS_DISABLE.equals(enableStatus)) {
            typeMessage = "??????";
        }
        if (typeMessage == null) {
            throw LoggerUtil.logAetherValidError(log, "enableStatus={}?????????", enableStatus);
        }
        log.debug("{}??????, ??????[account={},enableStatus={}]", typeMessage, account, enableStatus);
        SysUser sysUser = checkAccountIsExist(account);
        if (ObjectUtil.equals(enableStatus, sysUser.getEnableStatus())) {
            log.warn("??????[account={}]??????{},??????????????????", account, typeMessage);
            return;
        }
        sysUser.setEnableStatus(enableStatus);
        Wrapper<SysUser> wrapper = new LambdaUpdateWrapper<SysUser>()
                .eq(SysUser::getAccount, account)
                .set(SysUser::getEnableStatus, enableStatus);
        mapper.update(sysUser, wrapper);
        log.debug("{}????????????", typeMessage);
        SecurityFacade.kickOut(sysUser.getId());
    }

    /**
     * <p>?????????????????????</p>
     *
     * @param dto ??????
     * @author guocq
     * @date 2023/1/9 10:14
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void grantRoleToUser(GrantRoleToUserDto dto) {
        log.debug("?????????????????????,??????={}", dto);
        checkBeforeGrantRoleToUser(dto);
        Long id = dto.getId();
        mapper.unbindRoleOfUser(id);
        mapper.bindRoleOfUser(id, dto.getRoleId());
        userFacade.clearCache(id);
        log.debug("???????????????????????????");
        SecurityFacade.kickOut(id);
    }

    /**
     * <p>????????????????????????</p>
     *
     * @param dto ??????????????????
     * @author guocq
     * @date 2023/1/5 14:58
     */
    @Override
    @BlockMethod(value = "systemInnerUserCreate")
    public void systemInnerUserCreate(SysUserCreateDto dto) {
        dto.setUserType(SystemConstantPool.USER_TYPE_INNER);
    }

    /**
     * <p>??????????????????</p>
     *
     * @param dto ??????????????????
     * @author guocq
     * @date 2023/1/5 14:58
     */
    @Override
    @BlockMethod(value = "registeredUserCreate")
    public void registeredUserCreate(SysUserCreateDto dto) {
        dto.setUserType(SystemConstantPool.USER_TYPE_REGISTERED);
    }
    
    /**
     * <p>??????????????????????????????</p>
     *
     * @param account    ????????????
     * @param password   ???????????????
     * @param dbPassword ????????????????????????
     * @return boolean
     * @author guocq
     * @date 2022/12/28 15:08
     */
    private boolean match(String account, String password, String dbPassword) {
        CryptoStrategy strategy = this.findDefaultCryptoStrategy();
        if (strategy.isOptionNeed()) {
            Map<String, Object> option = Maps.newHashMapWithExpectedSize(1);
            option.put(Md5SaltCrypto.OPTION_KEY_SALT, account);
            return strategy.match(password, dbPassword, option);
        }
        return strategy.match(password, dbPassword);
    }

    /**
     * <p>??????????????????</p>
     *
     * @param account     ????????????
     * @param rawPassword ????????????
     * @return java.lang.String
     * @author guocq
     * @date 2023/1/5 16:03
     */
    private String encrypt(String account, String rawPassword) {
        CryptoStrategy strategy = this.findDefaultCryptoStrategy();
        if (strategy.isOptionNeed()) {
            Map<String, Object> option = Maps.newHashMapWithExpectedSize(1);
            option.put(Md5SaltCrypto.OPTION_KEY_SALT, account);
            return strategy.encrypt(rawPassword, option);
        }
        return strategy.encrypt(rawPassword);
    }

    /**
     * <p>?????????????????????</p>
     *
     * @return {@link CryptoStrategy}
     * @author guocq
     * @date 2023/1/9 15:25
     */
    private CryptoStrategy findDefaultCryptoStrategy() {
        Optional<SysParamHolders> optional = sysParamFacade.findParamByParamCode(SystemConstantPool.PARAM_DEFAULT_USER_PASSWORD_CRYPTO_STRATEGY);
        if (!optional.isPresent()) {
            return defaultCryptoStrategy;
        }
        String paramValue = optional.get().getParamValue();
        try {
            return SpringUtil.getBean(paramValue, CryptoStrategy.class);
        } catch (Exception e) {
            log.debug("??????[strategy={}]??????????????????????????????", paramValue);
            return defaultCryptoStrategy;
        }
    }

    /**
     * <p>???????????????????????????</p>
     *
     * @param createDto ????????????
     * @author guocq
     * @date 2022/12/27 10:30
     */
    private void checkBeforeCreate(SysUserCreateDto createDto) {
        String account = createDto.getAccount();
        String password = createDto.getPassword();
        String checkPassword = createDto.getCheckPassword();
        boolean isRegister = createDto.isRegister();
        if (isRegister && !password.equals(checkPassword)) {
            throw LoggerUtil.logAetherValidError(log, "??????[account={}]?????????????????????????????????", account);
        }
        if (!isRegister) {
            // ?????????????????????????????????
            createDto.setPassword(getDefaultPassword());
        }
        Wrapper<SysUser> wrapper = new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getAccount, account);
        boolean exists = mapper.exists(wrapper);
        if (exists) {
            throw LoggerUtil.logAetherValidError(log, "??????[account={}]???????????????????????????????????????", account);
        }
        // ??????????????????
        DictTool.verifyDictLegitimacy(createDto);
    }

    /**
     * <p>???????????????</p>
     *
     * @param dto ????????????
     * @author guocq
     * @date 2023/1/5 17:30
     */
    private void checkBeforeUpdate(SysUserUpdateDto dto) {
        Long id = dto.getId();
        SysUser sysUser = mapper.selectById(id);
        if (sysUser == null) {
            throw LoggerUtil.logAetherValidError(log, "??????[id={}]???????????????????????????", id);
        }
        String account = dto.getAccount();
        if (StrUtil.isNotBlank(account)) {
            Wrapper<SysUser> wrapper = new LambdaQueryWrapper<SysUser>()
                    .eq(SysUser::getAccount, account)
                    .ne(SysUser::getId, id);
            boolean exists = mapper.exists(wrapper);
            if (exists) {
                throw LoggerUtil.logAetherValidError(log, "???????????????[account={}]???????????????????????????????????????", account);
            }
        }
        // ???????????????????????????????????????????????????
        dto.setPassword(sysUser.getPassword());
        // ??????????????????
        DictTool.verifyDictLegitimacy(dto);
    }

    /**
     * <p>???????????????????????????</p>
     *
     * @param idSet ????????????
     * @author guocq
     * @date 2023/1/5 17:32
     */
    private void checkBeforeDelete(Set<Long> idSet) {
        if (CollUtil.isEmpty(idSet)) {
            throw LoggerUtil.logAetherValidError(log, "?????????????????????????????????", idSet);
        }
        Wrapper<SysUser> wrapper = new LambdaQueryWrapper<SysUser>()
                .in(SysUser::getId, idSet);
        Long count = mapper.selectCount(wrapper);
        int size = idSet.size();
        if (count == 0) {
            throw LoggerUtil.logAetherValidError(log, "??????????????????????????????[idSet={}]", idSet);
        }
        if (count < size) {
            log.warn("????????????idSet={}????????????????????????????????????????????????????????????????????????{}???", idSet, count);
        }
    }

    /**
     * <p>?????????????????????</p>
     *
     * @param dto ??????
     * @author guocq
     * @date 2023/1/9 9:28
     */
    private SysUser checkBeforeChangePassword(SysUserChangePasswordDto dto) {
        String account = dto.getAccount();
        SysUser sysUser = checkAccountIsExist(account);
        if (!dto.getPassword().equals(dto.getCheckPassword())) {
            throw LoggerUtil.logAetherValidError(log, "??????[account={}]???????????????????????????????????????", account);
        }
        return sysUser;
    }

    /**
     * <p>????????????????????????</p>
     *
     * @param account ????????????
     * @author guocq
     * @date 2023/1/9 9:27
     */
    private SysUser checkAccountIsExist(String account) {
        Wrapper<SysUser> wrapper = new LambdaQueryWrapper<SysUser>()
                .in(SysUser::getAccount, account);
        SysUser exists = mapper.selectOne(wrapper);
        if (exists == null) {
            throw LoggerUtil.logAetherValidError(log, "??????[{}]?????????", account);
        }
        return exists;
    }

    /**
     * <p>????????????????????????</p>
     *
     * @param dto ??????
     * @author guocq
     * @date 2023/1/9 10:27
     */
    private void checkBeforeGrantRoleToUser(GrantRoleToUserDto dto) {
        Long id = dto.getId();
        SysUser sysUser = mapper.selectById(id);
        if (sysUser == null) {
            throw LoggerUtil.logAetherError(log, "??????[id={}]?????????", id);
        }
        Integer enableStatus = sysUser.getEnableStatus();
        if (!SystemConstantPool.ENABLE_STATUS_ENABLE.equals(enableStatus)) {
            throw LoggerUtil.logAetherError(log, "????????????[id={},account={}]???????????????????????????", id, sysUser.getAccount());
        }
        Set<Long> roleId = dto.getRoleId();
        Wrapper<SysRole> wrapper = new LambdaQueryWrapper<SysRole>()
                .select(SysRole::getId)
                .in(SysRole::getId, roleId);
        Set<Long> roleIdFormDb = sysRoleMapper.selectList(wrapper).stream().map(SysRole::getId).collect(Collectors.toSet());
        if (CollUtil.isEmpty(roleIdFormDb)) {
            throw LoggerUtil.logAetherError(log, "??????[{}]?????????", roleId);
        }
        if (roleIdFormDb.size() < roleId.size()) {
            log.warn("??????????????????id??????[{}]??????????????????????????????????????????????????????????????????[{}]", roleId, roleIdFormDb);
            dto.setRoleId(roleIdFormDb);
        }
    }

    /**
     * <p>??????????????????</p>
     *
     * @return java.lang.String
     * @author guocq
     * @date 2023/1/9 14:38
     */
    private String getDefaultPassword() {
        return sysParamFacade.findParamByParamCode(SystemConstantPool.PARAM_DEFAULT_PASSWORD).map(SysParamHolders::getParamValue)
                .orElse("abc123456");
    }
}
