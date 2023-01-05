package top.finder.aether.data.core.support.helper;

import cn.hutool.core.util.StrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.finder.aether.data.cache.support.helper.RedisHelper;

import static top.finder.aether.common.support.pool.CommonConstantPool.APP_COMMON_PREFIX;
import static top.finder.aether.data.core.support.pool.SystemSettingConstantPool.SYSTEM_SETTING_KEY;

/**
 * <p>系统配置启动类</p>
 *
 * @author guocq
 * @since 2023/1/4
 */
public class SystemConfigHelper {
    private static final Logger log = LoggerFactory.getLogger(SystemConfigHelper.class);

    /**
     * <p>生成全局系统配置key</p>
     *
     * @return java.lang.String
     * @author guocq
     * @date 2023/1/4 15:51
     */
    public static String generateSystemSettingKey() {
        return generateSystemSettingKey(null);
    }

    /**
     * <p>根据appName生成系统配置key</p>
     *
     * @param appName 应用名
     * @return java.lang.String
     * @author guocq
     * @date 2023/1/4 15:51
     */
    public static String generateSystemSettingKey(String appName) {
        String baseSystemConfigKey = RedisHelper.keyJoin(true, APP_COMMON_PREFIX, SYSTEM_SETTING_KEY);
        String systemConfigKey = StrUtil.isNotBlank(appName) ? RedisHelper.keyJoin(true, baseSystemConfigKey, appName) : baseSystemConfigKey;
        log.debug("根据[appName={}]生成的系统配置key为={}", appName, systemConfigKey);
        return systemConfigKey;
    }
}
