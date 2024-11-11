package com.sparta.quokkatravel.domain.chat.repository;

import com.sparta.quokkatravel.domain.chat.entity.ChatMessage;

import java.util.List;

public interface ChatMessageCustomRepo {

    List<ChatMessage> findMessagesByChatRoomId(String chatRoomId, Boolean isActive);
}
