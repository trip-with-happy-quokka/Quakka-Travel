package com.sparta.quokkatravel.domain.common.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    // 메시지 브로커 설정: 클라이언트가 /app으로 시작하는 메시지를 서버로 전송하고, /topic으로 브로드캐스트 가능하게 설정
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic"); // /top 으로 시작하는 stomp 메세지는 브로커로 라우팅함
        registry.setApplicationDestinationPrefixes("/app"); // /app 으로 시작하는 stomp 메세지의 경로는 @controller @MessageMaping 메서드로 라우팅
    }

    // STOMP 엔드포인트 설정: 웹소켓 연결을 위한 엔드포인트로 /ws-chat을 정의, SockJS 사용을 허용
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 클라이언트가 연결할 WebSocket 엔드포인트를 정의
        registry.addEndpoint("/ws-chat")
                .setAllowedOriginPatterns("*") // CORS 허용 범위
                .withSockJS(); // 가능한 경로 설정 ( 전체 오픈 : 기호에따라 수정 )
    }

}