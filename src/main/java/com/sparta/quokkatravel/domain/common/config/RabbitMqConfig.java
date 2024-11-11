package com.sparta.quokkatravel.domain.common.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.port}")
    private int port;


    @Bean
    public Queue Queue() {
        return new Queue("coupon-issue-queue", true, false, false);
        // QueueName : "coupon-issue-queue"
    }

    // 지정된 Exchange 이름으로 Direct Exchange Bean 생성
    @Bean
    public DirectExchange exchange() {
        return new DirectExchange("coupon-issue-exchange");
        // exchangeName : "coupon-issue-exchange"
    }

    // Exchange 에 Queue 등록
    // == 주어진 Queue 와 Exchange 를 Binding + Routing Key 를 이용하여 Binding Bean 생성
    @Bean
    public Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("coupon.key");
        // routingKey : "hello.key"
    }

    // RabbitMQ 연동을 위한 ConnectionFactory Bean 을 생성하여 반환
    @Bean
    ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(host);
        connectionFactory.setPort(port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        return connectionFactory;
    }

    // 직렬화 : 메세지를 JSON 으로 변환하는 Message Converter
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    // RabbitTemplate : ConnectionFactory 로 연결 후 실제 작업을 위한 Template
    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }

}
