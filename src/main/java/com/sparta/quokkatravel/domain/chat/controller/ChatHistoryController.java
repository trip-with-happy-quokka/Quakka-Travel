package com.sparta.quokkatravel.domain.chat.controller;

import com.sparta.quokkatravel.domain.chat.entity.ChatMessage;
import com.sparta.quokkatravel.domain.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/chat")
public class ChatHistoryController {

    private final ChatService chatService;

    @GetMapping("/history/{chatRoomId}")
    public List<ChatMessage> getChatHistory(@PathVariable("chatRoomId") String chatRoomId) {
        return chatService.getActiveMessages(chatRoomId);
    }
}
