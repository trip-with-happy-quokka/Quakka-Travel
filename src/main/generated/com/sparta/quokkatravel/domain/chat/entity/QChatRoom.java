package com.sparta.quokkatravel.domain.chat.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QChatRoom is a Querydsl query type for ChatRoom
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChatRoom extends EntityPathBase<ChatRoom> {

    private static final long serialVersionUID = 1677266010L;

    public static final QChatRoom chatRoom = new QChatRoom("chatRoom");

    public final com.sparta.quokkatravel.domain.common.timestamped.QTimestamped _super = new com.sparta.quokkatravel.domain.common.timestamped.QTimestamped(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<Chatting, QChatting> messages = this.<Chatting, QChatting>createList("messages", Chatting.class, QChatting.class, PathInits.DIRECT2);

    public final ListPath<ChatParticipant, QChatParticipant> participants = this.<ChatParticipant, QChatParticipant>createList("participants", ChatParticipant.class, QChatParticipant.class, PathInits.DIRECT2);

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QChatRoom(String variable) {
        super(ChatRoom.class, forVariable(variable));
    }

    public QChatRoom(Path<? extends ChatRoom> path) {
        super(path.getType(), path.getMetadata());
    }

    public QChatRoom(PathMetadata metadata) {
        super(ChatRoom.class, metadata);
    }

}

