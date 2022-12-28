package top.finder.aether.common.support.helper;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.List;

/**
 * <p>转化辅助类</p>
 *
 * @author guocq
 * @since 2022/12/13
 */
public class TransformerHelper {
    private static final Logger log = LoggerFactory.getLogger(TransformerHelper.class);
    public static <S, T> T transformer(S source, Class<T> tClass) {
        if (ObjectUtil.isEmpty(source)) {
            log.warn("转化警告，源对象[source]不应该为空，将返回[null]作为目标对象");
            return null;
        }
        List<Field> fields = Lists.newArrayList(ReflectHelper.findClassProperties(source.getClass()));
        if (CollUtil.isEmpty(fields)) {
            log.warn("转化警告，源对象[source]内的成员变量属性为空，将返回空的目标对象[target]");
            return ReflectHelper.newInstance(tClass);
        }
        T target = ReflectHelper.newInstance(tClass);
        BeanUtil.copyProperties(source, target);
        return target;
    }
}
