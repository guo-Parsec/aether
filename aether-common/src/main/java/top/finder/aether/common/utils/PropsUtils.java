package top.finder.aether.common.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.dialect.Props;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * <p>属性工具类</p>
 *
 * @author guocq
 * @since 2023/1/17
 */
public class PropsUtils {
    private static final Logger log = LoggerFactory.getLogger(PropsUtils.class);

    /**
     * <p>获取属性map</p>
     *
     * @param props 属性对象
     * @param key   键
     * @return {@link Map}
     * @author guocq
     * @date 2023/1/17 15:18
     */
    public static Map<String, String> getPropsForMapForStr(Props props, String key) {
        return getPropsForMap(props, key, String.class);
    }

    /**
     * <p>获取属性map</p>
     *
     * @param props 属性对象
     * @param key   键
     * @return {@link Map}
     * @author guocq
     * @date 2023/1/17 15:18
     */
    public static Map<String, Object> getPropsForMapForObject(Props props, String key) {
        return getPropsForMap(props, key, Object.class);
    }

    /**
     * <p>获取属性map</p>
     *
     * @param props  属性对象
     * @param key    键
     * @param tClass value存储类型
     * @return java.util.Map<java.lang.String, java.lang.String>
     * @author guocq
     * @date 2023/1/17 15:18
     */
    public static <T> Map<String, T> getPropsForMap(Props props, String key, Class<T> tClass) {
        final String mapStart = "{";
        final String mapEnd = "}";
        Map<String, T> map = Maps.newHashMap();
        String mapStr = props.getStr(key);
        if (StrUtil.isBlank(mapStr)) {
            log.error("key{}不存在", key);
            return map;
        }
        if (!mapStr.startsWith(mapStart) && !mapStr.endsWith(mapEnd)) {
            log.error("key{}的值{}不符合map标准", key, mapStr);
            return map;
        }
        String mapContent = mapStr.substring(1, mapStr.length() - 1);
        String[] mapElements = mapContent.split(",");
        for (String mapElement : mapElements) {
            if (!mapElement.contains(":")) {
                log.error("key{}的值{}不符合map标准", key, mapStr);
                break;
            }
            String[] contentArray = mapElement.split(":");
            map.put(contentArray[0], tClass.cast(contentArray[1]));
        }
        return map;
    }

    /**
     * <p>获取list属性</p>
     *
     * @param props 属性对象
     * @param key   key
     * @return java.util.List<T>
     * @author guocq
     * @date 2023/1/17 15:48
     */
    public static List<Object> getPropsForListForObject(Props props, String key) {
        return getPropsForList(props, key, Object.class);
    }

    /**
     * <p>获取list属性</p>
     *
     * @param props 属性对象
     * @param key   key
     * @return java.util.List<T>
     * @author guocq
     * @date 2023/1/17 15:48
     */
    public static List<String> getPropsForListForString(Props props, String key) {
        return getPropsForList(props, key, String.class);
    }

    /**
     * <p>获取list属性</p>
     *
     * @param props  属性对象
     * @param key    key
     * @param tClass value存储类型
     * @return java.util.List<T>
     * @author guocq
     * @date 2023/1/17 15:48
     */
    public static <T> List<T> getPropsForList(Props props, String key, Class<T> tClass) {
        final String listStart = "[";
        final String listEnd = "]";
        List<T> list = Lists.newArrayList();
        String listStr = props.getStr(key);
        if (StrUtil.isBlank(listStr)) {
            log.error("key{}不存在", key);
            return list;
        }
        if (!listStr.startsWith(listStart) && !listStr.endsWith(listEnd)) {
            log.error("key{}的值{}不符合list标准", key, listStr);
            return list;
        }
        String listContent = listStr.substring(1, listStr.length() - 1);
        String[] split = listContent.split(",");
        for (String val : split) {
            list.add(tClass.cast(val));
        }
        return list;
    }
}
