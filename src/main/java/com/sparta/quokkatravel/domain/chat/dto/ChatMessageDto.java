package com.sparta.quokkatravel.domain.chat.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChatMessageDto {
    private String sender;
    private String receiver;
    private String text;
    private String chatRoomId;
}
