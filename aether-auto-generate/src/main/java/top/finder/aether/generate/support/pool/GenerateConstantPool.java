package top.finder.aether.generate.support.pool;

/**
 * <p>生成器模块常量池</p>
 *
 * @author guocq
 * @since 2023/1/17
 */
public interface GenerateConstantPool {
    /**
     * 配置文件名称
     */
    String CONFIG_FILE_NAME = "generate-config.properties";

    /**
     * key - db
     */
    String KEY_DB = "db";

    /**
     * key - jdbc url
     */
    String KEY_URL = KEY_DB + ".url";

    /**
     * key - 数据库密码
     */
    String KEY_PASSWORD = KEY_DB + ".password";

    /**
     * key - 用户名
     */
    String KEY_USERNAME = KEY_DB + ".username";

    /**
     * key - 数据库
     */
    String KEY_SCHEMA = KEY_DB + ".schema";

    /**
     * key - 数据库类型
     */
    String KEY_TYPE = KEY_DB + ".type";
}
