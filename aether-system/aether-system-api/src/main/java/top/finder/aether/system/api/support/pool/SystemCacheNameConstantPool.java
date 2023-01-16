package top.finder.aether.system.api.support.pool;

import static top.finder.aether.common.support.pool.CommonConstantPool.REDIS_KEY_SEPARATOR;
import static top.finder.aether.data.cache.support.pool.CacheConstantPool.CACHE_RESULT_MULTIPLE;
import static top.finder.aether.data.cache.support.pool.CacheConstantPool.CACHE_RESULT_SINGLE;

/**
 * <p>系统模块缓存常量池</p>
 *
 * @author guocq
 * @since 2023/1/11
 */
public interface SystemCacheNameConstantPool extends SystemCacheConstantPool {
    /**
     * dict业务vo单条结果缓存名称
     */
    String S_VO_DICT = P_VO_DICT + REDIS_KEY_SEPARATOR + CACHE_RESULT_SINGLE;

    /**
     * dict业务optional<vo>单条结果缓存名称
     */
    String S_OVO_DICT = P_OVO_DICT + REDIS_KEY_SEPARATOR + CACHE_RESULT_SINGLE;

    /**
     * dict业务vo多条结果缓存名称
     */
    String M_VO_DICT = P_VO_DICT + REDIS_KEY_SEPARATOR + CACHE_RESULT_MULTIPLE;

    /**
     * dict业务holder单条结果缓存名称
     */
    String S_HO_DICT = P_HO_DICT + REDIS_KEY_SEPARATOR + CACHE_RESULT_SINGLE;

    /**
     * dict业务optional<vo>单条结果缓存名称
     */
    String S_OHO_DICT = P_OHO_DICT + REDIS_KEY_SEPARATOR + CACHE_RESULT_SINGLE;

    /**
     * dict业务holder多条结果缓存名称
     */
    String M_HO_DICT = P_HO_DICT + REDIS_KEY_SEPARATOR + CACHE_RESULT_MULTIPLE;

    /**
     * param业务vo单条结果缓存名称
     */
    String S_VO_PARAM = P_VO_PARAM + REDIS_KEY_SEPARATOR + CACHE_RESULT_SINGLE;

    /**
     * param业务optional<vo>单条结果缓存名称
     */
    String S_OVO_PARAM = P_OVO_PARAM + REDIS_KEY_SEPARATOR + CACHE_RESULT_SINGLE;

    /**
     * param业务vo多条结果缓存名称
     */
    String M_VO_PARAM = P_VO_PARAM + REDIS_KEY_SEPARATOR + CACHE_RESULT_MULTIPLE;

    /**
     * param业务holder单条结果缓存名称
     */
    String S_HO_PARAM = P_HO_PARAM + REDIS_KEY_SEPARATOR + CACHE_RESULT_SINGLE;

    /**
     * param业务optional<holder>单条结果缓存名称
     */
    String S_OHO_PARAM = P_OHO_PARAM + REDIS_KEY_SEPARATOR + CACHE_RESULT_SINGLE;

    /**
     * param业务holder多条结果缓存名称
     */
    String M_HO_PARAM = P_HO_PARAM + REDIS_KEY_SEPARATOR + CACHE_RESULT_MULTIPLE;

    /**
     * resource业务vo单条结果缓存名称
     */
    String S_VO_RESOURCE = P_VO_RESOURCE + REDIS_KEY_SEPARATOR + CACHE_RESULT_SINGLE;

    /**
     * resource业务optional<vo>单条结果缓存名称
     */
    String S_OVO_RESOURCE = P_OVO_RESOURCE + REDIS_KEY_SEPARATOR + CACHE_RESULT_SINGLE;

    /**
     * resource业务vo多条结果缓存名称
     */
    String M_VO_RESOURCE = P_VO_RESOURCE + REDIS_KEY_SEPARATOR + CACHE_RESULT_MULTIPLE;

    /**
     * resource业务holder单条结果缓存名称
     */
    String S_HO_RESOURCE = P_HO_RESOURCE + REDIS_KEY_SEPARATOR + CACHE_RESULT_SINGLE;

    /**
     * resource业务optional<holder>单条结果缓存名称
     */
    String S_OHO_RESOURCE = P_OHO_RESOURCE + REDIS_KEY_SEPARATOR + CACHE_RESULT_SINGLE;

    /**
     * resource业务holder多条结果缓存名称
     */
    String M_HO_RESOURCE = P_HO_RESOURCE + REDIS_KEY_SEPARATOR + CACHE_RESULT_MULTIPLE;
}

