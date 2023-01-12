package top.finder.aether.base.core.service.impl;

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
import top.finder.aether.base.api.dto.SysUserCreateDto;
import top.finder.aether.base.api.facade.SysParamFacade;
import top.finder.aether.base.api.holders.SysParamHolders;
import top.finder.aether.base.api.support.pool.BaseConstantPool;
import top.finder.aether.base.api.tools.DictTool;
import top.finder.aether.base.api.vo.SysUserVo;
import top.finder.aether.base.core.dto.GrantRoleToUserDto;
import top.finder.aether.base.core.dto.SysUserChangePasswordDto;
import top.finder.aether.base.core.dto.SysSysUserPageQueryDto;
import top.finder.aether.base.core.dto.SysUserUpdateDto;
import top.finder.aether.base.core.entity.SysRole;
import top.finder.aether.base.core.entity.SysUser;
import top.finder.aether.base.core.mapper.SysRoleMapper;
import top.finder.aether.base.core.mapper.SysUserMapper;
import top.finder.aether.base.core.service.SysUserService;
import top.finder.aether.common.support.annotation.BlockBean;
import top.finder.aether.common.support.annotation.BlockMethod;
import top.finder.aether.common.support.helper.TransformerHelper;
import top.finder.aether.common.support.strategy.CryptoStrategy;
import top.finder.aether.common.support.strategy.Md5SaltCrypto;
import top.finder.aether.common.utils.LoggerUtil;
import top.finder.aether.data.core.entity.UserDetails;
import top.finder.aether.data.core.support.helper.PageHelper;
import top.finder.aether.security.api.facade.SecurityFacade;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>用户操作业务实现类</p>
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

    @Autowired
    public void setCryptoStrategy(@Qualifier("md5SaltCrypto") CryptoStrategy cryptoStrategy) {
        this.defaultCryptoStrategy = cryptoStrategy;
    }

    @Autowired
    public void setParamFacade(SysParamFacade sysParamFacade) {
        this.sysParamFacade = sysParamFacade;
    }

    /**
     * <p>根据账户信息加载用户</p>
     *
     * @param account  登陆账户
     * @param password 传入需要验证的密码
     * @return {@link UserDetails}
     * @author guocq
     * @date 2022/12/28 14:55
     */
    @Override
    public UserDetails loadUser(String account, String password) {
        // 防止内部调用缓存失效
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
     * <p>根据用户名查询用户</p>
     *
     * @param account 用户名
     * @return {@link SysUserVo }
     * @author guocq
     * @date 2022/12/28 16:08
     */
    @Cacheable(cacheNames = "AMS:USER:SINGLE", key = "'ACCOUNT:' + #account")
    public UserDetails findUserByAccount(String account) {
        log.debug("根据[account={}]查询用户信息", account);
        Wrapper<SysUser> wrapper = new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getAccount, account);
        SysUser sysUser = mapper.selectOne(wrapper);
        if (ObjectUtil.isNull(sysUser) || ObjectUtil.isNull(sysUser.getId())) {
            log.error("未找到用户account={}", account);
            return null;
        }
        return DictTool.transformerAndTranslate(sysUser, UserDetails.class);
    }

    /**
     * <p>新增：用户信息</p>
     *
     * @param dto 新增用户
     * @author guocq
     * @date 2023/1/5 11:02
     */
    @Override
    @CacheEvict(cacheNames = {"AMS:USER:SINGLE", "AMS:USER:LIST"}, allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void create(SysUserCreateDto dto) {
        log.debug("新增用户信息, 入参={}", dto);
        checkBeforeCreate(dto);
        SysUser sysUser = TransformerHelper.transformer(dto, SysUser.class);
        sysUser.setPassword(encrypt(sysUser.getAccount(), dto.getPassword()));
        mapper.insert(sysUser);
        // todo 默认角色赋予
        log.debug("新增用户成功");
    }

    /**
     * <p>更新：用户信息</p>
     *
     * @param dto 更新用户
     * @author guocq
     * @date 2023/1/5 11:02
     */
    @Override
    @CacheEvict(cacheNames = {"AMS:USER:SINGLE", "AMS:USER:LIST"}, allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update(SysUserUpdateDto dto) {
        log.debug("更新用户信息, 入参={}", dto);
        checkBeforeUpdate(dto);
        SysUser sysUser = TransformerHelper.transformer(dto, SysUser.class);
        mapper.updateById(sysUser);
        log.debug("更新用户信息成功");
    }

    /**
     * <p>批量删除：用户信息</p>
     *
     * @param idSet 主键集合
     * @author guocq
     * @date 2023/1/5 11:02
     */
    @Override
    @CacheEvict(cacheNames = {"AMS:USER:SINGLE", "AMS:USER:LIST"}, allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<Long> idSet) {
        log.debug("删除用户信息, 入参={}", idSet);
        checkBeforeDelete(idSet);
        mapper.logicBatchDeleteByIds(idSet, System.currentTimeMillis());
        log.debug("删除用户信息成功");
    }

    /**
     * <p>分页查询用户</p>
     *
     * @param dto 分页参数
     * @return {@link IPage <UserVo>}
     * @author guocq
     * @date 2023/1/5 11:04
     */
    @Override
    public IPage<SysUserVo> pageQuery(SysSysUserPageQueryDto dto) {
        log.debug("分页查询用户,入参={}", dto);
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
        return rawPage.convert(record -> DictTool.transformerAndTranslate(record, SysUserVo.class));
    }

    /**
     * <p>用户修改密码</p>
     *
     * @param dto 参数
     * @author guocq
     * @date 2023/1/9 9:24
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void changePassword(SysUserChangePasswordDto dto) {
        log.debug("用户修改密码,入参={}", dto);
        SysUser sysUser = checkBeforeChangePassword(dto);
        String account = dto.getAccount();
        String password = dto.getPassword();
        password = encrypt(account, password);
        Wrapper<SysUser> wrapper = new LambdaUpdateWrapper<SysUser>()
                .eq(SysUser::getAccount, account)
                .set(SysUser::getPassword, password);
        mapper.update(new SysUser(account, password), wrapper);
        log.debug("用户修改密码成功");
        SecurityFacade.kickOut(sysUser.getId());
    }

    /**
     * <p>重置用户</p>
     *
     * @param account 账户信息
     * @author guocq
     * @date 2023/1/9 9:41
     */
    @Override
    @CacheEvict(cacheNames = {"AMS:USER:SINGLE", "AMS:USER:LIST"}, allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void resetUser(String account) {
        log.debug("重置用户, 入参={}", account);
        SysUser sysUser = checkAccountIsExist(account);
        sysUser.setPassword(encrypt(account, getDefaultPassword()));
        sysUser.setEnableStatus(BaseConstantPool.ENABLE_STATUS_ENABLE);
        Wrapper<SysUser> wrapper = new LambdaUpdateWrapper<SysUser>()
                .eq(SysUser::getAccount, account)
                .set(SysUser::getPassword, sysUser.getPassword())
                .set(SysUser::getEnableStatus, BaseConstantPool.ENABLE_STATUS_ENABLE);
        mapper.update(sysUser, wrapper);
        log.debug("用户重置成功");
        SecurityFacade.kickOut(sysUser.getId());
    }

    /**
     * <p>修改用户启用状态</p>
     *
     * @param account      账户
     * @param enableStatus 启用状态
     * @author guocq
     * @date 2023/1/9 9:50
     */
    @Override
    @CacheEvict(cacheNames = {"AMS:USER:SINGLE", "AMS:USER:LIST"}, allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void changeUserEnableStatus(String account, Integer enableStatus) {
        String typeMessage = null;
        if (BaseConstantPool.ENABLE_STATUS_ENABLE.equals(enableStatus)) {
            typeMessage = "启用";
        }
        if (BaseConstantPool.ENABLE_STATUS_DISABLE.equals(enableStatus)) {
            typeMessage = "禁用";
        }
        if (typeMessage == null) {
            throw LoggerUtil.logAetherValidError(log, "enableStatus={}不合法", enableStatus);
        }
        log.debug("{}用户, 入参[account={},enableStatus={}]", typeMessage, account, enableStatus);
        SysUser sysUser = checkAccountIsExist(account);
        if (ObjectUtil.equals(enableStatus, sysUser.getEnableStatus())) {
            log.warn("用户[account={}]已被{},将不再次更新", account, typeMessage);
            return;
        }
        sysUser.setEnableStatus(enableStatus);
        Wrapper<SysUser> wrapper = new LambdaUpdateWrapper<SysUser>()
                .eq(SysUser::getAccount, account)
                .set(SysUser::getEnableStatus, enableStatus);
        mapper.update(sysUser, wrapper);
        log.debug("{}用户成功", typeMessage);
        SecurityFacade.kickOut(sysUser.getId());
    }

    /**
     * <p>为用户赋予角色</p>
     *
     * @param dto 入参
     * @author guocq
     * @date 2023/1/9 10:14
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void grantRoleToUser(GrantRoleToUserDto dto) {
        log.debug("为用户赋予角色,入参={}", dto);
        checkBeforeGrantRoleToUser(dto);
        Long id = dto.getId();
        mapper.unbindRoleOfUser(id);
        mapper.bindRoleOfUser(id, dto.getRoleId());
        log.debug("为用户赋予角色成功");
        SecurityFacade.kickOut(id);
    }

    /**
     * <p>系统内部用户创建</p>
     *
     * @param dto 新增用户参数
     * @author guocq
     * @date 2023/1/5 14:58
     */
    @Override
    @BlockMethod(value = "systemInnerUserCreate")
    public void systemInnerUserCreate(SysUserCreateDto dto) {
        dto.setUserType(BaseConstantPool.USER_TYPE_INNER);
    }

    /**
     * <p>注册用户创建</p>
     *
     * @param dto 新增用户参数
     * @author guocq
     * @date 2023/1/5 14:58
     */
    @Override
    @BlockMethod(value = "registeredUserCreate")
    public void registeredUserCreate(SysUserCreateDto dto) {
        dto.setUserType(BaseConstantPool.USER_TYPE_REGISTERED);
    }

    /**
     * <p>判断用户密码是否匹配</p>
     *
     * @param account    账户信息
     * @param password   传入的密码
     * @param dbPassword 数据库存储的密码
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
     * <p>密码加密存储</p>
     *
     * @param account     账户信息
     * @param rawPassword 原始密码
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
     * <p>获取默认策略其</p>
     *
     * @return {@link CryptoStrategy}
     * @author guocq
     * @date 2023/1/9 15:25
     */
    private CryptoStrategy findDefaultCryptoStrategy() {
        Optional<SysParamHolders> optional = sysParamFacade.findParamByParamCode(BaseConstantPool.PARAM_DEFAULT_USER_PASSWORD_CRYPTO_STRATEGY);
        if (!optional.isPresent()) {
            return defaultCryptoStrategy;
        }
        String paramValue = optional.get().getParamValue();
        try {
            return SpringUtil.getBean(paramValue, CryptoStrategy.class);
        } catch (Exception e) {
            log.debug("获取[strategy={}]失败，返回默认策略器", paramValue);
            return defaultCryptoStrategy;
        }
    }

    /**
     * <p>新增用户信息前校验</p>
     *
     * @param createDto 新增入参
     * @author guocq
     * @date 2022/12/27 10:30
     */
    private void checkBeforeCreate(SysUserCreateDto createDto) {
        String account = createDto.getAccount();
        String password = createDto.getPassword();
        String checkPassword = createDto.getCheckPassword();
        boolean isRegister = createDto.isRegister();
        if (isRegister && !password.equals(checkPassword)) {
            throw LoggerUtil.logAetherValidError(log, "用户[account={}]两次密码不一致无法新增", account);
        }
        if (!isRegister) {
            // 非注册接口赋予默认密码
            createDto.setPassword(getDefaultPassword());
        }
        Wrapper<SysUser> wrapper = new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getAccount, account);
        boolean exists = mapper.exists(wrapper);
        if (exists) {
            throw LoggerUtil.logAetherValidError(log, "用户[account={}]的数据已存在，不能重复新增", account);
        }
        // 校验字典信息
        DictTool.verifyDictLegitimacy(createDto);
    }

    /**
     * <p>更新前校验</p>
     *
     * @param dto 更新入参
     * @author guocq
     * @date 2023/1/5 17:30
     */
    private void checkBeforeUpdate(SysUserUpdateDto dto) {
        Long id = dto.getId();
        SysUser sysUser = mapper.selectById(id);
        if (sysUser == null) {
            throw LoggerUtil.logAetherValidError(log, "用户[id={}]不存在无法更新数据", id);
        }
        String account = dto.getAccount();
        if (StrUtil.isNotBlank(account)) {
            Wrapper<SysUser> wrapper = new LambdaQueryWrapper<SysUser>()
                    .eq(SysUser::getAccount, account)
                    .ne(SysUser::getId, id);
            boolean exists = mapper.exists(wrapper);
            if (exists) {
                throw LoggerUtil.logAetherValidError(log, "用户账户为[account={}]的数据已存在，不能重复更新", account);
            }
        }
        // 用户信息修改时不能直接修改用户密码
        dto.setPassword(sysUser.getPassword());
        // 校验字典信息
        DictTool.verifyDictLegitimacy(dto);
    }

    /**
     * <p>删除用户信息前校验</p>
     *
     * @param idSet 主键集合
     * @author guocq
     * @date 2023/1/5 17:32
     */
    private void checkBeforeDelete(Set<Long> idSet) {
        if (CollUtil.isEmpty(idSet)) {
            throw LoggerUtil.logAetherValidError(log, "删除时主键集合不能为空", idSet);
        }
        Wrapper<SysUser> wrapper = new LambdaQueryWrapper<SysUser>()
                .in(SysUser::getId, idSet);
        Long count = mapper.selectCount(wrapper);
        int size = idSet.size();
        if (count < size) {
            log.warn("待删除的idSet={}中部分主键不存在无法删除，系统将删除已存在的数据{}条", idSet, count);
        }
    }

    /**
     * <p>修改密码前校验</p>
     *
     * @param dto 入参
     * @author guocq
     * @date 2023/1/9 9:28
     */
    private SysUser checkBeforeChangePassword(SysUserChangePasswordDto dto) {
        String account = dto.getAccount();
        SysUser sysUser = checkAccountIsExist(account);
        if (!dto.getPassword().equals(dto.getCheckPassword())) {
            throw LoggerUtil.logAetherValidError(log, "用户[account={}]两次密码不一致无法修改密码", account);
        }
        return sysUser;
    }

    /**
     * <p>检查账户是否存在</p>
     *
     * @param account 账户信息
     * @author guocq
     * @date 2023/1/9 9:27
     */
    private SysUser checkAccountIsExist(String account) {
        Wrapper<SysUser> wrapper = new LambdaQueryWrapper<SysUser>()
                .in(SysUser::getAccount, account);
        SysUser exists = mapper.selectOne(wrapper);
        if (exists == null) {
            throw LoggerUtil.logAetherValidError(log, "账户[{}]不存在", account);
        }
        return exists;
    }

    /**
     * <p>为用户赋权前校验</p>
     *
     * @param dto 入参
     * @author guocq
     * @date 2023/1/9 10:27
     */
    private void checkBeforeGrantRoleToUser(GrantRoleToUserDto dto) {
        Long id = dto.getId();
        SysUser sysUser = mapper.selectById(id);
        if (sysUser == null) {
            throw LoggerUtil.logAetherError(log, "用户[id={}]不存在", id);
        }
        Integer enableStatus = sysUser.getEnableStatus();
        if (!BaseConstantPool.ENABLE_STATUS_ENABLE.equals(enableStatus)) {
            throw LoggerUtil.logAetherError(log, "当前用户[id={},account={}]已被禁用，无法授权", id, sysUser.getAccount());
        }
        Set<Long> roleId = dto.getRoleId();
        Wrapper<SysRole> wrapper = new LambdaQueryWrapper<SysRole>()
                .select(SysRole::getId)
                .in(SysRole::getId, roleId);
        Set<Long> roleIdFormDb = sysRoleMapper.selectList(wrapper).stream().map(SysRole::getId).collect(Collectors.toSet());
        if (CollUtil.isEmpty(roleIdFormDb)) {
            throw LoggerUtil.logAetherError(log, "角色[{}]不存在", roleId);
        }
        if (roleIdFormDb.size() < roleId.size()) {
            log.warn("当前传入角色id列表[{}]有部分数据不存在，系统默认只添加已存在的角色[{}]", roleId, roleIdFormDb);
            dto.setRoleId(roleIdFormDb);
        }
    }

    /**
     * <p>获取默认密码</p>
     *
     * @return java.lang.String
     * @author guocq
     * @date 2023/1/9 14:38
     */
    private String getDefaultPassword() {
        return sysParamFacade.findParamByParamCode(BaseConstantPool.PARAM_DEFAULT_PASSWORD).map(SysParamHolders::getParamValue)
                .orElse("abc123456");
    }
}
