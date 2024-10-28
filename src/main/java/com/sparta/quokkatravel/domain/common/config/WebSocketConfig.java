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
        registry.enableSimpleBroker("/topic"); //해당 주소를 구독하고 있는 클라이언트들에게 메세지 전달
        registry.setApplicationDestinationPrefixes("/app"); //클라이언트에서 보낸 메세지를 받을 prefix
    }

    // STOMP 엔드포인트 설정: 웹소켓 연결을 위한 엔드포인트로 /ws-chat을 정의, SockJS 사용을 허용
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 클라이언트가 연결할 WebSocket 엔드포인트를 정의
        registry.addEndpoint("/ws-chat") //SockJS 연결 주소
                .setAllowedOriginPatterns("*") // CORS 허용 범위
                .withSockJS(); //버전 낮은 브라우저에서도 적용 가능
    }

}