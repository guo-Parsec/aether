package top.finder.aether.system.api.support.pool.role;

import static top.finder.aether.common.support.pool.CommonConstantPool.REDIS_KEY_SEPARATOR;
import static top.finder.aether.data.cache.support.pool.CacheConstantPool.CACHE_RESULT_MULTIPLE;
import static top.finder.aether.data.cache.support.pool.CacheConstantPool.CACHE_RESULT_SINGLE;
import static top.finder.aether.system.api.support.pool.SystemCacheConstantPool.*;

/**
 * <p>系统角色缓存常量池</p>
 *
 * @author guocq
 * @since 2023/1/16
 */
public interface RoleCacheConstantPool {
    /**
     * role业务
     */
    String B_ROLE = "role";
    
    /**
     * role业务统一前缀
     */
    String P_ROLE = PREFIX + REDIS_KEY_SEPARATOR + B_ROLE;

    /**
     * role业务vo结果集统一前缀
     */
    String P_VO_ROLE = P_ROLE + REDIS_KEY_SEPARATOR + P_VO;

    /**
     * role业务optional-vo结果集统一前缀
     */
    String P_OVO_ROLE = P_ROLE + REDIS_KEY_SEPARATOR + P_OVO;

    /**
     * role业务holder结果集统一前缀
     */
    String P_HO_ROLE = P_ROLE + REDIS_KEY_SEPARATOR + P_HO;

    /**
     * role业务optional-holder结果集统一前缀
     */
    String P_OHO_ROLE = P_ROLE + REDIS_KEY_SEPARATOR + P_OHO;

    /**
     * role业务vo单条结果缓存名称
     */
    String S_VO_ROLE = P_VO_ROLE + REDIS_KEY_SEPARATOR + CACHE_RESULT_SINGLE;

    /**
     * role业务optional<vo>单条结果缓存名称
     */
    String S_OVO_ROLE = P_OVO_ROLE + REDIS_KEY_SEPARATOR + CACHE_RESULT_SINGLE;

    /**
     * role业务vo多条结果缓存名称
     */
    String M_VO_ROLE = P_VO_ROLE + REDIS_KEY_SEPARATOR + CACHE_RESULT_MULTIPLE;

    /**
     * role业务holder单条结果缓存名称
     */
    String S_HO_ROLE = P_HO_ROLE + REDIS_KEY_SEPARATOR + CACHE_RESULT_SINGLE;

    /**
     * role业务optional<holder>单条结果缓存名称
     */
    String S_OHO_ROLE = P_OHO_ROLE + REDIS_KEY_SEPARATOR + CACHE_RESULT_SINGLE;

    /**
     * role业务holder多条结果缓存名称
     */
    String M_HO_ROLE = P_HO_ROLE + REDIS_KEY_SEPARATOR + CACHE_RESULT_MULTIPLE;
}
