package com.sparta.quokkatravel.domain.common.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.quokkatravel.domain.chat.dto.ChatMessage;
import com.sparta.quokkatravel.domain.chat.entity.ChatHistory;
import com.sparta.quokkatravel.domain.chat.entity.ChatRoom;
import com.sparta.quokkatravel.domain.chat.repository.ChatMessageRepository;
import com.sparta.quokkatravel.domain.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
@RequiredArgsConstructor
@Component
public class WebSockChatHandler extends TextWebSocketHandler {
    private final ObjectMapper objectMapper;
    private final ChatService chatService;
    private final ChatMessageRepository chatMessageRepository;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("payload {}", payload);
        ChatMessage chatMessage = objectMapper.readValue(payload, ChatMessage.class);
        ChatRoom room = chatService.findRoom(chatMessage.getRoomId());

        if(room != null) {
            room.handleActions(session, chatMessage, chatService);
            ChatHistory chatHistory = new ChatHistory(chatMessage.getMessageType(), chatMessage.getSender(), chatMessage.getMessage(), room);
            chatMessageRepository.save(chatHistory);
            log.info("chat is successful!");
        } else {
            log.warn("Chat room not found for roomId: {}", chatMessage.getRoomId());
        }

    }
}