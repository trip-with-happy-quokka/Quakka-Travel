package com.sparta.quokkatravel.domain.chat.entity;

import com.sparta.quokkatravel.domain.common.timestamped.Timestamped;
import com.sparta.quokkatravel.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Chatting extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 여러 메시지가 하나의 채팅방에 속함
    @ManyToOne
    @JoinColumn(name = "chatroom_id", nullable = false)
    private ChatRoom chatRoom;

    // User와 메시지를 보낸 사용자 연결
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String content;
}
