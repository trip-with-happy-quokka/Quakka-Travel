package com.sparta.quokkatravel.domain.chat.service;

import com.sparta.quokkatravel.domain.chat.dto.ChatMessageDto;
import com.sparta.quokkatravel.domain.chat.entity.ChatMessage;

public interface ChatMessageService {

    ChatMessage createChatMessage(ChatMessageDto chatMessageDto);
}
