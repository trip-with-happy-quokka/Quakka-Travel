package com.sparta.quokkatravel.domain.chat.entity;

import com.sparta.quokkatravel.domain.chat.dto.ChatMessageDto;
import com.sparta.quokkatravel.domain.common.shared.Timestamped;
import com.sparta.quokkatravel.domain.reservation.entity.Reservation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String sender;

    @Column
    private String receiver;

    @Column
    private String text;

    @Column
    private String chatRoomId;

    @ManyToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @Column
    private Boolean isActive = true; // 메시지 기록 보관 여부

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public ChatMessage(ChatMessageDto chatMessage) {
        this.sender = chatMessage.getSender();
        this.receiver = chatMessage.getReceiver();
        this.text = chatMessage.getText();
        this.chatRoomId = createChatRoomId(chatMessage.getSender(), chatMessage.getReceiver());

    }

    private String createChatRoomId(String sender, String receiver) {
         return sender.compareTo(receiver) < 0 ? sender + "-" + receiver : receiver + "-" + sender;
    }
}
