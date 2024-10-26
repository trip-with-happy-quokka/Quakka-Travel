package com.sparta.quokkatravel.domain.common.config;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
    @Value("${spring.data.redis.host}")
    private String redisHost;
    @Value("${spring.data.redis.port}")
    private int redisPort;
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(redisHost, redisPort); // Lettuce로 변경
    } // jdbc 보다 lettuce 가 성능이 훨씬 좋아서 이걸 사용
    // redisHost(로컬호스트값이 밸류로 들어가있음- yml으로 가져다 쓴것), redisPort(6379)
    @Bean
    public MessageListenerAdapter messageListenerAdapter(RedisMessageSubscriber subscriber) {
        return new MessageListenerAdapter(subscriber, "handleMessage");
    } // 메세지 리스터너를 생성 : 역할: 처리할 리스너를 설정하는 것
    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listenerAdapter, new PatternTopic("notifications.*"));
        return container;
    } // 패턴토픽: 노티피케이션 패턴을 가진 메세지의 주제를 구독하는 것 레디스에서 발행된 메세지를 수신하고 등록된 리스너로 전달 한 것 (등록된 리스너는 슬랙이니까 슬랙으로 처리)
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        // Redis Key와 Value에 대한 Serializer 설정
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
        // Hash 타입의 경우에도 동일하게 Serializer 설정
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
        return template;
    } // 직렬하는 것
    @Bean
    public RedisTemplate<String, String> customStringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        return template;
    }
    @Bean
    public RedisMessageDuplicator redisMessageDuplicator(@Qualifier("customStringRedisTemplate") RedisTemplate<String, String> customStringRedisTemplate) {
        return new RedisMessageDuplicator(customStringRedisTemplate);
    } // 메세지 중복 처리를 위해; 메세지 중복에 대해 줄여주는 메서드
}
