package com.sparta.quokkatravel.domain.rabbitmq.service;

import com.sparta.quokkatravel.domain.coupon.dto.request.MessageRequestDto;
import com.sparta.quokkatravel.domain.rabbitmq.dto.MessageRes;

public interface ProducerService {

    MessageRes sendMessage(MessageRequestDto messageDto);
}
