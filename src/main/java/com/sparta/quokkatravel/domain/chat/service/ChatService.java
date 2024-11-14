package com.sparta.quokkatravel.domain.chat.service;

import com.sparta.quokkatravel.domain.chat.dto.ChatMessageDto;
import com.sparta.quokkatravel.domain.chat.entity.ChatMessage;

import java.time.LocalDateTime;
import java.util.List;

public interface ChatService {

    ChatMessage saveMessage(ChatMessageDto message);

    List<ChatMessage> getActiveMessages(String chatRoomId);

    void deactivateExpiredMessages(LocalDateTime endData);
}
