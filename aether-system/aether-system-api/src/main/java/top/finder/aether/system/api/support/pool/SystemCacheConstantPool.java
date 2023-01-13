package top.finder.aether.system.api.support.pool;

import static top.finder.aether.common.support.pool.CommonConstantPool.REDIS_KEY_SEPARATOR;
import static top.finder.aether.data.cache.support.pool.CacheConstantPool.CACHE_RESULT_SINGLE;
import static top.finder.aether.data.cache.support.pool.CacheConstantPool.CACHE_RESULT_LIST;

/**
 * <p>系统模块缓存常量池</p>
 *
 * @author guocq
 * @since 2023/1/11
 */
public interface SystemCacheConstantPool {
    /**
     * SYSTEM模块缓存前缀
     */
    String SYSTEM_CACHE_PREFIX = "system";

    /**
     * SYSTEM-DICT服务缓存前缀
     */
    String SYSTEM_DICT_CACHE_PREFIX = SYSTEM_CACHE_PREFIX + REDIS_KEY_SEPARATOR + "dict";

    /**
     * SYSTEM-DICT服务缓存单条结果
     */
    String SYSTEM_DICT_CACHE_SINGLE = SYSTEM_DICT_CACHE_PREFIX + REDIS_KEY_SEPARATOR + CACHE_RESULT_SINGLE;

    /**
     * SYSTEM-DICT服务缓存列表结果
     */
    String SYSTEM_DICT_CACHE_LIST = SYSTEM_DICT_CACHE_PREFIX + REDIS_KEY_SEPARATOR + CACHE_RESULT_LIST;

    /**
     * SYSTEM-DICT-MODEL服务缓存单条结果
     */
    String SYSTEM_DICT_MODEL_CACHE_SINGLE = SYSTEM_DICT_CACHE_PREFIX + REDIS_KEY_SEPARATOR + "model" + REDIS_KEY_SEPARATOR + CACHE_RESULT_SINGLE;

    /**
     * SYSTEM-DICT-MODEL服务缓存列表结果
     */
    String SYSTEM_DICT_MODEL_CACHE_LIST = SYSTEM_DICT_CACHE_PREFIX + REDIS_KEY_SEPARATOR + "model" + REDIS_KEY_SEPARATOR + CACHE_RESULT_LIST;

    /**
     * SYSTEM-PARAM服务缓存前缀
     */
    String SYSTEM_PARAM_CACHE_PREFIX = SYSTEM_CACHE_PREFIX + REDIS_KEY_SEPARATOR + "param";

    /**
     * SYSTEM-PARAM服务缓存单条结果
     */
    String SYSTEM_PARAM_CACHE_SINGLE = SYSTEM_PARAM_CACHE_PREFIX + REDIS_KEY_SEPARATOR + CACHE_RESULT_SINGLE;

    /**
     * SYSTEM-PARAM服务缓存列表结果
     */
    String SYSTEM_PARAM_CACHE_LIST = SYSTEM_PARAM_CACHE_PREFIX + REDIS_KEY_SEPARATOR + CACHE_RESULT_LIST;

    /**
     * SYSTEM-PARAM-MODEL服务缓存单条结果
     */
    String SYSTEM_PARAM_MODEL_CACHE_SINGLE = SYSTEM_PARAM_CACHE_PREFIX + REDIS_KEY_SEPARATOR + "model" + REDIS_KEY_SEPARATOR + CACHE_RESULT_SINGLE;

    /**
     * SYSTEM-PARAM-MODEL服务缓存列表结果
     */
    String SYSTEM_PARAM_MODEL_CACHE_LIST = SYSTEM_PARAM_CACHE_PREFIX + REDIS_KEY_SEPARATOR + "model" + REDIS_KEY_SEPARATOR + CACHE_RESULT_LIST;
}
