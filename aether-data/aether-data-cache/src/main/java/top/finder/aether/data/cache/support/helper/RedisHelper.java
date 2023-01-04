package top.finder.aether.data.cache.support.helper;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import top.finder.aether.common.support.helper.CodeHelper;
import top.finder.aether.common.support.helper.SpringBeanHelper;
import top.finder.aether.common.support.pool.CommonConstantPool;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <p>redis辅助类</p>
 *
 * @author guocq
 * @since 2022/12/15
 */
@Slf4j
@Component
public class RedisHelper implements Serializable {
    private static final long serialVersionUID = 1L;

    private final RedisTemplate<String, Object> redisTemplate;

    public RedisHelper(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * <p>根据匹配符{@code pattern}获取所有匹配的key列表</p>
     *
     * @param pattern key的匹配符
     * @return java.util.Set<java.lang.String>
     * @author guocq
     * @date 2022/12/15 14:54
     */
    public Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    /**
     * <p>根据key获取redis的值</p>
     *
     * @param key    存储的key
     * @param vClass 值的class对象
     * @return V
     * @author guocq
     * @date 2022/12/15 14:52
     */
    public <V> V get(String key, Class<V> vClass) {
        try {
            return vClass.cast(redisTemplate.opsForValue().get(key));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * <p>根据匹配符{@code pattern}获取所有key-value映射</p>
     *
     * @param pattern key的匹配符
     * @param vClass  值的class对象
     * @return java.util.Map<java.lang.String, V>
     * @author guocq
     * @date 2022/12/15 15:02
     */
    public <V> Map<String, V> getAll(String pattern, Class<V> vClass) {
        Set<String> keys = keys(pattern);
        if (CollUtil.isEmpty(keys)) {
            return Maps.newHashMap();
        }
        return keys.stream().collect(Collectors.toMap(key -> key, key -> this.get(key, vClass)));
    }

    /**
     * <p>向redis中添加key-value键值对</p>
     *
     * @param key      键
     * @param val      值
     * @param time     过期时间
     * @param timeUnit 时间单位
     * @author guocq
     * @date 2022/12/15 15:05
     */
    public <V> void set(String key, V val, long time, TimeUnit timeUnit) {
        log.debug("向redis中存储[key={},val={}]的数据并设置过期时间为[time={}, timeUnit={}]", key, val, time, timeUnit);
        redisTemplate.opsForValue().set(key, val, time, timeUnit);
    }

    /**
     * <p>向redis中添加key-value键值对</p>
     *
     * @param key  键
     * @param val  值
     * @param time 过期时间 默认为秒
     * @author guocq
     * @date 2022/12/15 15:07
     */
    public <V> void set(String key, V val, long time) {
        set(key, val, time, TimeUnit.SECONDS);
    }

    /**
     * <p>向redis中添加key-value键值对 该键值对不会过期</p>
     *
     * @param key 键
     * @param val 值
     * @author guocq
     * @date 2022/12/15 15:09
     */
    public <V> void set(String key, V val) {
        redisTemplate.opsForValue().set(key, val);
    }

    /**
     * <p>删除redis中的指定key, 删除成功返回true</p>
     *
     * @param key 键
     * @return boolean
     * @author guocq
     * @date 2022/12/15 15:12
     */
    public boolean delete(String key) {
        return Optional.ofNullable(redisTemplate.delete(key)).orElse(Boolean.FALSE);
    }

    /**
     * <p>批量删除redis中的key</p>
     *
     * @param keys 键列表
     * @return boolean
     * @author guocq
     * @date 2022/12/15 16:01
     */
    public long delete(Collection<String> keys) {
        if (CollUtil.isEmpty(keys)) {
            return 0L;
        }
        return Optional.ofNullable(redisTemplate.delete(keys)).orElse(0L);
    }

    /**
     * <p>根据匹配符{@code pattern}删除匹配的key-value映射</p>
     *
     * @param pattern 匹配符
     * @return long
     * @author guocq
     * @date 2022/12/15 16:06
     */
    public long deleteByPattern(String pattern) {
        Set<String> keys = keys(pattern);
        return CollUtil.isEmpty(keys) ? 0L : delete(keys);
    }

    /**
     * <p>对指定{@code key}设置过期时间</p>
     *
     * @param key      键
     * @param time     过期时间
     * @param timeUnit 时间单位
     * @return boolean
     * @author guocq
     * @date 2022/12/15 16:08
     */
    public boolean expire(String key, long time, TimeUnit timeUnit) {
        return Optional.ofNullable(redisTemplate.expire(key, time, timeUnit)).orElse(Boolean.FALSE);
    }

    /**
     * <p>对指定{@code key}设置过期时间</p>
     *
     * @param key  键
     * @param time 过期时间 默认单位为秒
     * @return boolean
     * @author guocq
     * @date 2022/12/15 16:09
     */
    public boolean expire(String key, long time) {
        return expire(key, time, TimeUnit.SECONDS);
    }

    /**
     * <p>获取{@code key}的过期时间</p>
     *
     * @param key      键
     * @param timeUnit 时间单位
     * @return java.lang.Long
     * @author guocq
     * @date 2022/12/15 16:10
     */
    public Long getExpire(String key, TimeUnit timeUnit) {
        return redisTemplate.getExpire(key, timeUnit);
    }

    /**
     * <p>获取{@code key}的过期时间 默认单位为秒</p>
     *
     * @param key 键
     * @return java.lang.Long
     * @author guocq
     * @date 2022/12/15 16:10
     */
    public Long getExpire(String key) {
        return getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * <p>判断redis中是否含有{@code key}</p>
     *
     * @param key 键
     * @return boolean
     * @author guocq
     * @date 2022/12/15 16:12
     */
    public boolean hasKey(String key) {
        return Optional.ofNullable(redisTemplate.hasKey(key)).orElse(Boolean.FALSE);
    }

    /**
     * <p>按{@code delta}对{@code key}的value增长</p>
     *
     * @param key   键
     * @param delta 增长长度
     * @return java.lang.Long
     * @author guocq
     * @date 2022/12/15 16:14
     */
    public Long increment(String key, Long delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * <p>按{@code delta}对{@code key}的value增长</p>
     *
     * @param key   键
     * @param delta 增长长度
     * @return java.lang.Long
     * @author guocq
     * @date 2022/12/15 16:14
     */
    public Double increment(String key, Double delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * <p>对{@code key}的value自增1</p>
     *
     * @param key 键
     * @return java.lang.Long
     * @author guocq
     * @date 2022/12/15 16:14
     */
    public Long increment(String key) {
        return increment(key, 1L);
    }

    /**
     * <p>按{@code delta}对{@code key}的value减少</p>
     *
     * @param key   键
     * @param delta 递减长度
     * @return java.lang.Long
     * @author guocq
     * @date 2022/12/15 16:14
     */
    public Long decrement(String key, Long delta) {
        return increment(key, -delta);
    }

    /**
     * <p>按{@code delta}对{@code key}的value减少</p>
     *
     * @param key   键
     * @param delta 递减长度
     * @return java.lang.Long
     * @author guocq
     * @date 2022/12/15 16:14
     */
    public Double decrement(String key, Double delta) {
        return increment(key, -delta);
    }

    /**
     * <p>对{@code key}的value自减1</p>
     *
     * @param key 键
     * @return java.lang.Long
     * @author guocq
     * @date 2022/12/15 16:14
     */
    public Long decrement(String key) {
        return decrement(key, 1L);
    }

    /**
     * <p>判断hash结构是否含有key</p>
     *
     * @param key     键
     * @param hashKey hash键
     * @return boolean
     * @author guocq
     * @date 2023/1/4 16:10
     */
    public boolean hasHashKey(String key, String hashKey) {
        return redisTemplate.opsForHash().hasKey(key, hashKey);
    }

    /**
     * <p>根据redis的key和hash结构的key获取指定value</p>
     *
     * @param key     键
     * @param hashKey hash结构的键
     * @param vClass  值的类对象
     * @return V
     * @author guocq
     * @date 2022/12/15 16:20
     */
    public <V> V hashGet(String key, String hashKey, Class<V> vClass) {
        return vClass.cast(redisTemplate.opsForHash().get(key, hashKey));
    }

    /**
     * <p>根据{@code key}获取整个hash结构</p>
     *
     * @param key    键
     * @param vClass 值的类对象
     * @return java.util.Map<java.lang.String, V>
     * @author guocq
     * @date 2022/12/15 16:36
     */
    public <V> Map<String, V> hashGet(String key, Class<V> vClass) {
        return redisTemplate.opsForHash().entries(key).entrySet().stream()
                .collect(Collectors.toMap(this::castKey, entry -> CodeHelper.cast(entry.getValue(), vClass)));
    }

    /**
     * <p>根据{@code key}和{@code hashKey}添加hash的值并设置整个hash的过期时间</p>
     *
     * @param key      键
     * @param hashKey  hash键
     * @param val      值
     * @param time     过期时间
     * @param timeUnit 时间单位
     * @author guocq
     * @date 2022/12/15 16:39
     */
    public <V> void hashSet(String key, String hashKey, V val, long time, TimeUnit timeUnit) {
        redisTemplate.opsForHash().put(key, hashKey, val);
        expire(key, time, timeUnit);
    }

    /**
     * <p>根据{@code key}和{@code hashKey}添加hash的值并设置整个hash的过期时间</p>
     *
     * @param key     键
     * @param hashKey hash键
     * @param val     值
     * @param time    过期时间 单位为秒
     * @author guocq
     * @date 2022/12/15 16:39
     */
    public <V> void hashSet(String key, String hashKey, V val, long time) {
        hashSet(key, hashKey, val, time, TimeUnit.SECONDS);
    }

    /**
     * <p>根据{@code key}和{@code hashKey}添加hash的值并设置整个hash的过期时间为永不过期</p>
     *
     * @param key     键
     * @param hashKey hash键
     * @param val     值
     * @author guocq
     * @date 2022/12/15 16:39
     */
    public <V> void hashSet(String key, String hashKey, V val) {
        log.debug("向redis中存储[key={},hashKey={},val={}]的数据并设置永不过期", key, val, hashKey);
        redisTemplate.opsForHash().put(key, hashKey, val);
    }

    /**
     * <p>根据{@code key}添加hash的值并设置整个hash的过期时间</p>
     *
     * @param key      键
     * @param map      hash结构
     * @param time     过期时间
     * @param timeUnit 时间单位
     * @author guocq
     * @date 2022/12/15 16:39
     */
    public <V> void hashSetAll(String key, Map<String, V> map, long time, TimeUnit timeUnit) {
        log.debug("向redis中存储[key={},map={}]的数据并设置过期时间[time={},timeUnit={}]", key, map, time, timeUnit);
        redisTemplate.opsForHash().putAll(key, map);
        expire(key, time, timeUnit);
    }

    /**
     * <p>根据{@code key}添加hash的值并设置整个hash的过期时间</p>
     *
     * @param key  键
     * @param map  hash结构
     * @param time 过期时间 单位为秒
     * @author guocq
     * @date 2022/12/15 16:39
     */
    public <V> void hashSetAll(String key, Map<String, V> map, long time) {
        hashSetAll(key, map, time, TimeUnit.SECONDS);
    }

    /**
     * <p>根据{@code key}添加hash的值并设置整个hash的过期时间为永不过期</p>
     *
     * @param key 键
     * @param map hash键
     * @author guocq
     * @date 2022/12/15 16:39
     */
    public <V> void hashSet(String key, Map<String, V> map) {
        redisTemplate.opsForHash().putAll(key, map);
    }

    /**
     * <p>删除hash结构</p>
     *
     * @param key     键
     * @param hashKey hash键
     * @return long
     * @author guocq
     * @date 2022/12/15 16:49
     */
    public long hashDelete(String key, String... hashKey) {
        List<Object> hashKeyList = Lists.newArrayList(hashKey).stream().map(ele -> (Object) ele).collect(Collectors.toList());
        return redisTemplate.opsForHash().delete(key, hashKeyList);
    }

    /**
     * <p>将{@code Map<Object, Object>}的key转化为{@link String}类型</p>
     *
     * @param entry entry
     * @return java.lang.String
     * @author guocq
     * @date 2022/12/15 16:34
     */
    private String castKey(Map.Entry<Object, Object> entry) {
        return StrUtil.toStringOrNull(entry.getKey());
    }

    /**
     * <p>将多个key拼接为一个key</p>
     *
     * @param keys keys
     * @return java.lang.String
     * @author guocq
     * @date 2022/12/26 14:01
     */
    public static String keyJoin(String... keys) {
        return keyJoin(false, keys);
    }

    public static String keyJoin(boolean isUpperCase, String... keys) {
        List<String> keyList = Arrays.stream(keys).map(key -> isUpperCase ? key.toUpperCase(Locale.ROOT) : key).collect(Collectors.toList());
        return StrUtil.join(CommonConstantPool.REDIS_KEY_SEPARATOR, keyList);
    }

    public static RedisHelper getInstance() {
        return SpringBeanHelper.getBean(RedisHelper.class);
    }
}
