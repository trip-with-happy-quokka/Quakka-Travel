package com.sparta.quokkatravel.domain.coupon.service;

import com.sparta.quokkatravel.domain.coupon.dto.request.MessageRequestDto;
import com.sparta.quokkatravel.domain.coupon.dto.response.MessageResponseDto;

public interface CouponProducerService {

    MessageResponseDto sendMessage(MessageRequestDto messageDto);
}
