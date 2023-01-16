package top.finder.aether.system.api.support.pool.user;

import static top.finder.aether.common.support.pool.CommonConstantPool.REDIS_KEY_SEPARATOR;
import static top.finder.aether.data.cache.support.pool.CacheConstantPool.CACHE_RESULT_MULTIPLE;
import static top.finder.aether.data.cache.support.pool.CacheConstantPool.CACHE_RESULT_SINGLE;
import static top.finder.aether.system.api.support.pool.SystemCacheConstantPool.*;

/**
 * <p>系统用户缓存常量池</p>
 *
 * @author guocq
 * @since 2023/1/16
 */
public interface UserCacheConstantPool {
    /**
     * user业务
     */
    String B_USER = "user";

    /**
     * user业务统一前缀
     */
    String P_USER = PREFIX + REDIS_KEY_SEPARATOR + B_USER;

    /**
     * user业务vo结果集统一前缀
     */
    String P_VO_USER = P_USER + REDIS_KEY_SEPARATOR + P_VO;

    /**
     * user业务details结果集统一前缀
     */
    String P_UD_USER = P_USER + REDIS_KEY_SEPARATOR + "details";

    /**
     * user业务optional-vo结果集统一前缀
     */
    String P_OVO_USER = P_USER + REDIS_KEY_SEPARATOR + P_OVO;

    /**
     * user业务holder结果集统一前缀
     */
    String P_HO_USER = P_USER + REDIS_KEY_SEPARATOR + P_HO;

    /**
     * user业务optional-holder结果集统一前缀
     */
    String P_OHO_USER = P_USER + REDIS_KEY_SEPARATOR + P_OHO;

    /**
     * user业务vo单条结果缓存名称
     */
    String S_VO_USER = P_VO_USER + REDIS_KEY_SEPARATOR + CACHE_RESULT_SINGLE;

    /**
     * user业务userDetails单条结果缓存名称
     */
    String S_UD_USER = P_UD_USER + REDIS_KEY_SEPARATOR + CACHE_RESULT_SINGLE;

    /**
     * user业务optional<vo>单条结果缓存名称
     */
    String S_OVO_USER = P_OVO_USER + REDIS_KEY_SEPARATOR + CACHE_RESULT_SINGLE;

    /**
     * user业务vo多条结果缓存名称
     */
    String M_VO_USER = P_VO_USER + REDIS_KEY_SEPARATOR + CACHE_RESULT_MULTIPLE;

    /**
     * user业务holder单条结果缓存名称
     */
    String S_HO_USER = P_HO_USER + REDIS_KEY_SEPARATOR + CACHE_RESULT_SINGLE;

    /**
     * user业务optional<holder>单条结果缓存名称
     */
    String S_OHO_USER = P_OHO_USER + REDIS_KEY_SEPARATOR + CACHE_RESULT_SINGLE;

    /**
     * user业务holder多条结果缓存名称
     */
    String M_HO_USER = P_HO_USER + REDIS_KEY_SEPARATOR + CACHE_RESULT_MULTIPLE;
}
