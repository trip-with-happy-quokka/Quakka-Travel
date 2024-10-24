package com.sparta.quokkatravel.domain.chat.dto;

import com.sparta.quokkatravel.domain.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class ChatRoomDto {

    private String title; // 채팅방 제목
    private Long ownerId; // 방장 아이디

    public ChatRoomDto(String title, Long ownerId ){
        this.title = title;
        this.ownerId = ownerId;
    }
}