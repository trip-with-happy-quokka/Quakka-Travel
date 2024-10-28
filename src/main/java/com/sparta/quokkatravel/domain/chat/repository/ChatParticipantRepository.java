package com.sparta.quokkatravel.domain.chat.repository;

import com.sparta.quokkatravel.domain.chat.entity.ChatParticipant;
import com.sparta.quokkatravel.domain.chat.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatParticipantRepository extends JpaRepository<ChatParticipant, Long> {
    // ChatRoom 객체와 userId로 참여자 찾기
    Optional<ChatParticipant> findByChatRoomIdAndUserEmail(Long chatRoomId, String email);

    // chatRoomId로 모든 참여자 찾기
    List<ChatParticipant> findByChatRoomId(Long chatRoomId);

}