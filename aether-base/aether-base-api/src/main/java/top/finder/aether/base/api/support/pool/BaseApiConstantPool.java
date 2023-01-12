package top.finder.aether.base.api.support.pool;

/**
 * <p>基础模块api常量池</p>
 *
 * @author guocq
 * @since 2022/12/27
 */
public interface BaseApiConstantPool {
    /**
     * 基础模块网络请求前缀
     */
    String BASE_WEB_API_PREFIX = "/sys";

    /**
     * 用户模块网络请求前缀
     */
    String USER_WEB_API_PREFIX = BASE_WEB_API_PREFIX + "/user";

    /**
     * 用户模块网络请求前缀
     */
    String ROLE_WEB_API_PREFIX = BASE_WEB_API_PREFIX + "/role";

    /**
     * 字典模块网络请求前缀
     */
    String DICT_WEB_API_PREFIX = BASE_WEB_API_PREFIX + "/dict";

    /**
     * 日志模块网络请求前缀
     */
    String LOG_WEB_API_PREFIX = BASE_WEB_API_PREFIX + "/log";

    /**
     * 参数模块网络请求前缀
     */
    String PARAM_WEB_API_PREFIX = BASE_WEB_API_PREFIX + "/param";

    /**
     * 资源模块网络请求前缀
     */
    String RESOURCE_WEB_API_PREFIX = BASE_WEB_API_PREFIX + "/resource";

    /**
     * 系统配置模块网络请求前缀
     */
    String SYSTEM_SETTING_WEB_API_PREFIX = BASE_WEB_API_PREFIX + "/system-setting";
}
