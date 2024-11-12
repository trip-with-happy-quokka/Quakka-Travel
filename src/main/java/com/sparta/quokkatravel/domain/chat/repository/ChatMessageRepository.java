package com.sparta.quokkatravel.domain.chat.repository;

import com.sparta.quokkatravel.domain.chat.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, String>, ChatMessageCustomRepo {

}