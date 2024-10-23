package com.sparta.quokkatravel.domain.common.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

//    private final WebSocketHandler webSocketHandler;
//
//    //  Websocket에 접속하기 위한 endpoint 는 /ws/chat 으로 설정하고 도메인이 다른 서버에서도 접속가능하도록 CORS : setAllowedOrigins("*") 설정 추가
//    // 클라이언트가 ws://localhost:8080/ws/chat 으로 커넥션을 연결하고 메시지 통신을 할 수 있는 기본적인 준비
//
//    @Override
//    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry){
//        registry.addHandler(webSocketHandler, "/ws/chat").setAllowedOrigins("*");
//    }

    // 메시지 브로커 설정: 클라이언트가 /app으로 시작하는 메시지를 서버로 전송하고, /topic으로 브로드캐스트 가능하게 설정
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic"); // /top 으로 시작하는 stomp 메세지는 브로커로 라우팅함
        config.setApplicationDestinationPrefixes("/app"); // /app 으로 시작하는 stomp 메세지의 경로는 @controller @MessageMaping 메서드로 라우팅
    }

    // STOMP 엔드포인트 설정: 웹소켓 연결을 위한 엔드포인트로 /ws-chat을 정의, SockJS 사용을 허용
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 클라이언트가 연결할 WebSocket 엔드포인트를 정의
        registry.addEndpoint("/ws-chat")
                .setAllowedOriginPatterns("*") ////sockJs 클라이언트가 websocket handshake로 커넥션할 경로
                .withSockJS(); // 가능한 경로 설정 ( 전체 오픈 : 기호에따라 수정 )
    }

}