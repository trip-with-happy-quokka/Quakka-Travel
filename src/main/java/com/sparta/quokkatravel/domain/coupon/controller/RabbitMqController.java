package com.sparta.quokkatravel.domain.coupon.controller;

import com.sparta.quokkatravel.domain.common.shared.ApiResponse;
import com.sparta.quokkatravel.domain.coupon.dto.request.MessageRequestDto;
import com.sparta.quokkatravel.domain.coupon.dto.response.MessageResponseDto;
import com.sparta.quokkatravel.domain.coupon.service.CouponProducerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RabbitMqController {

    private final CouponProducerService couponProducerService;

    /**
     * 생산자(Proceduer)가 메시지를 전송합니다.
     *
     * @param messageDto
     * @return
     */
    @PostMapping("/send/message")
    public ResponseEntity<?> sendMessage(
            @RequestBody MessageRequestDto messageDto
    ) {
        MessageResponseDto messageResponseDto = couponProducerService.sendMessage(messageDto);
        return ResponseEntity.ok(ApiResponse.success("메세지 전송 성공", messageResponseDto));
    }
}
