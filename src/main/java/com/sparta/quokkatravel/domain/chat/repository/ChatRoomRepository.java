package com.sparta.quokkatravel.domain.chat.repository;

import com.sparta.quokkatravel.domain.chat.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

}