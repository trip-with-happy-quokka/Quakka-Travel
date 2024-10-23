package com.sparta.quokkatravel.domain.chat.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChatMessageDto {
    private Long chatRoomId; // 채팅방 ID
    private Long userId; // 사용자 ID
    private String content; // 메세지 내용

    // 채팅 메세지 정보를 담기 위한 생성자
    public ChatMessageDto(Long chatRoomId, Long userId, String content){
        this.chatRoomId = chatRoomId;
        this.userId = userId;
        this.content = content;
    }
}