package com.sparta.quokkatravel.domain.rabbitmq.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.quokkatravel.domain.coupon.dto.request.MessageRequestDto;
import com.sparta.quokkatravel.domain.rabbitmq.dto.MessageRes;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProducerServiceImpl implements ProducerService {

    private final RabbitTemplate rabbitTemplate;

    @Override
    public MessageRes sendMessage(MessageRequestDto messageDto) {
        try {
            // 객체를 JSON 으로 변환
            ObjectMapper objectMapper = new ObjectMapper();
            String objectToJSON = objectMapper.writeValueAsString(messageDto);
            rabbitTemplate.convertAndSend("queue1-exchange-name", "queue1.key", objectToJSON);
        } catch (JsonProcessingException jpe) {
            System.out.println("Parsing error: " + jpe.getMessage());
        }
        return new MessageRes(messageDto.getTitle(), messageDto.getContent());
    }
}
