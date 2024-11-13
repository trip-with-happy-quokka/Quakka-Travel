package com.sparta.quokkatravel.domain.rabbitmq.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConsumerService {

    @RabbitListener(queues = "queue1-name")
    public void receiveMessage(String message) {
        log.info("Consume result : {}", message);
    }
}
