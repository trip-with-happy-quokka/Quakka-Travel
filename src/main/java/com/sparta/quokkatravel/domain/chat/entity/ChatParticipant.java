package com.sparta.quokkatravel.domain.chat.entity;

import com.sparta.quokkatravel.domain.common.timestamped.Timestamped;
import com.sparta.quokkatravel.domain.user.entity.User;
import com.sparta.quokkatravel.domain.user.entity.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class ChatParticipant extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 여러 참여자가 하나의 채팅방에 속함
    @ManyToOne
    @JoinColumn(name = "chatroom_id", nullable = false)
    private ChatRoom chatRoom;

    // 각 참여자가 한명의 사용자와 연결됨
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // 참가자 역할 (OWNER , USER)
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    // 생성자
    public ChatParticipant(ChatRoom chatRoom, User user, UserRole userRole) {
        this.chatRoom = chatRoom;
        this.user = user;
        this.userRole = userRole;
    }

}
