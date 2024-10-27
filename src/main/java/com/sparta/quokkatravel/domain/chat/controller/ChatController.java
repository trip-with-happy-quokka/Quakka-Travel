package com.sparta.quokkatravel.domain.chat.controller;
import com.sparta.quokkatravel.domain.chat.dto.ChatMessageDto;
import com.sparta.quokkatravel.domain.chat.dto.ChatRoomDto;
import com.sparta.quokkatravel.domain.chat.entity.Chatting;
import com.sparta.quokkatravel.domain.chat.service.ChatService;
import com.sparta.quokkatravel.domain.common.dto.CustomUserDetails;
import com.sparta.quokkatravel.domain.user.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
@RestController
@RequiredArgsConstructor
@Tag(name = "Chat", description = "채팅 관련 컨트롤러")
public class ChatController {

    private final ChatService chatService;
    private final UserRepository userRepository;

    // 채팅방 생성
    @PostMapping("/chattings/room")
    @Operation(summary = "채팅방 생성", description = "채팅방을 생성하는 API")
    public ResponseEntity<String> createChatRoom(
            @RequestBody ChatRoomDto chatRoomDto,
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        chatService.createChatRoom(chatRoomDto, customUserDetails);
        return ResponseEntity.status(HttpStatus.CREATED).body("채팅방이 생성되었습니다.");
    }

    // 채팅방 참여
    @PostMapping("/rooms/{chatRoomId}/join")
    @Operation(summary = "채팅방 참여", description = "채팅방을 침여하는 API")
    public ResponseEntity<String> joinChatRoom(@PathVariable Long chatRoomId, @AuthenticationPrincipal CustomUserDetails customUserDetails){
        chatService.joinChatRoom(chatRoomId, customUserDetails);
        return ResponseEntity.ok("채팅방에 참여했습니다.");
    }

    // 채팅방 삭제 (방장만)
    @Operation(summary = "채팅방 삭제", description = "채팅방을 삭제하는 API")
    @DeleteMapping("/chattings/room/{chatRoomId}")
    public void deleteChatRoom(@PathVariable Long chatRoomId, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        chatService.deleteChatRoom(chatRoomId, customUserDetails);
    }

    // STOMP를 통해 채팅 메시지 전송
    @MessageMapping("/chattings.sendMessage")
    @SendTo("/topic/public")
    public Chatting sendMessage(ChatMessageDto chatMessageDTO, SimpMessageHeaderAccessor headerAccessor, @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        // SimpMessageHeaderAccessor를 사용하여 세션 속성 설정
        headerAccessor.getSessionAttributes().put("username", customUserDetails.getUsername());

        // 메시지 저장 및 전송
        return chatService.saveMessage(chatMessageDTO, customUserDetails);
    }

    // 메시지 읽음 처리
    @Operation(summary = "메시지 읽음 처리", description = "특정 메시지를 읽음 처리하는 API")
    @PostMapping("/chattings/markAsRead")
    public ResponseEntity<String> markMessageAsRead(@RequestParam Long chatRoomId, @RequestParam Long messageId, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        chatService.markMessageAsRead(chatRoomId, messageId, customUserDetails.getEmail());
        return ResponseEntity.ok("메시지를 읽음 처리했습니다.");
    }

}