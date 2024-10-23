package com.sparta.quokkatravel.domain.chat.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMessageReadStatus is a Querydsl query type for MessageReadStatus
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMessageReadStatus extends EntityPathBase<MessageReadStatus> {

    private static final long serialVersionUID = -1985416088L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMessageReadStatus messageReadStatus = new QMessageReadStatus("messageReadStatus");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QChatting message;

    public final DateTimePath<java.time.LocalDateTime> readAt = createDateTime("readAt", java.time.LocalDateTime.class);

    public final com.sparta.quokkatravel.domain.user.entity.QUser user;

    public QMessageReadStatus(String variable) {
        this(MessageReadStatus.class, forVariable(variable), INITS);
    }

    public QMessageReadStatus(Path<? extends MessageReadStatus> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMessageReadStatus(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMessageReadStatus(PathMetadata metadata, PathInits inits) {
        this(MessageReadStatus.class, metadata, inits);
    }

    public QMessageReadStatus(Class<? extends MessageReadStatus> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.message = inits.isInitialized("message") ? new QChatting(forProperty("message"), inits.get("message")) : null;
        this.user = inits.isInitialized("user") ? new com.sparta.quokkatravel.domain.user.entity.QUser(forProperty("user")) : null;
    }

}

