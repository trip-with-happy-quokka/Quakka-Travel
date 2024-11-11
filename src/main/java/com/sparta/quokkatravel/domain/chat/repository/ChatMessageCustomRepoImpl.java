package com.sparta.quokkatravel.domain.chat.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.quokkatravel.domain.chat.entity.ChatMessage;
import com.sparta.quokkatravel.domain.chat.entity.QChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ChatMessageCustomRepoImpl implements ChatMessageCustomRepo {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ChatMessage> findMessagesByChatRoomId(String chatRoomId, Boolean isActive) {

        QChatMessage qchatMessage = QChatMessage.chatMessage;

        return queryFactory
                .selectFrom(qchatMessage)
                .where(
                        qchatMessage.chatRoomId.eq(chatRoomId),
                        isActive != null ? qchatMessage.isActive.eq(isActive) : null
                )
                .fetch();
    }
}
