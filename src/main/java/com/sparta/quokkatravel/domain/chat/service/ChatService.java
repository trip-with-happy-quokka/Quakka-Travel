package com.sparta.quokkatravel.domain.chat.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.quokkatravel.domain.chat.dto.ChatMessage;
import com.sparta.quokkatravel.domain.chat.dto.ChatRoomResponseDto;
import com.sparta.quokkatravel.domain.chat.entity.ChatHistory;
import com.sparta.quokkatravel.domain.chat.entity.ChatRoom;
import com.sparta.quokkatravel.domain.chat.repository.ChatMessageRepository;
import com.sparta.quokkatravel.domain.chat.repository.ChatRoomRepository;
import com.sparta.quokkatravel.domain.user.entity.User;
import com.sparta.quokkatravel.domain.user.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {

    private final ObjectMapper objectMapper;
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;
    private final ChatMessageRepository chatMessageRepository;
    private Map<String, ChatRoom> chatRooms;

    @PostConstruct
    private void init() {
        chatRooms = new LinkedHashMap<>();
    }


    public ChatRoom findRoom(Long chatroomId) {
        return chatRoomRepository.findById(chatroomId).orElse(null);
    }
    public List<ChatRoomResponseDto> findAllRoom() {
        return chatRoomRepository.findAll().stream().map(ChatRoomResponseDto::new).toList();
    }


    // 오픈 채팅방 생성
    public ChatRoom createOpenChatRoom(String name) {
        ChatRoom chatRoom = ChatRoom.builder()
                .chatRoomName(name)
                .build();

        chatRoomRepository.save(chatRoom);
        return chatRoom;
    }

    // 사용자 입장
    public void joinChatRoom(String email, Long chatRoomId, WebSocketSession session) {
        User user = userRepository.findByEmailOrElseThrow(email);
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElseThrow();

        ChatMessage enterMessage = new ChatMessage();
        enterMessage.setMessageType(ChatMessage.MessageType.ENTER);
        enterMessage.setSender(user.getNickname());
        chatRoom.handleActions(session, enterMessage, this);

    }

    // 사용자 퇴장
    public void exitChatRoom(String email, Long chatRoomId, WebSocketSession session) {
        User user = userRepository.findByEmailOrElseThrow(email);
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElseThrow();

        ChatMessage exitMessage = new ChatMessage();
        exitMessage.setMessageType(ChatMessage.MessageType.EXIT);
        exitMessage.setSender(user.getNickname());
        chatRoom.handleActions(session, exitMessage, this);
        chatRoom.getSessions().remove(session); // 세션 제거

    }

    /*-------------------------------------이 밑으로는 개인 채팅방 관련 메서드입니다.----------------------------------------*/


//    // 개인 채팅방 생성
//    public ChatRoom createPrivateChatRoom(String email, String name) {
//        User user = userRepository.findByEmailOrElseThrow(email);
//
//        String randomId = UUID.randomUUID().toString();
//        ChatRoom chatRoom = ChatRoom.builder()
//                .chatRoomName(name)
//                .user(User)
//                .build();
//
//        chatRoomRepository.save(chatRoom);
//        return chatRoom;
//    }
//
//    // 개인 채팅방 삭제
//    public void deleteChatRoom(String email, String roomId) {
//        ChatRoom chatRoom = chatRoomRepository.findByRoomId(roomId);
//
//        if(!email.equals(chatRoom.getUser().getEmail())) {
//            throw new RuntimeException("Invalid email");
//        }
//
//        // 모든 세션에 방이 삭제되었다는 메시지를 전송
//        ChatMessage deleteMessage = new ChatMessage();
//        deleteMessage.setType(ChatMessage.MessageType.TALK); // TALK 타입을 활용하여 삭제 알림
//        deleteMessage.setMessage("채팅방이 삭제되었습니다.");
//        chatRoom.sendMessage(deleteMessage, this);
//
//        // 메모리와 DB에서 채팅방 삭제
//        chatRoomRepository.deleteByRoomId(roomId);
//
//    }
//
//
//
//    // 사용자 초대
//    public void inviteUsers(String roomId, String inviter, InviteRequestDto inviteRequestDto) {
//        ChatRoom chatRoom = chatRoomRepository.findByRoomId(roomId);
//
//        ChatMessage inviteMessage = new ChatMessage();
//        inviteMessage.setType(ChatMessage.MessageType.TALK);
//        inviteMessage.setSender(inviter);
//        inviteMessage.setMessage(invitee + "님이 초대되었습니다.");
//        chatRoom.sendMessage(inviteMessage, this);
//
//    }


    /*----------------------------------------------------------------------------------------------------------------*/

    // 채팅 보내기
    public <T> void sendMessage(WebSocketSession session, T message) {
        try {
            // ChatMessage 객체를 직렬화한 값으로 TextMessage 생성
            ChatMessage chatMessage = (ChatMessage) message;
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));

            ChatRoom chatRoom = chatRoomRepository.findById(chatMessage.getRoomId()).orElseThrow();
            ChatHistory chatHistory = new ChatHistory(chatMessage.getMessageType(), chatMessage.getSender(), chatMessage.getMessage(), chatRoom);
            chatMessageRepository.save(chatHistory);
            log.info("message : " + message);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }


}