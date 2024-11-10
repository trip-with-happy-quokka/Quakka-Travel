package com.sparta.quokkatravel.domain.chat.controller;

import com.sparta.quokkatravel.domain.chat.dto.CreateChatRoomRequestDto;
import com.sparta.quokkatravel.domain.chat.dto.CreateChatRoomResponse;
import com.sparta.quokkatravel.domain.chat.service.ChatRoomService;
import com.sparta.quokkatravel.domain.common.jwt.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/chatrooms")
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @PostMapping("/personal") //개인 DM 채팅방 생성
    public CreateChatRoomResponse createPersonalChatRoom(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                         @RequestBody CreateChatRoomRequestDto request) {
        return chatRoomService.createChatRoomForPersonal(userDetails.getUserId(), request);
    }
}
