package com.sparta.quokkatravel.domain.chat.repository;

import com.sparta.quokkatravel.domain.chat.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    Optional<ChatRoom> findById(Long chatRoomId);
}