package top.finder.aether.generate.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.dialect.Props;
import cn.hutool.setting.dialect.PropsUtil;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.TemplateType;
import com.baomidou.mybatisplus.generator.config.builder.Entity;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.querys.MySqlQuery;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.keywords.MySqlKeyWordsHandler;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.finder.aether.common.utils.LoggerUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static top.finder.aether.common.utils.PropsUtils.*;
import static top.finder.aether.generate.support.pool.GenerateConstantPool.*;

/**
 * <p>生成器工具类</p>
 *
 * @author guocq
 * @since 2023/1/17
 */
public class GenerateUtil {
    private static final Logger log = LoggerFactory.getLogger(GenerateUtil.class);

    private static Props generateConfigProps = null;

    private static FastAutoGenerator fastAutoGenerator = null;

    static {
        initConfigProps();
    }
    
    public static void execute() {
        buildStrategyConfig().templateEngine(new FreemarkerTemplateEngine()).execute();
    }

    /**
     * <p>获取{@link FastAutoGenerator}</p>
     *
     * @return {@link FastAutoGenerator}
     * @author guocq
     * @date 2023/1/17 14:08
     */
    private static FastAutoGenerator getFastAutoGenerator() {
        DataSourceConfig.Builder builder = initDataSourceConfigBuilder();
        buildSqlType(builder);
        return FastAutoGenerator.create(builder);
    }

    /**
     * <p>构建全局配置信息</p>
     *
     * @return com.baomidou.mybatisplus.generator.FastAutoGenerator
     * @author guocq
     * @date 2023/1/17 14:37
     */
    private static FastAutoGenerator buildGlobalConfig() {
        if (fastAutoGenerator == null) {
            initFastAutoGenerator();
        }
        Boolean fileOverride = generateConfigProps.getBool("generate.global.file-override", true);
        Boolean disableOpenDir = generateConfigProps.getBool("generate.global.disable-open-dir", true);
        Boolean enableKotlin = generateConfigProps.getBool("generate.global.enable-kotlin", false);
        Boolean enableSwagger = generateConfigProps.getBool("generate.global.enable-swagger", true);
        fastAutoGenerator.globalConfig(builder -> {
            if (fileOverride) {
                builder.fileOverride();
            }
            if (!disableOpenDir) {
                builder.disableOpenDir();
            }
            if (enableKotlin) {
                builder.enableKotlin();
            }
            if (enableSwagger) {
                builder.enableSwagger();
            }
            builder.outputDir(generateConfigProps.getStr("generate.global.output-dir"))
                    .author(generateConfigProps.getStr("generate.global.author"))
                    .dateType(generateConfigProps.getEnum(DateType.class, "generate.global.date-type", DateType.TIME_PACK))
                    .commentDate(generateConfigProps.getStr("generate.global.comment-date"));
        });
        return fastAutoGenerator;
    }

    /**
     * <p>构建包配置信息</p>
     *
     * @return com.baomidou.mybatisplus.generator.FastAutoGenerator
     * @author guocq
     * @date 2023/1/17 14:48
     */
    private static FastAutoGenerator buildPackageConfig() {
        if (fastAutoGenerator == null) {
            fastAutoGenerator = buildGlobalConfig();
        }
        fastAutoGenerator.packageConfig(builder -> {
            builder.parent(generateConfigProps.getStr("generate.package.parent"))
                    .moduleName(generateConfigProps.getStr("generate.package.moduleName", null))
                    .entity(generateConfigProps.getStr("generate.package.entity"))
                    .service(generateConfigProps.getStr("generate.package.service"))
                    .serviceImpl(generateConfigProps.getStr("generate.package.serviceImpl"))
                    .mapper(generateConfigProps.getStr("generate.package.mapper"))
                    .xml(generateConfigProps.getStr("generate.package.xml"))
                    .controller(generateConfigProps.getStr("generate.package.controller"))
                    .other(generateConfigProps.getStr("generate.package.other"))
                    .pathInfo(Collections.singletonMap(OutputFile.mapperXml, generateConfigProps.getStr("generate.package.pathInfo.mapperXml")));
        });
        return fastAutoGenerator;
    }

    /**
     * <p>构建模板配置信息</p>
     *
     * @return com.baomidou.mybatisplus.generator.FastAutoGenerator
     * @author guocq
     * @date 2023/1/17 15:03
     */
    private static FastAutoGenerator buildTemplateConfig() {
        if (fastAutoGenerator == null) {
            fastAutoGenerator = buildPackageConfig();
        }
        Boolean disable = generateConfigProps.getBool("generate.template.disable", false);
        String disables = generateConfigProps.getStr("generate.template.disables", "");
        List<TemplateType> disabledTemplates = Lists.newArrayList();
        if (StrUtil.isNotBlank(disables)) {
            String[] templates = disables.split(",");
            Arrays.stream(templates).forEach(template -> {
                try {
                    TemplateType templateType = TemplateType.valueOf(template);
                    disabledTemplates.add(templateType);
                } catch (IllegalArgumentException e) {
                    log.error("template[{}]不存在", template);
                }
            });
        }
        fastAutoGenerator.templateConfig(builder -> {
            if (disable) {
                builder.disable();
            }
            if (CollUtil.isNotEmpty(disabledTemplates)) {
                TemplateType[] templateTypes = ArrayUtil.toArray(disabledTemplates, TemplateType.class);
                builder.disable(templateTypes);
            }
            builder.entity(generateConfigProps.getStr("generate.template.entity"))
                    .service(generateConfigProps.getStr("generate.template.service"))
                    .serviceImpl(generateConfigProps.getStr("generate.template.serviceImpl"))
                    .mapper(generateConfigProps.getStr("generate.template.mapper"))
                    .mapperXml(generateConfigProps.getStr("generate.template.mapperXml"))
                    .controller(generateConfigProps.getStr("generate.template.controller"));
        });
        return fastAutoGenerator;
    }

