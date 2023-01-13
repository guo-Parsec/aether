package top.finder.aether.system.api.support.pool;

/**
 * <p>系统业务常量池</p>
 *
 * @author guocq
 * @since 2023/1/5
 */
public interface SystemConstantPool {
    /**
     * 用户类型 - 内部用户
     */
    Integer USER_TYPE_INNER = 0;

    /**
     * 用户类型 - 注册用户
     */
    Integer USER_TYPE_REGISTERED = 1;

    /**
     * 性别类型 - 未知
     */
    Integer SEX_TYPE_UNKNOWN = 0;

    /**
     * 性别类型 - 男性
     */
    Integer SEX_TYPE_MALE = 1;

    /**
     * 性别类型 - 女性
     */
    Integer SEX_TYPE_FEMALE = 2;

    /**
     * 启用状态 - 启用
     */
    Integer ENABLE_STATUS_ENABLE = 0;

    /**
     * 启用状态 - 禁用
     */
    Integer ENABLE_STATUS_DISABLE = 1;

    /**
     * 操作结果 - 操作成功
     */
    Integer OPERATE_RESULT_SUCCESS = 0;

    /**
     * 操作结果 - 操作失败
     */
    Integer OPERATE_RESULT_FAILED = 1;

    /**
     * 默认初始密码
     */
    String PARAM_DEFAULT_PASSWORD = "DEFAULT_PASSWORD";

    /**
     * 默认用户密码加解密策略
     */
    String PARAM_DEFAULT_USER_PASSWORD_CRYPTO_STRATEGY = "DEFAULT_USER_PASSWORD_CRYPTO_STRATEGY";
}
