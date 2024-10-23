package com.sparta.quokkatravel.domain.chat.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ChatReadStatusDto {
    private Long messageId; // 읽은 메세지 ID
    private Long userId; // 읽은 사용자 ID
    private LocalDateTime readAt;

    // 읽음 상태 정보를 담기 위한 생성자
    public ChatReadStatusDto(Long messageId, Long userId, LocalDateTime readAt){
        this.messageId = messageId;
        this.userId = userId;
        this.readAt = readAt;
    }
}
