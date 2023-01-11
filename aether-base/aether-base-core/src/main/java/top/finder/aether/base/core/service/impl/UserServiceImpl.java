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
import top.finder.aether.base.api.dto.UserCreateDto;
import top.finder.aether.base.api.facade.ParamFacade;
import top.finder.aether.base.api.model.ParamModel;
import top.finder.aether.base.api.support.pool.BaseConstantPool;
import top.finder.aether.base.api.tools.DictTool;
import top.finder.aether.base.api.vo.UserVo;
import top.finder.aether.base.core.dto.GrantRoleToUserDto;
import top.finder.aether.base.core.dto.UserChangePasswordDto;
import top.finder.aether.base.core.dto.UserPageQueryDto;
import top.finder.aether.base.core.dto.UserUpdateDto;
import top.finder.aether.base.core.entity.Role;
import top.finder.aether.base.core.entity.User;
import top.finder.aether.base.core.mapper.RoleMapper;
import top.finder.aether.base.core.mapper.UserMapper;
import top.finder.aether.base.core.service.UserService;
import top.finder.aether.common.support.annotation.BlockBean;
import top.finder.aether.common.support.annotation.BlockMethod;
import top.finder.aether.common.support.exception.AetherException;
import top.finder.aether.common.support.exception.AetherValidException;
import top.finder.aether.common.support.helper.TransformerHelper;
import top.finder.aether.common.support.strategy.CryptoStrategy;
import top.finder.aether.common.support.strategy.Md5SaltCrypto;
import top.finder.aether.common.utils.Loggers;
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
public class UserServiceImpl implements UserService {
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Resource
    private UserMapper userMapper;
    @Resource
    private RoleMapper roleMapper;
    private CryptoStrategy defaultCryptoStrategy;
    private ParamFacade paramFacade;

    @Autowired
    public void setCryptoStrategy(@Qualifier("md5SaltCrypto") CryptoStrategy cryptoStrategy) {
        this.defaultCryptoStrategy = cryptoStrategy;
    }

    @Autowired
    public void setParamFacade(ParamFacade paramFacade) {
        this.paramFacade = paramFacade;
    }

    /**
     * <p>根据账户信息加载用户</p>
     *
     * @param account  登陆账户
     * @param password 传入需要验证的密码
     * @return {@link UserVo}
     * @author guocq
     * @date 2022/12/28 14:55
     */
    @Override
    public UserVo loadUser(String account, String password) {
        // 防止内部调用缓存失效
        UserVo userVo = SpringUtil.getBean(UserService.class).findUserByAccount(account);
        if (userVo == null) {
            return UserVo.emptyUser(account);
        }
        boolean match = match(account, password, userVo.getPassword());
        userVo.setCertified(match);
        userVo.setPassword(null);
        return userVo;
    }

