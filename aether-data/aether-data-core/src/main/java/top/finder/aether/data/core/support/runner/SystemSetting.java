package top.finder.aether.data.core.support.runner;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import top.finder.aether.common.support.helper.EnvHelper;
import top.finder.aether.common.support.pool.CommonConstantPool;
import top.finder.aether.common.support.strategy.CryptoStrategy;
import top.finder.aether.data.cache.support.helper.RedisHelper;
import top.finder.aether.data.core.model.ApiModel;
import top.finder.aether.data.core.support.helper.SystemConfigHelper;

import java.util.List;
import java.util.Map;

import static top.finder.aether.common.support.pool.CommonConstantPool.FIRST_PRIORITY;
import static top.finder.aether.data.core.support.pool.SystemSettingConstantPool.API_MODELS;
import static top.finder.aether.data.core.support.pool.SystemSettingConstantPool.FEIGN_SECRET;

/**
 * <p>系统设置配置</p>
 *
 * @author guocq
 * @since 2023/1/4
 */
@Component(value = "systemSetting")
public class SystemSetting implements ApplicationRunner, DisposableBean, Ordered {
    private static final Logger log = LoggerFactory.getLogger(SystemSetting.class);
    private static final Map<String, String> DESTROY_SETTING_MAPPING = Maps.newConcurrentMap();

    private final RedisHelper redisHelper;

    private CryptoStrategy cryptoStrategy;

    public SystemSetting(RedisHelper redisHelper) {
        this.redisHelper = redisHelper;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        initFeignSecret();
        initApiModels();
    }

    /**
     * <p>应用初始化feign密钥</p>
     *
     * @author guocq
     * @date 2023/1/4 16:25
     */
    public void initFeignSecret() {
        String appName = EnvHelper.get(CommonConstantPool.APP_NAME_KEY);
        log.debug("系统[{}]开始初始化feign密钥", appName);
        final String systemSettingKey = SystemConfigHelper.generateSystemSettingKey(appName);
        final String secret = IdUtil.fastUUID();
        final String feignSecret = cryptoStrategy.encrypt(secret);
        log.debug("系统[{}]初始化feign密钥为[feignSecret={}]", appName, feignSecret);
        redisHelper.hashSet(systemSettingKey, FEIGN_SECRET, feignSecret);
        DESTROY_SETTING_MAPPING.put(systemSettingKey, FEIGN_SECRET);
    }

    public void initApiModels() {
        ApiRegister.register();
        String appName = EnvHelper.get(CommonConstantPool.APP_NAME_KEY);
        log.debug("系统[{}]开始初始化api", appName);
        final String systemSettingKey = SystemConfigHelper.generateSystemSettingKey(appName);
        List<ApiModel> apiModels = ApiRegister.collectApiModels();
        redisHelper.hashSet(systemSettingKey, API_MODELS, apiModels);
        DESTROY_SETTING_MAPPING.put(systemSettingKey, API_MODELS);
    }

    /**
     * <p>获取应用系统设置参数</p>
     *
     * @param appName 应用名
     * @param key     key值
     * @return java.lang.Object
     * @author guocq
     * @date 2023/1/4 16:19
     */
    public Object getAppSetting(String appName, String key) {
        return get(appName, key, null);
    }

    /**
     * <p>获取系统设置参数</p>
     *
     * @param key        key值
     * @param defaultVal 默认值
     * @return java.lang.Object
     * @author guocq
     * @date 2023/1/4 16:21
     */
    public Object get(String key, Object defaultVal) {
        return get(null, key, defaultVal);
    }

    /**
     * <p>获取系统设置参数</p>
     *
     * @param key key值
     * @return java.lang.Object
     * @author guocq
     * @date 2023/1/4 16:20
     */
    public Object get(String key) {
        return get(null, key, null);
    }

    /**
     * <p>获取应用系统设置参数，当该参数不存在时返回默认值</p>
     *
     * @param appName    应用名
     * @param key        key值
     * @param defaultVal 默认值
     * @return java.lang.Object
     * @author guocq
     * @date 2023/1/4 16:16
     */
    public Object get(String appName, String key, Object defaultVal) {
        String systemSettingKey = SystemConfigHelper.generateSystemSettingKey(appName);
        boolean hasKey = redisHelper.hasHashKey(systemSettingKey, key);
        if (hasKey) {
            return redisHelper.hashGet(systemSettingKey, key, Object.class);
        }
        log.warn("systemSettingKey-{}不存在", key);
        return defaultVal;
    }

    @Autowired
    public void setCryptoStrategy(@Qualifier("aesCrypto") CryptoStrategy cryptoStrategy) {
        this.cryptoStrategy = cryptoStrategy;
    }

    @Override
    public int getOrder() {
        return FIRST_PRIORITY;
    }

    @Override
    public void destroy() throws Exception {
        String appName = EnvHelper.get(CommonConstantPool.APP_NAME_KEY);
        log.info("系统[{}]正在关闭", appName);
        if (DESTROY_SETTING_MAPPING.isEmpty()) {
            return;
        }
        DESTROY_SETTING_MAPPING.forEach(this::destroy);
    }

    /**
     * <p>销毁配置</p>
     *
     * @param key     key
     * @param hashKey hashKey
     * @author guocq
     * @date 2023/1/4 16:30
     */
    private void destroy(String key, String hashKey) {
        if (StrUtil.hasBlank(key, hashKey)) {
            log.error("无法销毁[key={},hashKey={}]", key, hashKey);
            return;
        }
        if (CommonConstantPool.ALL_TEXT.equals(hashKey)) {
            log.info("key={}的配置信息将完全销毁", key);
            redisHelper.delete(key);
            return;
        }
        log.info("[key={},hashKey={}]的配置信息将完全销毁", key, hashKey);
        redisHelper.hashDelete(key, hashKey);
    }
}
