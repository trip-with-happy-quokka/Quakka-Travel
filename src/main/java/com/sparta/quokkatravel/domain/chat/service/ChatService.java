package com.sparta.quokkatravel.domain.chat.service;

import com.sparta.quokkatravel.domain.chat.dto.ChatMessageDto;
import com.sparta.quokkatravel.domain.chat.dto.ChatRoomDto;
import com.sparta.quokkatravel.domain.chat.entity.ChatParticipant;
import com.sparta.quokkatravel.domain.chat.entity.ChatRoom;
import com.sparta.quokkatravel.domain.chat.entity.Chatting;
import com.sparta.quokkatravel.domain.chat.entity.ChattingReadStatus;
import com.sparta.quokkatravel.domain.chat.repository.ChatParticipantRepository;
import com.sparta.quokkatravel.domain.chat.repository.ChatRoomRepository;
import com.sparta.quokkatravel.domain.chat.repository.ChattingReadStatusRepository;
import com.sparta.quokkatravel.domain.chat.repository.ChattingRepository;
import com.sparta.quokkatravel.domain.user.entity.User;
import com.sparta.quokkatravel.domain.user.entity.UserRole;
import com.sparta.quokkatravel.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChattingRepository chattingRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChattingReadStatusRepository chattingReadStatusRepository;
    private final ChatParticipantRepository chatParticipantRepository;
    private final UserRepository userRepository;

    // 채팅방 생성
    @Transactional
    public void createChatRoom(ChatRoomDto chatRoomDto) {
        User owner = userRepository.findById(chatRoomDto.getOwnerId())
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));

        ChatRoom chatRoom = new ChatRoom(chatRoomDto.getTitle(), owner);
        chatRoomRepository.save(chatRoom);

        // 방장 역할 부여
        ChatParticipant ownerParticipant = new ChatParticipant(chatRoom, owner, UserRole.OWNER);
        chatParticipantRepository.save(ownerParticipant);

    }

    // 채팅방 참여
    @Transactional
    public void joinChatRoom(Long chatRoomId, Long userId) {
        // 채팅방 찾기
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new IllegalArgumentException("채팅방을 찾을 수 없습니다."));

        // 유저 찾기
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // 이미 채팅방에 참여하고 있는지 확인
        boolean isAlreadyParticipant = chatParticipantRepository.findByChatRoomIdAndUserId(chatRoom.getId(), userId).isPresent();
        if (isAlreadyParticipant) {
            throw new IllegalArgumentException("이미 채팅방에 참여하고 있습니다.");
        }

        // 새로운 참여자 추가 (일반 사용자로 참여)
        ChatParticipant participant = new ChatParticipant(chatRoom, user, UserRole.USER);
        chatParticipantRepository.save(participant);
    }

    // 채팅방 삭제 (방장만 가능)
    public void deleteChatRoom(Long chatRoomId, Long userId) {

        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new IllegalArgumentException("채팅방을 찾을 수 없습니다."));
        ChatParticipant participant = chatParticipantRepository.findByChatRoomIdAndUserId(chatRoomId, userId)
                .orElseThrow(() -> new IllegalArgumentException("참여자를 찾을 수 없습니다."));

        if (participant.getUserRole() != UserRole.OWNER) {
            throw new IllegalArgumentException("채팅방 삭제는 방장만 할 수 있습니다.");
        }

        chatRoomRepository.delete(chatRoom);

    }

    // 채팅 메세지 저장
    public Chatting saveMessage(ChatMessageDto messageDto) {

        ChatRoom chatRoom = chatRoomRepository.findById(messageDto.getChatRoomId())
                .orElseThrow(() -> new IllegalArgumentException("채팅방을 찾을 수 없습니다."));
        User user = userRepository.findById(messageDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // 메세지 저장
        Chatting chatMessage = new Chatting(chatRoom, user, messageDto.getContent());
        chattingRepository.save(chatMessage);

        // 모든 참여자에게 읽지 않은 상태로 저장
        List<ChatParticipant> participants = chatParticipantRepository.findByChatRoomId(chatRoom.getId());
        for (ChatParticipant participant : participants) {
            // 자기자신 제외도 시켜야됨
            if (!participant.getUser().getId().equals(user.getId())) {
                ChattingReadStatus readStatus = new ChattingReadStatus(chatRoom, chatMessage, participant.getUser());
                chattingReadStatusRepository.save(readStatus);
            }
        }

        return chatMessage;
    }

    // 메세지 읽음 처리
    public void markMessageAsRead(Long chatRoomId, Long messageId, Long userId){
        ChattingReadStatus readStatus = chattingReadStatusRepository
                .findByChatRoomIdAndMessageIdAndUserId(chatRoomId,messageId,userId)
                .orElseThrow(() -> new IllegalArgumentException("읽음 상태를 찾을 수 없습니다?"));

        readStatus.markAsRead(); // 시간 기록
        chattingReadStatusRepository.save(readStatus);
    }

    // 특정 채팅방의 모든 메세지 가져오기
    public List<Chatting> getMessages(Long chatRoomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new IllegalArgumentException("채팅방을 찾을 수 없습니다."));
        return chattingRepository.findByChatRoom(chatRoom);
    }

}
