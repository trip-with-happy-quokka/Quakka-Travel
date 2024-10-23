package com.sparta.quokkatravel.domain.chat.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChatMessageDto {
    private Long chatRoomId;
    private String sender; // 메세지 전송자
    private String content;

    // 채팅 메세지 정보를 담기 위한 생성자
    public ChatMessageDto(Long chatRoomId, String sender, String content){
        this.chatRoomId = chatRoomId;
        this.sender = sender;
        this.content = content;
    }
}