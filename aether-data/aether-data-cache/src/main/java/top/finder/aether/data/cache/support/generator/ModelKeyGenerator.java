package top.finder.aether.data.cache.support.generator;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;
import top.finder.aether.common.model.IModel;
import top.finder.aether.common.support.helper.CodeHelper;
import top.finder.aether.common.support.pool.CommonConstantPool;

import java.lang.reflect.Method;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

/**
 * <p>Model缓存key生成</p>
 *
 * @author guocq
 * @since 2022/12/27
 */
@Component(value = "modelKeyGenerator")
public class ModelKeyGenerator implements KeyGenerator {
    private static final Logger log = LoggerFactory.getLogger(ModelKeyGenerator.class);

    static final String EMPTY_STR = "EMPTY";

    /**
     * Generate a key for the given method and its parameters.
     *
     * @param target the target instance
     * @param method the method being called
     * @param params the method parameters (with any var-args expanded)
     * @return a generated key
     */
    @Override
    public Object generate(Object target, Method method, Object... params) {
        if (ArrayUtil.isEmpty(params)) {
            CodeHelper.logAetherError(log, "[ModelKeyGenerator]参数列表为空,不能生成缓存的key");
        }
        if (params.length > 1) {
            CodeHelper.logAetherError(log, "[ModelKeyGenerator]参数列表长度不能超过1");
        }
        Object param = params[0];
        if (param instanceof IModel) {
            IModel model = (IModel) param;
            Map<String, Object> modelMap = BeanUtil.beanToMap(model);
            StringBuilder builder = new StringBuilder();
            TreeMap<String, Object> sortedMap = MapUtil.sort(modelMap);
            sortedMap.forEach((key, value) -> {
                if (StrUtil.isNotBlank(key) && ObjectUtil.isNotEmpty(value)) {
                    builder.append(key).append(CommonConstantPool.REDIS_KEY_SEPARATOR).append(value);
                }
            });
            return StrUtil.isBlank(builder.toString()) ? EMPTY_STR : builder.toString().toUpperCase(Locale.ROOT);
        }
        return CodeHelper.logAetherErrorReturn(log, "[ModelKeyGenerator]参数必须为IModel类型");
    }
}
