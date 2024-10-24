package com.sparta.quokkatravel.domain.chat.entity;

import com.sparta.quokkatravel.domain.common.timestamped.Timestamped;
import com.sparta.quokkatravel.domain.user.entity.User;
import com.sparta.quokkatravel.domain.user.entity.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.socket.WebSocketSession;

import java.net.http.WebSocket;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
public class ChatRoom extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 방 제목
    @Column(nullable = false)
    private String title;

    // 방 소유자 (User 객체로 변경)
    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner; // ownerId에서 User 객체로 변경

    // 참여자 리스트 추가
    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL)
    private List<ChatParticipant> participants = new ArrayList<>();


    // 생성자: 채팅방 제목을 받아서 ChatRoom 객체 생성
    public ChatRoom(String title, User owner) {
        this.title = title;
        this.owner = owner;
    }

}