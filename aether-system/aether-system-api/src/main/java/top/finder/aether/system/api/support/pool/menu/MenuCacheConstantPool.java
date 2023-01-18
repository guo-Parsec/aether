package top.finder.aether.system.api.support.pool.menu;

import static top.finder.aether.common.support.pool.CommonConstantPool.REDIS_KEY_SEPARATOR;
import static top.finder.aether.data.cache.support.pool.CacheConstantPool.CACHE_RESULT_MULTIPLE;
import static top.finder.aether.data.cache.support.pool.CacheConstantPool.CACHE_RESULT_SINGLE;
import static top.finder.aether.system.api.support.pool.SystemCacheConstantPool.*;

/**
 * <p>系统菜单缓存常量池</p>
 *
 * @author guocq
 * @since 2023/01/18
 */
public interface MenuCacheConstantPool {
    /**
     * menu业务
     */
    String B_MENU = "menu";
    
    /**
     * menu业务统一前缀
     */
    String P_MENU = PREFIX + REDIS_KEY_SEPARATOR + B_MENU;

    /**
     * menu业务vo结果集统一前缀
     */
    String P_VO_MENU = P_MENU + REDIS_KEY_SEPARATOR + P_VO;

    /**
     * menu业务optional-vo结果集统一前缀
     */
    String P_OVO_MENU = P_MENU + REDIS_KEY_SEPARATOR + P_OVO;

    /**
     * menu业务holder结果集统一前缀
     */
    String P_HO_MENU = P_MENU + REDIS_KEY_SEPARATOR + P_HO;

    /**
     * menu业务optional-holder结果集统一前缀
     */
    String P_OHO_MENU = P_MENU + REDIS_KEY_SEPARATOR + P_OHO;

    /**
     * menu业务vo单条结果缓存名称
     */
    String S_VO_MENU = P_VO_MENU + REDIS_KEY_SEPARATOR + CACHE_RESULT_SINGLE;

    /**
     * menu业务optional<vo>单条结果缓存名称
     */
    String S_OVO_MENU = P_OVO_MENU + REDIS_KEY_SEPARATOR + CACHE_RESULT_SINGLE;

    /**
     * menu业务vo多条结果缓存名称
     */
    String M_VO_MENU = P_VO_MENU + REDIS_KEY_SEPARATOR + CACHE_RESULT_MULTIPLE;

    /**
     * menu业务holder单条结果缓存名称
     */
    String S_HO_MENU = P_HO_MENU + REDIS_KEY_SEPARATOR + CACHE_RESULT_SINGLE;

    /**
     * menu业务optional<holder>单条结果缓存名称
     */
    String S_OHO_MENU = P_OHO_MENU + REDIS_KEY_SEPARATOR + CACHE_RESULT_SINGLE;

    /**
     * menu业务holder多条结果缓存名称
     */
    String M_HO_MENU = P_HO_MENU + REDIS_KEY_SEPARATOR + CACHE_RESULT_MULTIPLE;
}
