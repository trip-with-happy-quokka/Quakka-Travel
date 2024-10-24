package com.sparta.quokkatravel.domain.chat.controller;
import com.sparta.quokkatravel.domain.chat.dto.ChatMessageDto;
import com.sparta.quokkatravel.domain.chat.dto.ChatRoomDto;
import com.sparta.quokkatravel.domain.chat.entity.Chatting;
import com.sparta.quokkatravel.domain.chat.service.ChatService;
import com.sparta.quokkatravel.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.*;
@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final UserRepository userRepository;

    // 채팅방 생성
    @PostMapping("/chattings/room")
    public void createChatRoom(@RequestBody ChatRoomDto chatRoomDto) {
        chatService.creatChatRoom(chatRoomDto);
    }

    // 채팅방 참여
    @PostMapping("/rooms/{chatRoodId}/join")
    public ResponseEntity<String> joinChatRoom(@PathVariable Long chatRoomId, @RequestParam Long userId){
        chatService.joinChatRoom(chatRoomId, userId);
        return ResponseEntity.ok("채팅방에 참여했습니다.");
    }

    // 채팅방 삭제 (방장만)
    @DeleteMapping("/chattings/room/{chatRoomId}")
    public void deleteChatRoom(@PathVariable Long chatRoomId, @RequestParam Long userId) {
        chatService.deleteChatRoom(chatRoomId, userId);
    }

    // STOMP를 통해 채팅 메시지 전송
    @MessageMapping("/chattings.sendMessage")
    @SendTo("/topic/public")
    public Chatting sendMessage(ChatMessageDto chatMessageDTO, SimpMessageHeaderAccessor headerAccessor) {
        return chatService.saveMessage(chatMessageDTO);
    }
    // 메시지 읽음 처리
    @PostMapping("/chattings/markAsRead")
    public void markMessageAsRead(@RequestParam Long chatRoomId, @RequestParam Long messageId, @RequestParam Long userId) {
        chatService.markMessageAsRead(chatRoomId, messageId, userId);
    }
}