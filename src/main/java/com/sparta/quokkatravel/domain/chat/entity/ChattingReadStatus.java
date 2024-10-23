package com.sparta.quokkatravel.domain.chat.entity;

import jakarta.persistence.*;
import com.sparta.quokkatravel.domain.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class ChattingReadStatus {

    // 읽은 상태 ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 읽은 메세지 정보
    @ManyToOne
    @JoinColumn(name = "messageId", nullable = false)
    private Chatting message;

    @ManyToOne
    @JoinColumn(name = "chatRoom_id", nullable = false)
    private ChatRoom chatRoom;

    // 읽은 사용자 정보
    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    // 메세지 읽은 시간
    @Column(nullable = false)
    private LocalDateTime readAt;

    public ChattingReadStatus(ChatRoom chatRoom,Chatting message, User user){
        this.chatRoom = chatRoom;
        this.message = message;
        this.user = user;
        this.readAt = LocalDateTime.now();
    }

    // 메세지 읽음으로 표시
    public void markAsRead(){
        this.readAt = LocalDateTime.now();
    }

}
