package cn.yanghuisen.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author Y
 */
@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<String,Object> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory){
        // 创建模板
        RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();
        // 设置String类型的Key的序列器
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        // 设置String类型的value的序列器
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        // 设置Hash类型的Key的序列器
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        // 设置hash类型的value的序列器
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        // 设置连接方式
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        return redisTemplate;
    }
}