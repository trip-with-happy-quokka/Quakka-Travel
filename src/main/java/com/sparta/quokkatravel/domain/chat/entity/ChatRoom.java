package com.sparta.quokkatravel.domain.chat.entity;

import com.sparta.quokkatravel.domain.common.timestamped.Timestamped;
import com.sparta.quokkatravel.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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

    // 채팅방을 생성한 방장
    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    // 각 방에 여러 참여자 연결될 수 있음
    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChatParticipant> participants;

    // 각 방에 여러 메시지 저장될 수 있음
    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Chatting> messages;

    public ChatRoom(String title, User createdBy) {
        this.title = title;
        this.createdBy = createdBy;
    }

}