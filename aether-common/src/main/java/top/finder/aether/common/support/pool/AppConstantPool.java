package top.finder.aether.common.support.pool;

/**
 * <p>App常量池</p>
 *
 * @author guocq
 * @since 2022/12/28
 */
public interface AppConstantPool {
    /**
     * APP名称 - system
     */
    String APP_NAME_SYSTEM = "aether-system";

    /**
     * 用于feign的systemApp
     */
    String APP_SYSTEM = APP_NAME_SYSTEM + "/system";

    /**
     * APP名称 - SECURITY
     */
    String APP_NAME_SECURITY = "aether-security";

    /**
     * 用于feign的authApp
     */
    String APP_SECURITY = APP_NAME_SECURITY + "/security";
}
