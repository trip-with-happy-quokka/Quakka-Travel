package com.sparta.quokkatravel.domain.chat.service;

import com.sparta.quokkatravel.domain.chat.dto.ChatMessageDto;
import com.sparta.quokkatravel.domain.chat.entity.ChatMessage;
import com.sparta.quokkatravel.domain.chat.entity.ChatRoom;
import com.sparta.quokkatravel.domain.chat.repository.ChatRoomRepository;
import com.sparta.quokkatravel.domain.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatMessageServiceImpl implements ChatMessageService {
    private final ChatRoomRepository chatRoomRepository;

    @Override
    public ChatMessage createChatMessage(ChatMessageDto chatMessageDto) {
        ChatMessage chatMessage = new ChatMessage(chatMessageDto);
        ChatRoom chatRoom = chatRoomRepository.findById(chatMessage.getRoomId()).orElseThrow(() -> new NotFoundException("Chat room not found"));
        chatRoom.setLastChatMesg(chatMessage);
        chatRoomRepository.save(chatRoom);

        return chatMessage;
    }
}
