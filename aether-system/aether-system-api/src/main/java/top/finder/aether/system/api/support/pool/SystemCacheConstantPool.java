package top.finder.aether.system.api.support.pool;

import static top.finder.aether.common.support.pool.CommonConstantPool.REDIS_KEY_SEPARATOR;

/**
 * <p>系统缓存常量池</p>
 *
 * @author guocq
 */
public interface SystemCacheConstantPool {
    /**
     * 系统模块统一前缀
     */
    String PREFIX = "system";

    /**
     * vo结果集前缀
     */
    String P_VO = "vo";

    /**
     * holder结果集前缀
     */
    String P_HO = "holder";

    /**
     * optional结果集前缀
     */
    String P_O = "optional";

    /**
     * optional-holder结果集前缀
     */
    String P_OHO = P_O + REDIS_KEY_SEPARATOR + P_HO;

    /**
     * optional-vo结果集前缀
     */
    String P_OVO = P_O + REDIS_KEY_SEPARATOR + P_VO;

    /**
     * dict业务
     */
    String B_DICT = "dict";

    /**
     * param业务
     */
    String B_PARAM = "param";

    /**
     * dict业务统一前缀
     */
    String P_DICT = PREFIX + REDIS_KEY_SEPARATOR + B_DICT;

    /**
     * dict业务vo结果集统一前缀
     */
    String P_VO_DICT = P_DICT + REDIS_KEY_SEPARATOR + P_VO;

    /**
     * dict业务optional-VO结果集统一前缀
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
}
