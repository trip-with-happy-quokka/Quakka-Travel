package com.sparta.quokkatravel.domain.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    // 메시지 브로커 설정: 클라이언트가 /app으로 시작하는 메시지를 서버로 전송하고, /topi으로 브로드캐스트 가능하게 설정
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic"); // 구독 경로로 메시지 전달
        config.setApplicationDestinationPrefixes("/app"); // 클라이언트에서 메시지를 보낼 떄의 경로 설정
    }

    // STOMP 엔드포인트 설정: 웹소켓 연결을 위한 엔드포인트로 /ws-chat을 정의, SockJS 사용을 허용
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 클라이언트가 연결할 WebSocket 엔드포인트를 정의
        registry.addEndpoint("/ws-chat")
                .setAllowedOriginPatterns("*").withSockJS(); // SockJS 사용
    }

}