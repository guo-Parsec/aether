package top.finder.aether.common.support.helper;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.finder.aether.common.support.exception.AetherValidException;

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

    /**
     * <p>对象互转</p>
     *
     * @param source 源对象
     * @param tClass 目标对象class
     * @return T
     * @author guocq
     * @date 2022/12/29 15:13
     */
    public static <S, T> T transformer(S source, Class<T> tClass) {
        if (ObjectUtil.isEmpty(source)) {
            log.error("转化警告，源对象[source]不应该为空");
            throw new AetherValidException("转化警告，源对象[source]不应该为空");
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
