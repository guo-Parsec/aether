package top.finder.aether.common.support.helper;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Pair;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>map辅助类</p>
 *
 * @author guocq
 * @since 2023/1/5
 */
@Slf4j
public class MapHelper {
    /**
     * <p>根据键值对列表构建map</p>
     *
     * @param pairList 键值对列表
     * @return java.util.Map<K, V>
     * @author guocq
     * @date 2023/1/5 14:18
     */
    public static <K, V> Map<K, V> buildMap(List<Pair<K, V>> pairList) {
        if (CollUtil.isEmpty(pairList)) {
            log.error("pairList不能为空");
            return Maps.newHashMap();
        }
        return pairList.stream().collect(Collectors.toMap(Pair::getKey, Pair::getValue));
    }
}