    /**
     * <p>根据用户名查询用户</p>
     *
     * @param account 用户名
     * @return {@link UserVo }
     * @author guocq
     * @date 2022/12/28 16:08
     */
    @Cacheable(cacheNames = "AMS:USER:SINGLE", key = "'ACCOUNT:' + #account")
    public UserVo findUserByAccount(String account) {
        log.debug("根据[account={}]查询用户信息", account);
        Wrapper<User> wrapper = new LambdaQueryWrapper<User>()
                .eq(User::getAccount, account);
        User user = userMapper.selectOne(wrapper);
        if (ObjectUtil.isNull(user) || ObjectUtil.isNull(user.getId())) {
            log.error("未找到用户account={}", account);
            return null;
        }
        return DictTool.transformerAndTranslate(user, UserVo.class);
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
    public void create(UserCreateDto dto) {
        log.debug("新增用户信息, 入参={}", dto);
        checkBeforeCreate(dto);
        User user = TransformerHelper.transformer(dto, User.class);
        user.setPassword(encrypt(user.getAccount(), dto.getPassword()));
        userMapper.insert(user);
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
    public void update(UserUpdateDto dto) {
        log.debug("更新用户信息, 入参={}", dto);
        checkBeforeUpdate(dto);
        User user = TransformerHelper.transformer(dto, User.class);
        userMapper.updateById(user);
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
        userMapper.logicBatchDeleteByIds(idSet, System.currentTimeMillis());
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
    public IPage<UserVo> pageQuery(UserPageQueryDto dto) {
        log.debug("分页查询用户,入参={}", dto);
        IPage<User> page = PageHelper.initPage(dto);
        Wrapper<User> wrapper = new LambdaQueryWrapper<User>()
                .eq(ObjectUtil.isNotNull(dto.getId()), User::getId, dto.getId())
                .like(StrUtil.isNotBlank(dto.getAccount()), User::getAccount, dto.getAccount())
                .like(StrUtil.isNotBlank(dto.getNickname()), User::getNickname, dto.getNickname())
                .in(CollUtil.isNotEmpty(dto.getSexSet()), User::getSex, dto.getSexSet())
                .ge(ObjectUtil.isNotEmpty(dto.getBirthdayStarter()), User::getBirthday, dto.getBirthdayStarter())
                .le(ObjectUtil.isNotEmpty(dto.getBirthdayEnd()), User::getBirthday, dto.getBirthdayEnd())
                .in(CollUtil.isNotEmpty(dto.getUserTypeSet()), User::getUserType, dto.getUserTypeSet())
                .orderByDesc(User::getGmtModify)
                .orderByDesc(User::getGmtCreate);
        IPage<User> rawPage = userMapper.selectPage(page, wrapper);
        return rawPage.convert(record -> DictTool.transformerAndTranslate(record, UserVo.class));
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
    public void changePassword(UserChangePasswordDto dto) {
        log.debug("用户修改密码,入参={}", dto);
        User user = checkBeforeChangePassword(dto);
        String account = dto.getAccount();
        String password = dto.getPassword();
        password = encrypt(account, password);
        Wrapper<User> wrapper = new LambdaUpdateWrapper<User>()
                .eq(User::getAccount, account)
                .set(User::getPassword, password);
        userMapper.update(new User(account, password), wrapper);
        log.debug("用户修改密码成功");
        SecurityFacade.kickOut(user.getId());
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
        User user = checkAccountIsExist(account);
        user.setPassword(encrypt(account, getDefaultPassword()));
        user.setEnableStatus(BaseConstantPool.ENABLE_STATUS_ENABLE);
        Wrapper<User> wrapper = new LambdaUpdateWrapper<User>()
                .eq(User::getAccount, account)
                .set(User::getPassword, user.getPassword())
                .set(User::getEnableStatus, BaseConstantPool.ENABLE_STATUS_ENABLE);
        userMapper.update(user, wrapper);
        log.debug("用户重置成功");
        SecurityFacade.kickOut(user.getId());
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
            log.error("enableStatus={}不合法", enableStatus);
            throw new AetherValidException("enableStatus不合法");
        }
        log.debug("{}用户, 入参[account={},enableStatus={}]", typeMessage, account, enableStatus);
        User user = checkAccountIsExist(account);
        if (ObjectUtil.equals(enableStatus, user.getEnableStatus())) {
            log.warn("用户[account={}]已被{},将不再次更新", account, typeMessage);
            return;
        }
        user.setEnableStatus(enableStatus);
        Wrapper<User> wrapper = new LambdaUpdateWrapper<User>()
                .eq(User::getAccount, account)
                .set(User::getEnableStatus, enableStatus);
        userMapper.update(user, wrapper);
        log.debug("{}用户成功", typeMessage);
        SecurityFacade.kickOut(user.getId());
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
        userMapper.unbindRoleOfUser(id);
        userMapper.bindRoleOfUser(id, dto.getRoleId());
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
    public void systemInnerUserCreate(UserCreateDto dto) {
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
    public void registeredUserCreate(UserCreateDto dto) {
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
        Optional<ParamModel> optional = paramFacade.findParamByParamCode(BaseConstantPool.PARAM_DEFAULT_USER_PASSWORD_CRYPTO_STRATEGY);
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
    private void checkBeforeCreate(UserCreateDto createDto) {
        String account = createDto.getAccount();
        String password = createDto.getPassword();
        String checkPassword = createDto.getCheckPassword();
        boolean isRegister = createDto.isRegister();
        if (isRegister && !password.equals(checkPassword)) {
            Loggers.logAetherValidError(log, "用户[account={}]两次密码不一致无法新增", account);
        }
        if (!isRegister) {
            // 非注册接口赋予默认密码
            createDto.setPassword(getDefaultPassword());
        }
        Wrapper<User> wrapper = new LambdaQueryWrapper<User>()
                .eq(User::getAccount, account);
        boolean exists = userMapper.exists(wrapper);
        if (exists) {
            Loggers.logAetherValidError(log, "用户[account={}]的数据已存在，不能重复新增", account);
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
    private void checkBeforeUpdate(UserUpdateDto dto) {
        Long id = dto.getId();
        User user = userMapper.selectById(id);
        if (user == null) {
            Loggers.logAetherValidError(log, "用户[id={}]不存在无法更新数据", id);
        }
        String account = dto.getAccount();
        if (StrUtil.isNotBlank(account)) {
            Wrapper<User> wrapper = new LambdaQueryWrapper<User>()
                    .eq(User::getAccount, account)
                    .ne(User::getId, id);
            boolean exists = userMapper.exists(wrapper);
            if (exists) {
                Loggers.logAetherValidError(log, "用户账户为[account={}]的数据已存在，不能重复更新", account);
            }
        }
        // 用户信息修改时不能直接修改用户密码
        dto.setPassword(user.getPassword());
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
            Loggers.logAetherValidError(log, "删除时主键集合不能为空", idSet);
        }
        Wrapper<User> wrapper = new LambdaQueryWrapper<User>()
                .in(User::getId, idSet);
        Long count = userMapper.selectCount(wrapper);
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
    private User checkBeforeChangePassword(UserChangePasswordDto dto) {
        String account = dto.getAccount();
        User user = checkAccountIsExist(account);
        if (!dto.getPassword().equals(dto.getCheckPassword())) {
            log.error("用户[account={}]两次密码不一致无法修改密码", account);
            throw new AetherException("两次密码不一致无法修改密码");
        }
        return user;
    }

    /**
     * <p>检查账户是否存在</p>
     *
     * @param account 账户信息
     * @author guocq
     * @date 2023/1/9 9:27
     */
    private User checkAccountIsExist(String account) {
        Wrapper<User> wrapper = new LambdaQueryWrapper<User>()
                .in(User::getAccount, account);
        User exists = userMapper.selectOne(wrapper);
        if (exists == null) {
            log.error("账户[{}]不存在", account);
            throw new AetherValidException("账户不存在");
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
        User user = userMapper.selectById(id);
        if (user == null) {
            log.error("用户[id={}]不存在", id);
            throw new AetherValidException("用户不存在");
        }
        Integer enableStatus = user.getEnableStatus();
        if (!BaseConstantPool.ENABLE_STATUS_ENABLE.equals(enableStatus)) {
            log.error("当前用户[id={},account={}]已被禁用，无法授权", id, user.getAccount());
            throw new AetherValidException(StrUtil.format("当前用户[id={},account={}]已被禁用，无法授权", id, user.getAccount()));
        }
        Set<Long> roleId = dto.getRoleId();
        Wrapper<Role> wrapper = new LambdaQueryWrapper<Role>()
                .select(Role::getId)
                .in(Role::getId, roleId);
        Set<Long> roleIdFormDb = roleMapper.selectList(wrapper).stream().map(Role::getId).collect(Collectors.toSet());
        if (CollUtil.isEmpty(roleIdFormDb)) {
            log.error("角色[{}]不存在", roleId);
            throw new AetherValidException("角色不存在");
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
        return paramFacade.findParamByParamCode(BaseConstantPool.PARAM_DEFAULT_PASSWORD).map(ParamModel::getParamValue)
                .orElse("abc123456");
    }
}
