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
     * APP名称 - AUTH
     */
    String APP_NAME_AUTH = "aether-auth";

    /**
     * 用于feign的authApp
     */
    String APP_AUTH = APP_NAME_AUTH + "/auth";
}
