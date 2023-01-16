package top.finder.aether.system.api.support.pool.resource;

import static top.finder.aether.common.support.pool.CommonConstantPool.REDIS_KEY_SEPARATOR;
import static top.finder.aether.data.cache.support.pool.CacheConstantPool.CACHE_RESULT_MULTIPLE;
import static top.finder.aether.data.cache.support.pool.CacheConstantPool.CACHE_RESULT_SINGLE;
import static top.finder.aether.system.api.support.pool.SystemCacheConstantPool.*;

/**
 * <p>系统资源缓存常量池</p>
 *
 * @author guocq
 * @since 2023/1/16
 */
public interface ResourceCacheConstantPool {
    /**
     * resource业务
     */
    String B_RESOURCE = "resource";
    
    /**
     * resource业务统一前缀
     */
    String P_RESOURCE = PREFIX + REDIS_KEY_SEPARATOR + B_RESOURCE;

    /**
     * resource业务vo结果集统一前缀
     */
    String P_VO_RESOURCE = P_RESOURCE + REDIS_KEY_SEPARATOR + P_VO;

    /**
     * resource业务optional-vo结果集统一前缀
     */
    String P_OVO_RESOURCE = P_RESOURCE + REDIS_KEY_SEPARATOR + P_OVO;

    /**
     * resource业务holder结果集统一前缀
     */
    String P_HO_RESOURCE = P_RESOURCE + REDIS_KEY_SEPARATOR + P_HO;

    /**
     * resource业务optional-holder结果集统一前缀
     */
    String P_OHO_RESOURCE = P_RESOURCE + REDIS_KEY_SEPARATOR + P_OHO;

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
