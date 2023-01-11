package top.finder.aether.common.support.pool;

/**
 * <p>App常量池</p>
 *
 * @author guocq
 * @since 2022/12/28
 */
public interface AppConstantPool {
    /**
     * APP名称 - BASE
     */
    String APP_NAME_BASE = "aether-base";

    /**
     * 用于feign的baseApp
     */
    String APP_BASE = APP_NAME_BASE + "/base";

    /**
     * APP名称 - SECURITY
     */
    String APP_NAME_SECURITY = "aether-security";

    /**
     * 用于feign的authApp
     */
    String APP_SECURITY = APP_NAME_SECURITY + "/security";
}
