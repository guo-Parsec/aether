package top.finder.aether.data.core.support.mp;

import com.baomidou.mybatisplus.core.metadata.TableInfo;
import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * <p>逻辑删除方法</p>
 *
 * @author guocq
 * @since 2023/1/3
 */
public class LogicDeleteMethod extends AbstractMySqlMethod {
    private static final long serialVersionUID = 1L;

    public static final String METHOD_NAME = "logicDeleteById";

    public LogicDeleteMethod() {
        super(METHOD_NAME);
    }

    /**
     * <p>注入方法名称获取</p>
     *
     * @return java.lang.String
     * @author guocq
     * @date 2022/12/13 11:43
     */
    @Override
    public String getMethodName() {
        return METHOD_NAME;
    }

    /**
     * 注入自定义 MappedStatement
     *
     * @param mapperClass mapper 接口
     * @param modelClass  mapper 泛型
     * @param tableInfo   数据库表反射信息
     * @return MappedStatement
     */
    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        long now = LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
        final String sql = "update %s set delete_at = " + now + " where %s = #{%s} and delete_at = 0";
        String tableName = tableInfo.getTableName();
        String keyColumn = tableInfo.getKeyColumn();
        String keyProperty = tableInfo.getKeyProperty();
        final String sqlResult = String.format(sql, tableName, keyColumn, keyProperty);
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sqlResult, modelClass);
        return this.addInsertMappedStatement(mapperClass, modelClass, getMethodName(), sqlSource, new NoKeyGenerator(), null, null);
    }
}
