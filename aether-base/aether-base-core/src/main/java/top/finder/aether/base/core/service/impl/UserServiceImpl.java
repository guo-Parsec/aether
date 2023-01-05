package top.finder.aether.base.core.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
import top.finder.aether.base.api.support.helper.DictHelper;
import top.finder.aether.base.api.support.pool.BaseConstantPool;
import top.finder.aether.base.api.vo.UserVo;
import top.finder.aether.base.core.dto.UserPageQueryDto;
import top.finder.aether.base.core.dto.UserUpdateDto;
import top.finder.aether.base.core.entity.User;
import top.finder.aether.base.core.mapper.UserMapper;
import top.finder.aether.base.core.service.UserService;
import top.finder.aether.common.support.helper.CodeHelper;
import top.finder.aether.common.support.helper.SpringBeanHelper;
import top.finder.aether.common.support.helper.TransformerHelper;
import top.finder.aether.common.support.strategy.CryptoStrategy;
import top.finder.aether.common.support.strategy.Md5SaltCrypto;
import top.finder.aether.data.core.support.helper.PageHelper;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Set;

/**
 * <p>用户操作业务实现类</p>
 *
 * @author guocq
 * @since 2022/12/14
 */
@Service(value = "userService")
public class UserServiceImpl implements UserService {
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Resource
    private UserMapper userMapper;
    private CryptoStrategy defaultCryptoStrategy;

    @Autowired
    public void setCryptoStrategy(@Qualifier("md5SaltCrypto") CryptoStrategy cryptoStrategy) {
        this.defaultCryptoStrategy = cryptoStrategy;
    }

    /**
     * <p>根据id查询用户数据</p>
     *
     * @param id 主键
     * @return {@link UserVo}
     * @author guocq
     * @date 2022/12/14 9:48
     */
    @Override
    @Cacheable(cacheNames = "USER:SINGLE", key = "'ID:' + #id")
    public UserVo findById(Long id) {
        User user = userMapper.findById(id);
        UserVo userVo = TransformerHelper.transformer(user, UserVo.class);
        DictHelper.translate(userVo);
        return userVo;
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
        UserVo userVo = SpringBeanHelper.getBean(UserService.class).findUserByAccount(account);
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
        UserVo userVo = TransformerHelper.transformer(user, UserVo.class);
        DictHelper.translate(userVo);
        return userVo;
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
        return rawPage.convert(record -> DictHelper.transformerAndTranslate(record, UserVo.class));
    }

    /**
     * <p>系统内部用户创建</p>
     *
     * @param dto 新增用户参数
     * @author guocq
     * @date 2023/1/5 14:58
     */
    @Override
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
        if (defaultCryptoStrategy.isOptionNeed()) {
            Map<String, Object> option = Maps.newHashMapWithExpectedSize(1);
            option.put(Md5SaltCrypto.OPTION_KEY_SALT, account);
            return defaultCryptoStrategy.match(password, dbPassword, option);
        }
        return defaultCryptoStrategy.match(password, dbPassword);
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
        if (defaultCryptoStrategy.isOptionNeed()) {
            Map<String, Object> option = Maps.newHashMapWithExpectedSize(1);
            option.put(Md5SaltCrypto.OPTION_KEY_SALT, account);
            return defaultCryptoStrategy.encrypt(rawPassword, option);
        }
        return defaultCryptoStrategy.encrypt(rawPassword);
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
        if (!password.equals(checkPassword)) {
            CodeHelper.logAetherValidError(log, "用户[account={}]两次密码不一致无法新增", account);
        }
        Wrapper<User> wrapper = new LambdaQueryWrapper<User>()
                .eq(User::getAccount, account);
        boolean exists = userMapper.exists(wrapper);
        if (exists) {
            CodeHelper.logAetherValidError(log, "用户[account={}]的数据已存在，不能重复新增", account);
        }
        // 校验字典信息
        DictHelper.verifyDictLegitimacy(createDto);
    }

    /**
     * <p>更新前校验</p>
     *
     * @param dto 更新入参
     * @return void
     * @author guocq
     * @date 2023/1/5 17:30
     */
    private void checkBeforeUpdate(UserUpdateDto dto) {
        Long id = dto.getId();
        User user = userMapper.selectById(id);
        if (user == null) {
            CodeHelper.logAetherValidError(log, "用户[id={}]不存在无法更新数据", id);
        }
        String account = dto.getAccount();
        if (StrUtil.isNotBlank(account)) {
            Wrapper<User> wrapper = new LambdaQueryWrapper<User>()
                    .eq(User::getAccount, account)
                    .ne(User::getId, id);
            boolean exists = userMapper.exists(wrapper);
            if (exists) {
                CodeHelper.logAetherValidError(log, "用户账户为[account={}]的数据已存在，不能重复更新", account);
            }
        }
        // 用户信息修改时不能直接修改用户密码
        dto.setPassword(user.getPassword());
        // 校验字典信息
        DictHelper.verifyDictLegitimacy(dto);
    }

    /**
     * <p>删除用户信息前校验</p>
     *
     * @param idSet 主键集合
     * @return void
     * @author guocq
     * @date 2023/1/5 17:32
     */
    private void checkBeforeDelete(Set<Long> idSet) {
        if (CollUtil.isEmpty(idSet)) {
            CodeHelper.logAetherValidError(log, "删除时主键集合不能为空", idSet);
        }
        Wrapper<User> wrapper = new LambdaQueryWrapper<User>()
                .in(User::getId, idSet);
        Long count = userMapper.selectCount(wrapper);
        int size = idSet.size();
        if (count < size) {
            log.warn("待删除的idSet={}中部分主键不存在无法删除，系统将删除已存在的数据{}条", idSet, count);
        }
    }
}
