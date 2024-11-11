package com.sparta.quokkatravel.domain.coupon.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.quokkatravel.domain.coupon.dto.request.MessageRequestDto;
import com.sparta.quokkatravel.domain.coupon.dto.response.MessageResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CouponProducerServiceImpl implements CouponProducerService {

    private final RabbitTemplate rabbitTemplate;

    @Override
    public MessageResponseDto sendMessage(MessageRequestDto messageDto) {
        try {
            // 객체를 JSON 으로 변환
            ObjectMapper objectMapper = new ObjectMapper();
            String objectToJSON = objectMapper.writeValueAsString(messageDto);
            rabbitTemplate.convertAndSend("coupon-issue-exchange", "coupon.key", objectToJSON);
        } catch (JsonProcessingException jpe) {
            System.out.println("Parsing error: " + jpe.getMessage());
        }
        return new MessageResponseDto(messageDto.getTitle(), messageDto.getContent());
    }
}
