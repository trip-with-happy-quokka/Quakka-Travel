package com.sparta.quokkatravel.domain.chat.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateChatRoomResponse {
    private Long roomMakerId;
    private Long guestId;
    private String chatRoomId;

    /* Entity -> Dto */
    public CreateChatRoomResponse(Long roomMakerId, Long guestId, String chatRoomId) {
        this.roomMakerId = roomMakerId;
        this.guestId = guestId;
        this.chatRoomId = chatRoomId;
    }
}