    /**
     * <p>构建注入选项</p>
     *
     * @return com.baomidou.mybatisplus.generator.FastAutoGenerator
     * @author guocq
     * @date 2023/1/17 15:35
     */
    private static FastAutoGenerator buildInjectionConfig() {
        if (fastAutoGenerator == null) {
            fastAutoGenerator = buildTemplateConfig();
        }
        fastAutoGenerator.injectionConfig(builder -> {
            builder.customMap(getPropsForMapForObject(generateConfigProps, "generate.injection.customMap"))
                    .customFile(getPropsForMapForStr(generateConfigProps, "generate.injection.customFile"));
        });
        return fastAutoGenerator;
    }

    /**
     * <p>构建策略</p>
     *
     * @return com.baomidou.mybatisplus.generator.FastAutoGenerator
     * @author guocq
     * @date 2023/1/17 15:51
     */
    private static FastAutoGenerator buildStrategyConfig() {
        if (fastAutoGenerator == null) {
            fastAutoGenerator = buildInjectionConfig();
        }
        Boolean enableLombok = generateConfigProps.getBool("generate.strategy.entity.enableLombok", true);
        Boolean enableRemoveIsPrefix = generateConfigProps.getBool("generate.strategy.entity.enableRemoveIsPrefix", true);
        fastAutoGenerator.strategyConfig(builder -> {
            List<String> tables = getPropsForListForString(generateConfigProps, "generate.strategy.addInclude");
            builder.addInclude(ArrayUtil.toArray(tables, String.class));
            Entity.Builder entityBuilder = builder.entityBuilder();
            if (enableLombok) {
                entityBuilder.enableLombok();
            }
            if (enableRemoveIsPrefix) {
                entityBuilder.enableRemoveIsPrefix();
            }
            List<String> columns = getPropsForListForString(generateConfigProps, "generate.strategy.entity.addSuperEntityColumns");
            entityBuilder.superClass(generateConfigProps.getStr("generate.strategy.entity.superClass"))
                    .addSuperEntityColumns(ArrayUtil.toArray(columns, String.class));
            builder.controllerBuilder().enableRestStyle().formatFileName("%sController")
                    .serviceBuilder().formatServiceFileName("%sService").formatServiceImplFileName("%sServiceImpl")
                    .mapperBuilder().enableBaseColumnList().enableBaseResultMap().formatMapperFileName("%sMapper").formatXmlFileName("%sMapper");
        });
        return fastAutoGenerator;
    }

    /**
     * <p>初始化数据源配置</p>
     *
     * @return {@link DataSourceConfig.Builder}
     * @author guocq
     * @date 2023/1/17 14:10
     */
    private static DataSourceConfig.Builder initDataSourceConfigBuilder() {
        String url = generateConfigProps.getStr(KEY_URL);
        String username = generateConfigProps.getStr(KEY_USERNAME);
        String password = generateConfigProps.getStr(KEY_PASSWORD);
        if (StrUtil.hasBlank(url, username, password)) {
            throw LoggerUtil.logAetherError(log, "配置文件[{}]中的数据库连接信息[url={},username={},password={}]存在空值，无法创建数据库连接",
                    CONFIG_FILE_NAME, url, username, password);
        }
        DataSourceConfig.Builder builder = new DataSourceConfig.Builder(url, username, password);
        String schema = generateConfigProps.getStr(KEY_SCHEMA);
        if (StrUtil.isNotBlank(schema)) {
            builder.schema(schema);
        }
        return builder;
    }

    /**
     * <p>初始化配置文件信息</p>
     *
     * @author guocq
     * @date 2023/1/17 14:08
     */
    private static void initConfigProps() {
        if (generateConfigProps == null) {
            generateConfigProps = PropsUtil.get(CONFIG_FILE_NAME);
        }
    }

    /**
     * <p>初始化{@link FastAutoGenerator}</p>
     *
     * @author guocq
     * @date 2023/1/17 14:16
     */
    private static void initFastAutoGenerator() {
        if (fastAutoGenerator == null) {
            fastAutoGenerator = getFastAutoGenerator();
        }
    }

    /**
     * <p>销毁{@link FastAutoGenerator}</p>
     *
     * @author guocq
     * @date 2023/1/17 14:17
     */
    private static void destroyFastAutoGenerator() {
        if (fastAutoGenerator != null) {
            fastAutoGenerator = null;
        }
    }

    /**
     * <p>构建数据库方言等信息</p>
     *
     * @param builder 数据源配置构建器
     * @author guocq
     * @date 2023/1/17 14:09
     */
    private static void buildSqlType(DataSourceConfig.Builder builder) {
        DbType dbType = DbType.getDbType(generateConfigProps.getStr(KEY_TYPE));
        if (DbType.OTHER.equals(dbType) || DbType.MYSQL.equals(dbType)) {
            builder.dbQuery(new MySqlQuery()).typeConvert(new MySqlTypeConvert()).keyWordsHandler(new MySqlKeyWordsHandler());
        }
    }
}
