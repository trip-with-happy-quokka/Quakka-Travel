package com.sparta.quokkatravel.domain.chat.service;

import com.sparta.quokkatravel.domain.chat.dto.ChatMessageDto;
import com.sparta.quokkatravel.domain.chat.entity.ChatMessage;
import com.sparta.quokkatravel.domain.chat.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatMessageRepository chatMessageRepository;


    @Override
    public ChatMessage saveMessage(ChatMessageDto message) {
        return chatMessageRepository.save(new ChatMessage(message));
    }

    @Override
    public List<ChatMessage> getActiveMessages(String chatRoomId) {
        return chatMessageRepository.findMessagesByChatRoomId(chatRoomId, true);
    }

    @Override
    public void deactivateExpiredMessages(LocalDateTime endDate) {
        List<ChatMessage> expiredMessages = chatMessageRepository.findAll().stream()
                .filter(m -> m.getReservation().getEndDate().isBefore(ChronoLocalDate.from(endDate)))
                .toList();


        expiredMessages.forEach(m -> m.setIsActive(false));
        chatMessageRepository.saveAll(expiredMessages);
    }
}
