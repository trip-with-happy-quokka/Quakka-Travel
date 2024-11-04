package com.sparta.quokkatravel.domain.common.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    // STOMP 엔드포인트 설정: 웹소켓 연결을 위한 엔드포인트로 /ws-chat을 정의, SockJS 사용을 허용
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 클라이언트가 연결할 WebSocket 엔드포인트를 정의
        registry.addEndpoint("/chat")
                .setAllowedOriginPatterns("*")
                .withSockJS(); // CORS 허용 범위
    }

    // 메시지 브로커 설정: 클라이언트가 /app으로 시작하는 메시지를 서버로 전송하고, /topic으로 브로드캐스트 가능하게 설정
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {

        // 메모리 기반 메시지 브로커가 해당 api를 구독하고 있는 클라이언트에게 메시지를 전달함
        // to subscriber
        registry.enableSimpleBroker("/topic");
        // 클라이언트로부터 메시지를 받을 api prefix 설정
        // publish
        registry.setApplicationDestinationPrefixes("/app");
    }

}