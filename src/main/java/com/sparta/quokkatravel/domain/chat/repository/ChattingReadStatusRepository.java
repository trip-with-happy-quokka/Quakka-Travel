package com.sparta.quokkatravel.domain.chat.repository;

import com.sparta.quokkatravel.domain.chat.entity.ChattingReadStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ChattingReadStatusRepository extends JpaRepository<ChattingReadStatus, Long> {

    Optional<ChattingReadStatus> findByChatRoomIdAndMessageIdAndUserId(Long chatRoomId, Long messageId, Long userId);

}
