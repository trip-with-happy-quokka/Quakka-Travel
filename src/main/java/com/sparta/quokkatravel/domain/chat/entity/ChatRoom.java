package com.sparta.quokkatravel.domain.chat.entity;

import com.sparta.quokkatravel.domain.chat.dto.ChatMessage;
import com.sparta.quokkatravel.domain.chat.service.ChatService;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;

@Getter
@Entity
@NoArgsConstructor
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String chatRoomName;

    @Transient
    private Set<WebSocketSession> sessions = new HashSet<>();

    @Builder
    public ChatRoom(String chatRoomName) {
        this.chatRoomName = chatRoomName;
    }

    public void handleActions(WebSocketSession session, ChatMessage chatMessage, ChatService chatService) {
        if (chatMessage.getMessageType() == ChatMessage.MessageType.ENTER) {
            sessions.add(session);
            chatMessage.setMessage(chatMessage.getSender() + "님이 입장했습니다.");
        } else if (chatMessage.getMessageType() == ChatMessage.MessageType.EXIT) {
            sessions.remove(session);
            chatMessage.setMessage(chatMessage.getSender() + "님이 퇴장했습니다.");
        }
        sendMessage(chatMessage, chatService);
    }

    public <T> void sendMessage(ChatMessage message, ChatService chatService) {
        sessions.parallelStream().forEach(session -> chatService.sendMessage(session, message));
    }


}