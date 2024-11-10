package com.sparta.quokkatravel.domain.chat.service;

import com.sparta.quokkatravel.domain.chat.dto.CreateChatRoomRequestDto;
import com.sparta.quokkatravel.domain.chat.dto.CreateChatRoomResponse;
import com.sparta.quokkatravel.domain.chat.entity.ChatRoom;
import com.sparta.quokkatravel.domain.chat.repository.ChatRoomRepository;
import com.sparta.quokkatravel.domain.common.exception.NotFoundException;
import com.sparta.quokkatravel.domain.user.entity.User;
import com.sparta.quokkatravel.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {

    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;

    @Override // 개인 DM방 생성
    public CreateChatRoomResponse createChatRoomForPersonal(Long userId, CreateChatRoomRequestDto chatRoomRequest) {

        User roomMaker = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        User guest = userRepository.findByEmail(chatRoomRequest.getGuestEmail()).orElseThrow(() -> new NotFoundException("User not found"));

        ChatRoom newRoom = ChatRoom.create();
        newRoom.addMembers(roomMaker, guest);

        chatRoomRepository.save(newRoom);

        return new CreateChatRoomResponse(roomMaker.getId(), guest.getId(), newRoom.getId().toString());
    }
}
