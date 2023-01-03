package top.finder.aether.common.support.pool;


/**
 * <p>通用常量池</p>
 *
 * @author guocq
 * @since 2022/12/13
 */
public interface CommonConstantPool {
    /**
     * 方法返回值 - 空值类型
     */
    String VOID_METHOD_RETURN_TYPE = "void";
    /**
     * 应用通用前缀
     */
    String APP_COMMON_PREFIX = "aether";

    /**
     * 字符串 - 点
     */
    String POINT = ".";

    /**
     * redis key的间隔符
     */
    String REDIS_KEY_SEPARATOR = ":";

    /**
     * redis key统一前缀
     */
    String BASE_REDIS_KEY = "AETHER";

    /**
     * 应用属性值通用前缀
     */
    String APP_PROPERTIES_COMMON_PREFIX = APP_COMMON_PREFIX + POINT;

    /**
     * 占位符
     */
    String EMPTY_VAR_PLACEHOLDER = "{}";

    /**
     * Spring App名称key
     */
    String APP_NAME_KEY = "spring.application.name";

    /**
     * User-Agent
     */
    String USER_AGENT_HEAD = "User-Agent";

    /**
     * 成功信息
     */
    String SUCCESS = "SUCCESS";

    /**
     * 失败信息
     */
    String FAILED = "FAILED";

    /**
     * feign调用时注入请求头的key
     */
    String FEIGN_SOURCE_APP_HEAD_KEY = "source-app";

    /**
     * 逻辑删除填充文本
     */
    String LOGIC_DELETE_FILL_TEXT = "deleteAtTime";
}
