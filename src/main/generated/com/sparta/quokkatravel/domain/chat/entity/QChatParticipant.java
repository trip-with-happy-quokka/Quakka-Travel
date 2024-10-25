package com.sparta.quokkatravel.domain.chat.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QChatParticipant is a Querydsl query type for ChatParticipant
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChatParticipant extends EntityPathBase<ChatParticipant> {

    private static final long serialVersionUID = 1689077460L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QChatParticipant chatParticipant = new QChatParticipant("chatParticipant");

    public final com.sparta.quokkatravel.domain.common.timestamped.QTimestamped _super = new com.sparta.quokkatravel.domain.common.timestamped.QTimestamped(this);

    public final NumberPath<Long> chatParticipantId = createNumber("chatParticipantId", Long.class);

    public final QChatRoom chatRoom;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final com.sparta.quokkatravel.domain.user.entity.QUser user;

    public final EnumPath<com.sparta.quokkatravel.domain.user.entity.UserRole> userRole = createEnum("userRole", com.sparta.quokkatravel.domain.user.entity.UserRole.class);

    public QChatParticipant(String variable) {
        this(ChatParticipant.class, forVariable(variable), INITS);
    }

    public QChatParticipant(Path<? extends ChatParticipant> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QChatParticipant(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QChatParticipant(PathMetadata metadata, PathInits inits) {
        this(ChatParticipant.class, metadata, inits);
    }

    public QChatParticipant(Class<? extends ChatParticipant> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.chatRoom = inits.isInitialized("chatRoom") ? new QChatRoom(forProperty("chatRoom"), inits.get("chatRoom")) : null;
        this.user = inits.isInitialized("user") ? new com.sparta.quokkatravel.domain.user.entity.QUser(forProperty("user")) : null;
    }

}

