package top.finder.aether.data.core.config;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;
import top.finder.aether.common.support.pool.CommonConstantPool;
import top.finder.aether.data.core.support.runner.ApiRegister;
import top.finder.aether.data.core.support.annotation.ApiScan;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>API扫描配置</p>
 *
 * @author guocq
 * @see ApiScan
 * @since 2023/1/10
 */
public class ApiScanConfig implements ImportSelector {
    private static final Logger log = LoggerFactory.getLogger(ApiScanConfig.class);
    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        // 获取EnableEcho注解的所有属性的value
        Map<String, Object> attributes = annotationMetadata.getAnnotationAttributes(ApiScan.class.getName());
        if (attributes == null) {
            return new String[0];
        }
        String[] packages = (String[]) attributes.get("value");
        if (isArrayBlank(packages)) {
            String basePackage = null;
            String className = annotationMetadata.getClassName();
            if (StrUtil.isBlank(className)) {
                log.error("获取className为空");
                throw new IllegalStateException("获取className为空");
            }
            try {
                Class<?> baseClass = Class.forName(className);
                basePackage = ClassUtil.getPackage(baseClass);
            } catch (ClassNotFoundException e) {
                basePackage = className.substring(0, className.lastIndexOf(CommonConstantPool.POINT));
            }
            packages = new String[]{basePackage};
        }
        log.info("加载的包名为{}", Arrays.toString(packages));
        ApiRegister.setBasePackages(packages);
        return new String[0];
    }

    /**
     * <p>判断数组内是否为空字符串</p>
     *
     * @param array 数组
     * @return boolean
     * @author guocq
     * @date 2023/1/6 9:38
     */
    private boolean isArrayBlank(String[] array) {
        if (ArrayUtil.isEmpty(array)) {
            return true;
        }
        List<String> filter = Arrays.stream(array).filter(StrUtil::isNotBlank).collect(Collectors.toList());
        return CollUtil.isEmpty(filter);
    }
}
