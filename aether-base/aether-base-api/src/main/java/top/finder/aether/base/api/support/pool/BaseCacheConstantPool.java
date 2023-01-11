package top.finder.aether.base.api.support.pool;

import static top.finder.aether.common.support.pool.CommonConstantPool.REDIS_KEY_SEPARATOR;
import static top.finder.aether.data.cache.support.pool.CacheConstantPool.CACHE_RESULT_SINGLE;
import static top.finder.aether.data.cache.support.pool.CacheConstantPool.CACHE_RESULT_LIST;

/**
 * <p>Base模块缓存常量池</p>
 *
 * @author guocq
 * @since 2023/1/11
 */
public interface BaseCacheConstantPool {
    /**
     * BASE模块缓存前缀
     */
    String BASE_CACHE_PREFIX = "BASE";

    /**
     * BASE-DICT服务缓存前缀
     */
    String BASE_DICT_CACHE_PREFIX = BASE_CACHE_PREFIX + REDIS_KEY_SEPARATOR + "DICT";

    /**
     * BASE-DICT服务缓存单条结果
     */
    String BASE_DICT_CACHE_SINGLE = BASE_DICT_CACHE_PREFIX + REDIS_KEY_SEPARATOR + CACHE_RESULT_SINGLE;

    /**
     * BASE-DICT服务缓存列表结果
     */
    String BASE_DICT_CACHE_LIST = BASE_DICT_CACHE_PREFIX + REDIS_KEY_SEPARATOR + CACHE_RESULT_LIST;
}
