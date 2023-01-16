package top.finder.aether.system.api.support.pool.param;

import static top.finder.aether.common.support.pool.CommonConstantPool.REDIS_KEY_SEPARATOR;
import static top.finder.aether.data.cache.support.pool.CacheConstantPool.CACHE_RESULT_MULTIPLE;
import static top.finder.aether.data.cache.support.pool.CacheConstantPool.CACHE_RESULT_SINGLE;
import static top.finder.aether.system.api.support.pool.SystemCacheConstantPool.*;

/**
 * <p>系统参数缓存常量池</p>
 *
 * @author guocq
 * @since 2023/1/16
 */
public interface ParamCacheConstantPool {
    /**
     * param业务
     */
    String B_PARAM = "param";
    
    /**
     * param业务统一前缀
     */
    String P_PARAM = PREFIX + REDIS_KEY_SEPARATOR + B_PARAM;

    /**
     * param业务vo结果集统一前缀
     */
    String P_VO_PARAM = P_PARAM + REDIS_KEY_SEPARATOR + P_VO;

    /**
     * param业务optional-vo结果集统一前缀
     */
    String P_OVO_PARAM = P_PARAM + REDIS_KEY_SEPARATOR + P_OVO;

    /**
     * param业务holder结果集统一前缀
     */
    String P_HO_PARAM = P_PARAM + REDIS_KEY_SEPARATOR + P_HO;

    /**
     * param业务optional-holder结果集统一前缀
     */
    String P_OHO_PARAM = P_PARAM + REDIS_KEY_SEPARATOR + P_OHO;

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
}
