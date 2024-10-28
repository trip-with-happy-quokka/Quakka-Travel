package com.sparta.quokkatravel.domain.accommodation.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAccommodation is a Querydsl query type for Accommodation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAccommodation extends EntityPathBase<Accommodation> {

    private static final long serialVersionUID = -399876395L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAccommodation accommodation = new QAccommodation("accommodation");

    public final com.sparta.quokkatravel.domain.common.timestamped.QTimestamped _super = new com.sparta.quokkatravel.domain.common.timestamped.QTimestamped(this);

    public final StringPath address = createString("address");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imageurl = createString("imageurl");

    public final StringPath name = createString("name");

    public final NumberPath<Long> rating = createNumber("rating", Long.class);

    public final ListPath<com.sparta.quokkatravel.domain.review.entity.Review, com.sparta.quokkatravel.domain.review.entity.QReview> reviews = this.<com.sparta.quokkatravel.domain.review.entity.Review, com.sparta.quokkatravel.domain.review.entity.QReview>createList("reviews", com.sparta.quokkatravel.domain.review.entity.Review.class, com.sparta.quokkatravel.domain.review.entity.QReview.class, PathInits.DIRECT2);

    public final ListPath<com.sparta.quokkatravel.domain.room.entity.Room, com.sparta.quokkatravel.domain.room.entity.QRoom> rooms = this.<com.sparta.quokkatravel.domain.room.entity.Room, com.sparta.quokkatravel.domain.room.entity.QRoom>createList("rooms", com.sparta.quokkatravel.domain.room.entity.Room.class, com.sparta.quokkatravel.domain.room.entity.QRoom.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final com.sparta.quokkatravel.domain.user.entity.QUser user;

    public QAccommodation(String variable) {
        this(Accommodation.class, forVariable(variable), INITS);
    }

    public QAccommodation(Path<? extends Accommodation> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAccommodation(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAccommodation(PathMetadata metadata, PathInits inits) {
        this(Accommodation.class, metadata, inits);
    }

    public QAccommodation(Class<? extends Accommodation> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.sparta.quokkatravel.domain.user.entity.QUser(forProperty("user")) : null;
    }

}

