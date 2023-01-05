package top.finder.aether.data.core.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.apache.ibatis.reflection.MetaObject;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.finder.aether.data.core.support.mp.SqlInjector;

import java.time.LocalDateTime;

/**
 * <p>mybatisPlus配置类</p>
 *
 * @author guocq
 * @since 2022/12/12
 */
@MapperScan({"top.finder.aether.**.mapper", "com.baomidou.mybatisplus.samples.quickstart.mapper"})
@Configuration
public class MybatisPlusConfig implements MetaObjectHandler {
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 防止全表更新与删除：攻击SQL阻断解析器、加入解析链，防止恶意进行delete、update全表操作
        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        // 自动分页
        PaginationInnerInterceptor pageInterceptor = new PaginationInnerInterceptor(DbType.MYSQL);
        pageInterceptor.setMaxLimit(500L);
        interceptor.addInnerInterceptor(pageInterceptor);
        return interceptor;
    }

    @Bean
    public SqlInjector sqlInjector() {
        return new SqlInjector();
    }

    /**
     * 插入元对象字段填充（用于插入时对公共字段的填充）
     *
     * @param metaObject 元对象
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        setFieldValByName("gmtCreate", LocalDateTime.now(), metaObject);
        setFieldValByName("gmtModify", LocalDateTime.now(), metaObject);
    }

    /**
     * 更新元对象字段填充（用于更新时对公共字段的填充）
     *
     * @param metaObject 元对象
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        setFieldValByName("gmtModify", LocalDateTime.now(), metaObject);
    }
}
