package top.finder.aether.common.support.helper;

import cn.hutool.core.util.ReflectUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * <p>反射辅助类</p>
 *
 * @author guocq
 * @see ReflectUtil
 * @since 2022/12/13
 */
public class ReflectHelper extends ReflectUtil {
    /**
     * <p>获取类成员变量</p>
     *
     * @param tClass 类对象
     * @return {@link Field[]}
     * @author guocq
     * @date 2022/12/13 16:50
     */
    public static <T> Field[] findClassProperties(Class<T> tClass) {
        return ReflectUtil.getFields(tClass, ReflectHelper::isFieldClassProperties);
    }

    /**
     * <p>判断{@code field}是否为类成员变量</p>
     *
     * @param field 字段
     * @return boolean 是否为类成员变量 true: 是类成员变量 false: 不是类成员变量
     * @author guocq
     * @date 2022/12/13 16:49
     */
    public static boolean isFieldClassProperties(Field field) {
        return !Modifier.isStatic(field.getModifiers()) &&
                !Modifier.isFinal(field.getModifiers()) &&
                !Modifier.isSynchronized(field.getModifiers()) &&
                !Modifier.isTransient(field.getModifiers());
    }
}
