package top.finder.aether.system.api.support.pool.record;

import static top.finder.aether.common.support.pool.CommonConstantPool.REDIS_KEY_SEPARATOR;
import static top.finder.aether.data.cache.support.pool.CacheConstantPool.CACHE_RESULT_MULTIPLE;
import static top.finder.aether.data.cache.support.pool.CacheConstantPool.CACHE_RESULT_SINGLE;
import static top.finder.aether.system.api.support.pool.SystemCacheConstantPool.*;

/**
 * <p>系统操作记录缓存常量池</p>
 *
 * @author guocq
 * @since 2023/01/18
 */
public interface SysOperateRecordCacheConstantPool {
    /**
     * record业务
     */
    String B_RECORD = "record";
    
    /**
     * record业务统一前缀
     */
    String P_RECORD = PREFIX + REDIS_KEY_SEPARATOR + B_RECORD;

    /**
     * record业务vo结果集统一前缀
     */
    String P_VO_RECORD = P_RECORD + REDIS_KEY_SEPARATOR + P_VO;

    /**
     * record业务optional-vo结果集统一前缀
     */
    String P_OVO_RECORD = P_RECORD + REDIS_KEY_SEPARATOR + P_OVO;

    /**
     * record业务holder结果集统一前缀
     */
    String P_HO_RECORD = P_RECORD + REDIS_KEY_SEPARATOR + P_HO;

    /**
     * record业务optional-holder结果集统一前缀
     */
    String P_OHO_RECORD = P_RECORD + REDIS_KEY_SEPARATOR + P_OHO;

    /**
     * record业务vo单条结果缓存名称
     */
    String S_VO_RECORD = P_VO_RECORD + REDIS_KEY_SEPARATOR + CACHE_RESULT_SINGLE;

    /**
     * record业务optional<vo>单条结果缓存名称
     */
    String S_OVO_RECORD = P_OVO_RECORD + REDIS_KEY_SEPARATOR + CACHE_RESULT_SINGLE;

    /**
     * record业务vo多条结果缓存名称
     */
    String M_VO_RECORD = P_VO_RECORD + REDIS_KEY_SEPARATOR + CACHE_RESULT_MULTIPLE;

    /**
     * record业务holder单条结果缓存名称
     */
    String S_HO_RECORD = P_HO_RECORD + REDIS_KEY_SEPARATOR + CACHE_RESULT_SINGLE;

    /**
     * record业务optional<holder>单条结果缓存名称
     */
    String S_OHO_RECORD = P_OHO_RECORD + REDIS_KEY_SEPARATOR + CACHE_RESULT_SINGLE;

    /**
     * record业务holder多条结果缓存名称
     */
    String M_HO_RECORD = P_HO_RECORD + REDIS_KEY_SEPARATOR + CACHE_RESULT_MULTIPLE;
}
