package com.wuhuafeng.redis.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**注意：jackson是根据反射获取model的属性和get/set方法，序列化的顺序为字段、方法(get/set)，默认只序列化public修饰的字段和public修饰的get/set方法。
 * @Description TODO
 * @Author Mr.Wu
 * @Date 2019/10/10 10:50
 * @Version 1.0
 **/
@Configuration
public class RedisConfiguration {
/*
    @Value("${spring.redis.cluster.nodes}")
    private String nodes;*/
    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private Integer port;
    @Value("${spring.redis.password}")
    private String password;
    @Value("${spring.redis.lettuce.pool.maxIdle:}")
    private Integer maxIdle;
    @Value("${spring.redis.lettuce.pool.minIdle:}")
    private Integer minIdle;
    @Value("${spring.redis.lettuce.pool.maxTotal:}")
    private Integer maxTotal;
    @Value("${spring.redis.lettuce.pool.maxWaitMillis:}")
    private Long maxWaitMillis;
    @Value("${spring.redis.lettuce.pool.maxActive:}")
    private Long maxActive;


    //1.5的版本默认采用的连接池技术是jedis  2.0以上版本默认连接池是lettuce, 在这里采用lecture
    @Bean
    public LettuceConnectionFactory lettuceConnectionFactory() {
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMaxIdle(maxIdle);
        poolConfig.setMinIdle(minIdle);
        poolConfig.setMaxTotal(maxTotal);
        poolConfig.setMaxWaitMillis(maxWaitMillis);
        LettucePoolingClientConfiguration lettucePoolingClientConfiguration = LettucePoolingClientConfiguration.builder()
                .poolConfig(poolConfig)
                .build();

        //单机redis
        RedisStandaloneConfiguration redisConfig  = new RedisStandaloneConfiguration();
        redisConfig.setHostName(StringUtils.isBlank(host) ? "localhost":host.split(":")[0]);
        redisConfig.setPort(port != null ? 6379: Integer.valueOf(host.split(":")[1]));
        redisConfig.setPassword(StringUtils.isNotBlank(password) ? password : "");

        // 集群redis
        /*RedisClusterConfiguration redisConfig = new RedisClusterConfiguration();
        Set<RedisNode> nodeses = new HashSet<>();*/
        return new LettuceConnectionFactory(redisConfig, lettucePoolingClientConfiguration);
    }

    @Bean
    public RedisTemplate<String,Object> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
        RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);

        //解决查询缓存转换异常的问题,设置序列化。
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer(om);

        //配置redisTemplate
        //redisTemplate.setDefaultSerializer(genericJackson2JsonRedisSerializer);
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(genericJackson2JsonRedisSerializer);
        redisTemplate.setHashValueSerializer(genericJackson2JsonRedisSerializer);
        return redisTemplate;
    }


}




