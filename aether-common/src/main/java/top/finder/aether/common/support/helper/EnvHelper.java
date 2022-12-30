package top.finder.aether.common.support.helper;

import cn.hutool.core.util.StrUtil;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * <p>spring配置文件辅助类</p>
 *
 * @author guocq
 * @since 2022/12/30
 */
public class EnvHelper {
    private static ConfigurableEnvironment env;

    public static void setEnvironment(ConfigurableEnvironment environment) {
        env = environment;
    }

    /**
     * <p>获取配置项</p>
     *
     * @param key 配置key
     * @return java.lang.String
     * @author guocq
     * @date 2022/12/30 10:51
     */
    public static String get(String key) {
        if (StrUtil.isBlank(key)) {
            return null;
        }
        return env.getProperty(key);
    }

    /**
     * <p>获取配置项,允许有默认值</p>
     *
     * @param key          配置key
     * @param defaultValue 默认值
     * @return java.lang.String
     * @author guocq
     * @date 2022/12/30 10:51
     */
    public static String get(String key, String defaultValue) {
        if (StrUtil.isBlank(key)) {
            return null;
        }
        return env.getProperty(key, defaultValue);
    }

    /**
     * <p>获取配置项，返回指定类型</p>
     *
     * @param key   配置key
     * @param clazz 指定类型
     * @return T
     * @author guocq
     * @date 2022/12/30 10:53
     */
    public static <T> T get(String key, Class<T> clazz) {
        if (StrUtil.isBlank(key) || clazz == null) {
            return null;
        }
        return env.getProperty(key, clazz);
    }

    /**
     * <p>获取配置项，返回指定类型，允许有默认值</p>
     *
     * @param key          配置key
     * @param clazz        指定类型
     * @param defaultValue 默认值
     * @return T
     * @author guocq
     * @date 2022/12/30 10:53
     */
    public static <T> T get(String key, Class<T> clazz, T defaultValue) {
        if (StrUtil.isBlank(key) || clazz == null) {
            return null;
        }
        return env.getProperty(key, clazz, defaultValue);
    }
}
