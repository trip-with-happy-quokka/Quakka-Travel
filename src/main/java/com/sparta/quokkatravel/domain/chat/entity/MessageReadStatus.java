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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "messageId", nullable = false)
    private Chatting message;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @Column(nullable = false)
    private LocalDateTime readAt;

    public MessageReadStatus(Chatting messsage, User user){
        this.message = message;
        this.user = user;
        this.readAt = LocalDateTime.now();
    }

}
