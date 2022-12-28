package top.finder.aether.data.cache.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import top.finder.aether.common.support.pool.CommonConstantPool;
import top.finder.aether.data.cache.support.helper.RedisHelper;

import java.util.Optional;

/**
 * <p>缓存配置类</p>
 *
 * @author guocq
 * @since 2022/12/14
 */
@EnableCaching
@Configuration
@Import(org.springframework.boot.autoconfigure.cache.CacheProperties.class)
public class CacheConfig extends CachingConfigurerSupport {
    private RedisSerializer<Object> redisSerializer;
    private CacheProperties cacheProperties;

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory factory) {
        RedisSerializer<String> keySerializer = new StringRedisSerializer();
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(factory);
        //设置Redis缓存有效期为1小时
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(cacheProperties.getRedis().getTimeToLive())
                .disableCachingNullValues()
                .computePrefixWith(this::computePrefixWith)
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(keySerializer))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer));
        return new RedisCacheManager(redisCacheWriter, redisCacheConfiguration);
    }

    private String computePrefixWith(String name) {
        String keyPrefix = Optional.ofNullable(cacheProperties.getRedis()).map(CacheProperties.Redis::getKeyPrefix).orElse(CommonConstantPool.BASE_REDIS_KEY);
        return RedisHelper.keyJoin(keyPrefix, name) + CommonConstantPool.REDIS_KEY_SEPARATOR;
    }

    @Autowired
    public void setRedisSerializer(RedisSerializer<Object> redisSerializer) {
        this.redisSerializer = redisSerializer;
    }

    @Autowired
    public void setCacheProperties(CacheProperties cacheProperties) {
        this.cacheProperties = cacheProperties;
    }
}
