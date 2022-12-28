package top.finder.aether.base.core.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import top.finder.aether.base.api.support.helper.DictHelper;
import top.finder.aether.base.api.vo.UserVo;
import top.finder.aether.base.core.entity.User;
import top.finder.aether.base.core.mapper.UserMapper;
import top.finder.aether.base.core.service.UserService;
import top.finder.aether.common.support.helper.SpringBeanHelper;
import top.finder.aether.common.support.strategy.CryptoStrategy;
import top.finder.aether.common.support.strategy.Md5SaltCrypto;

import javax.annotation.Resource;
import java.util.Map;

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
        UserVo userVo = new UserVo();
        BeanUtil.copyProperties(user, userVo);
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
        UserVo userVo = new UserVo();
        BeanUtil.copyProperties(user, userVo);
        DictHelper.translate(userVo);
        return userVo;
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
}
