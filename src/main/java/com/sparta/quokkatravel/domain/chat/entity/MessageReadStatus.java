package com.sparta.quokkatravel.domain.chat.entity;

import jakarta.persistence.*;
import com.sparta.quokkatravel.domain.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class MessageReadStatus {

    // 읽은 상태 ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 읽은 메세지 정보
    @ManyToOne
    @JoinColumn(name = "messageId", nullable = false)
    private Chatting message;

    // 읽은 사용자 정보
    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    // 메세지 읽은 시간
    @Column(nullable = false)
    private LocalDateTime readAt;

    public MessageReadStatus(Chatting messsage, User user){
        this.message = message;
        this.user = user;
        this.readAt = LocalDateTime.now();
    }

}
