package com.sparta.quokkatravel.domain.chat.service;

import com.sparta.quokkatravel.domain.chat.dto.CreateChatRoomRequestDto;
import com.sparta.quokkatravel.domain.chat.dto.CreateChatRoomResponse;

public interface ChatRoomService {

    CreateChatRoomResponse createChatRoomForPersonal(Long id, CreateChatRoomRequestDto chatRoomRequest);
}
