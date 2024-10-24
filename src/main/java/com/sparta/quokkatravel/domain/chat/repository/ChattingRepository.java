package com.sparta.quokkatravel.domain.chat.repository;

import com.sparta.quokkatravel.domain.chat.entity.ChatRoom;
import com.sparta.quokkatravel.domain.chat.entity.Chatting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChattingRepository extends JpaRepository<Chatting, Long> {
    // 특정 채팅방에 있는 모든 메시지 조회
    List<Chatting> findByChatRoom(ChatRoom chatRoom);

}
