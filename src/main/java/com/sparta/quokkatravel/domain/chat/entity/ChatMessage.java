package com.sparta.quokkatravel.domain.chat.entity;

import com.sparta.quokkatravel.domain.chat.dto.ChatMessageDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "ChatMessage")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@EntityListeners(value = {AuditingEntityListener.class})
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JoinColumn(name = "roomId", insertable = false, updatable = false)
    private String roomId; //단순히 ID 값만 필요  (ChatRoom)

    @JoinColumn(name = "authorId", insertable = false, updatable = false)
    private String authorId; //단순히 ID 값만 필요 (User)

    @Column(name = "message")
    private String message;

    @Column(name = "createdAt", updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    public ChatMessage(ChatMessageDto chatMessageDto) {
        this.roomId = chatMessageDto.getRoomId();
        this.authorId = chatMessageDto.getAuthorId();
        this.message = chatMessageDto.getMessage();
    }
}
