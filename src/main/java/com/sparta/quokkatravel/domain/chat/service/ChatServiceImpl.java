package com.sparta.quokkatravel.domain.chat.service;

import com.sparta.quokkatravel.domain.chat.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatMessageRepository chatMessageRepository;

    public void cleanUpExpiredMessages() {

    }

}
