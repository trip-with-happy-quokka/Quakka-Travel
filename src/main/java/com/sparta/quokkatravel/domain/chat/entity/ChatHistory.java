package com.sparta.quokkatravel.domain.chat.entity;

import com.sparta.quokkatravel.domain.chat.dto.ChatMessage;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class ChatHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Enumerated(EnumType.STRING)
    private ChatMessage.MessageType type;

    @Column
    private String sender;

    @Column
    private String message;

    @ManyToOne
    @JoinColumn(name = "chatroom_id", nullable = false)
    private ChatRoom chatRoom;

    public ChatHistory(ChatMessage.MessageType type, String sender, String message, ChatRoom chatRoom) {
        this.type = type;
        this.sender = sender;
        this.message = message;
        this.chatRoom = chatRoom;
    }
}
