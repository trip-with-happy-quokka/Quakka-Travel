package com.sparta.quokkatravel.domain.chat.controller;

import com.sparta.quokkatravel.domain.chat.entity.ChatMessage;
import com.sparta.quokkatravel.domain.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat")
    public void chat(ChatMessage chatMessage) {
        // 대화방 ID가 존재하는지 확인하여 생성
        String roomId = chatMessage.getChatRoomId();

        // 메시지 수신자에서 특정 방의 메시지 전송
        messagingTemplate.convertAndSendToUser(
                chatMessage.getReceiver(),"/queue/messages/" + roomId, chatMessage);
    }

}