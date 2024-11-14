package com.sparta.quokkatravel.domain.common.rabbitmq.service;

import com.sparta.quokkatravel.domain.common.rabbitmq.dto.MessageRes;
import com.sparta.quokkatravel.domain.common.rabbitmq.util.RabbitMqProducerUtil;
import com.sparta.quokkatravel.domain.coupon.dto.request.MessageRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProducerServiceImpl implements ProducerService {

    private final RabbitMqProducerUtil rabbitMqProducerUtil;

    @Override
    public MessageRes sendMessage(MessageRequestDto messageDto) {
        // RabbitMQ - Direct Exchange Binding
        rabbitMqProducerUtil.sendMessageToQueue("coupon-issue-exchange", "coupon.key", messageDto);
        return new MessageRes(messageDto.getTitle(), messageDto.getContent());
    }
}
