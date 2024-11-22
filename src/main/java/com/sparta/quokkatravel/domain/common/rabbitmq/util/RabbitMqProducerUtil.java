package com.sparta.quokkatravel.domain.common.rabbitmq.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class RabbitMqProducerUtil {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public RabbitMqProducerUtil(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendMessageToQueue(String exchangeName, String routingKey, Object messageObject) {
        try {
            // 객체를 JSON 문자열로 변환
            String objectToJSON = objectMapper.writeValueAsString(messageObject);
            // Direct Exchange를 통해 메시지 전송
            rabbitTemplate.convertAndSend(exchangeName, routingKey, objectToJSON);
        } catch (JsonProcessingException e) {
            System.out.println("Parsing error: " + e.getMessage());
        }
    }
}
