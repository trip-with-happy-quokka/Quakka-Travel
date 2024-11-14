package com.sparta.quokkatravel.domain.common.rabbitmq.service;

import com.sparta.quokkatravel.domain.common.rabbitmq.dto.MessageRes;
import com.sparta.quokkatravel.domain.coupon.dto.request.MessageRequestDto;

public interface ProducerService {

    MessageRes sendMessage(MessageRequestDto messageDto);
}
