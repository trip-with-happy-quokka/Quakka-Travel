package com.sparta.quokkatravel.domain.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sparta.quokkatravel.domain.common.redis.RedisMessageDuplicator;
import com.sparta.quokkatravel.domain.common.redis.RedisMessageSubscriber;
import org.redisson.Redisson;
import org.redisson.api.NatMapper;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.misc.RedisURI;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

@Profile("aws")
@Configuration
@EnableCaching
public class AWSRedisConfig {

    @Value("${spring.data.redis.sentinel.master}")
    private String master;

    @Value("${spring.data.redis.sentinel.nodes[0]}")
    private String node0;

    @Value("${spring.data.redis.sentinel.nodes[1]}")
    private String node1;

    @Value("${spring.data.redis.sentinel.nodes[2]}")
    private String node2;

    private static final String REDISSON_HOST_PREFIX = "redis://";

    private List<String> getNodes() {
        return Arrays.asList(node0, node1, node2);
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration()
                .master("mymaster")
                .sentinel("13.124.1.237", 26379)
                .sentinel("13.124.1.237", 26380)
                .sentinel("13.124.1.237", 26381);

        return new LettuceConnectionFactory(sentinelConfig);
    }

    // 메세지를 받을 때 지정된 메서드를 호출하여 메세지를 처리
    @Bean
    public MessageListenerAdapter messageListenerAdapter(RedisMessageSubscriber subscriber) {
        return new MessageListenerAdapter(subscriber, "handleMessage");
    }

    // 레디스 pub/sub 기능을 사용하여 메세지를 구독하는 리스너들을 관리하는 컨테이너
    // Pattern Topic: notifications 으로 시작하는 주제의 모든 메세지를 수신
    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listenerAdapter, new PatternTopic("notifications.*"));
        return container;
    }

    // 레디스 서버와의 데이터 저장, 조회, 삭제 등을 처리하는 템플릿: 레디스에 저장할 데이터의 키와 값을 직렬화 할때, 다양한 형식으로 직렬화 할 수 있도록 설정
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
        return template;
    }

    // key, value 모두 문자열로 직렬화
    @Bean
    public RedisTemplate<String, String> customStringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        return template;
    }

    // redisTemplate을 사용하여 메세지 중복 감지 및 처리하는 메서드
    @Bean
    public RedisMessageDuplicator redisMessageDuplicator(@Qualifier("customStringRedisTemplate") RedisTemplate<String, String> customStringRedisTemplate) {
        return new RedisMessageDuplicator(customStringRedisTemplate);
    }

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.useSentinelServers()
                .setMasterName("mymaster")
                .addSentinelAddress("redis://13.124.1.237:26379", "redis://13.124.1.237:26380", "redis://13.124.1.237:26381")
                .setNatMapper(new NatMapper() {
                    @Override
                    public RedisURI map(RedisURI redisURI) {
                        if (redisURI.getHost().equals("172.18.0.4")) {
                            return new RedisURI("redis", "13.124.1.237", 26379);
                        } else if (redisURI.getHost().equals("172.18.0.5")) {
                            return new RedisURI("redis://13.124.1.237:26380");
                        } else if (redisURI.getHost().equals("172.18.0.6")) {
                            return new RedisURI("redis://13.124.1.237:26381");
                        }
                        return redisURI;
                    }
                })
                .setCheckSentinelsList(true)
                .setTimeout(30000);
        return Redisson.create(config);
    }

    @Primary
    @Bean(name = "cacheManager")
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {

        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
                .entryTtl(Duration.ofMinutes(30));

        return RedisCacheManager.RedisCacheManagerBuilder
                .fromConnectionFactory(redisConnectionFactory)
                .cacheDefaults(redisCacheConfiguration)
                .build();

    }

    @Bean(name = "adminCacheManager")
    public CacheManager adminCacheManager(RedisConnectionFactory redisConnectionFactory) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // Java8 날짜/시간 모듈 추가
        objectMapper.findAndRegisterModules(); // Jackson 모듈 등록

        GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer(objectMapper);

        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(serializer))
                .entryTtl(Duration.ofMinutes(30)); // 캐시 TTL 설정

        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(redisCacheConfiguration)
                .build();
    }
}
