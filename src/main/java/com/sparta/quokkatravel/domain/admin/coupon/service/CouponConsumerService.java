package com.sparta.quokkatravel.domain.admin.coupon.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CouponConsumerService {

    @RabbitListener(queues = "coupon-issue-queue")
    public void receiveMessage(String message) {
        log.info("Consume result : {}", message);
    }
}
