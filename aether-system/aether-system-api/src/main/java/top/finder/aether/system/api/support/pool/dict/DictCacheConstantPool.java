package top.finder.aether.system.api.support.pool.dict;

import static top.finder.aether.common.support.pool.CommonConstantPool.REDIS_KEY_SEPARATOR;
import static top.finder.aether.data.cache.support.pool.CacheConstantPool.CACHE_RESULT_MULTIPLE;
import static top.finder.aether.data.cache.support.pool.CacheConstantPool.CACHE_RESULT_SINGLE;
import static top.finder.aether.system.api.support.pool.SystemCacheConstantPool.*;

/**
 * <p>系统字典缓存常量池</p>
 *
 * @author guocq
 * @since 2023/1/16
 */
public interface DictCacheConstantPool {
    /**
     * dict业务
     */
    String B_DICT = "dict";
    
    /**
     * dict业务统一前缀
     */
    String P_DICT = PREFIX + REDIS_KEY_SEPARATOR + B_DICT;

    /**
     * dict业务vo结果集统一前缀
     */
    String P_VO_DICT = P_DICT + REDIS_KEY_SEPARATOR + P_VO;

    /**
     * dict业务optional-vo结果集统一前缀
     */
    String P_OVO_DICT = P_DICT + REDIS_KEY_SEPARATOR + P_OVO;

    /**
     * dict业务holder结果集统一前缀
     */
    String P_HO_DICT = P_DICT + REDIS_KEY_SEPARATOR + P_HO;

    /**
     * dict业务optional-holder结果集统一前缀
     */
    String P_OHO_DICT = P_DICT + REDIS_KEY_SEPARATOR + P_OHO;

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
     * dict业务optional<holder>单条结果缓存名称
     */
    String S_OHO_DICT = P_OHO_DICT + REDIS_KEY_SEPARATOR + CACHE_RESULT_SINGLE;

    /**
     * dict业务holder多条结果缓存名称
     */
    String M_HO_DICT = P_HO_DICT + REDIS_KEY_SEPARATOR + CACHE_RESULT_MULTIPLE;
}
