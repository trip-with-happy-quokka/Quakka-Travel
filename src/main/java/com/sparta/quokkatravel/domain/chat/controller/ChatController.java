package com.sparta.quokkatravel.domain.chat.controller;


import com.sparta.quokkatravel.domain.chat.dto.ChatRoomResponseDto;
import com.sparta.quokkatravel.domain.chat.service.ChatService;
import com.sparta.quokkatravel.domain.common.dto.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
@Tag(name = "Chat", description = "채팅 관련 컨트롤러")
public class ChatController {

    private final ChatService chatService;

    // 채팅방 생성
    @PostMapping("/chatrooms")
    @Operation(summary = "채팅방 생성", description = "채팅방을 생성하는 API")
    public ResponseEntity<?> createChatRoom(@RequestBody String name) {
        return ResponseEntity.ok().body(chatService.createOpenChatRoom(name));
    }

    // 모든 채팅방 조회
    @GetMapping("/chatrooms")
    @Operation(summary = "채팅방 조회", description = "채팅방을 조회하는 API")
    public ResponseEntity<?> findAllChatRoom() {
        List<ChatRoomResponseDto> chatrooms = chatService.findAllRoom();
        return ResponseEntity.ok().body(chatrooms);
    }

    // 채팅방 참가
    @Operation(summary = "채팅방 참여", description = "채팅방을 침여하는 API")
    public ResponseEntity<?> joinChatRoom(@AuthenticationPrincipal CustomUserDetails userDetails,
                                          @PathVariable Long chatroomId,
                                          WebSocketSession session) {
        String email = userDetails.getUsername();
        chatService.joinChatRoom(email, chatroomId, session);
        return ResponseEntity.ok().body("채팅방에 참여하셨습니다.");
    }

    // 채팅방 퇴장
    @PostMapping("/chatrooms/{chatroomId}/exit")
    public ResponseEntity<?> exitChatRoom(@AuthenticationPrincipal CustomUserDetails userDetails,
                                          @PathVariable Long chatroomId,
                                          WebSocketSession session) {
        String email = userDetails.getUsername();
        chatService.exitChatRoom(email, chatroomId, session);
        return ResponseEntity.ok().body("채팅방을 퇴장하셨습니다.");
    }

    /*-------------------------------------이 밑으로는 개인 채팅방 관련 메서드입니다.----------------------------------------*/

//    // 채팅방 생성
//    @PostMapping("/chatrooms")
//    public ResponseEntity<?> createPrivateChatRoom(@AuthenticationPrincipal CustomUserDetails userDetails,
//                                                   @RequestBody String name) {
//        String email = userDetails.getUsername();
//        return ResponseEntity.ok().body(chatService.createPrivateChatRoom(email, name));
//    }
//
//    // 채팅방 삭제
//    @PostMapping("/chatrooms/{chatroomId}/delete")
//    public ResponseEntity<?> deleteChatRoom(@AuthenticationPrincipal CustomUserDetails userDetails,
//                                            @PathVariable Long chatroomId) {
//        String email = userDetails.getUsername();
//        chatService.deleteChatRoom(email, chatroomId);
//        return ResponseEntity.ok().body("채팅방 삭제");
//    }
//
//    // 채팅방 초대
//    @PostMapping("/chatrooms/{chatroomId}/invite")
//    public ResponseEntity<?> createRoom(@AuthenticationPrincipal CustomUserDetails userDetails,
//                                        @PathVariable Long chatroomId,
//                                        @RequestBody InviteRequestDto inviteRequestDto) {
//        String email = userDetails.getUsername();
//        chatService.inviteUsers(email, chatroomId, inviteRequestDto);
//        return ResponseEntity.ok().body("초대 완료");
//    }

}