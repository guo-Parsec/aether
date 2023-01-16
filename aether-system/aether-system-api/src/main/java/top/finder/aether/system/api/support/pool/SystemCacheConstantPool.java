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
}
