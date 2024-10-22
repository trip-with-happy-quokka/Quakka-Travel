package com.sparta.quokkatravel.domain.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 489896773L;

    public static final QUser user = new QUser("user");

    public final com.sparta.quokkatravel.domain.common.timestamped.QTimestamped _super = new com.sparta.quokkatravel.domain.common.timestamped.QTimestamped(this);

    public final ListPath<com.sparta.quokkatravel.domain.accommodation.entity.Accommodation, com.sparta.quokkatravel.domain.accommodation.entity.QAccommodation> accommodations = this.<com.sparta.quokkatravel.domain.accommodation.entity.Accommodation, com.sparta.quokkatravel.domain.accommodation.entity.QAccommodation>createList("accommodations", com.sparta.quokkatravel.domain.accommodation.entity.Accommodation.class, com.sparta.quokkatravel.domain.accommodation.entity.QAccommodation.class, PathInits.DIRECT2);

    public final ListPath<com.sparta.quokkatravel.domain.chat.entity.ChatParticipant, com.sparta.quokkatravel.domain.chat.entity.QChatParticipant> chatParticipants = this.<com.sparta.quokkatravel.domain.chat.entity.ChatParticipant, com.sparta.quokkatravel.domain.chat.entity.QChatParticipant>createList("chatParticipants", com.sparta.quokkatravel.domain.chat.entity.ChatParticipant.class, com.sparta.quokkatravel.domain.chat.entity.QChatParticipant.class, PathInits.DIRECT2);

    public final ListPath<com.sparta.quokkatravel.domain.chat.entity.Chatting, com.sparta.quokkatravel.domain.chat.entity.QChatting> chattings = this.<com.sparta.quokkatravel.domain.chat.entity.Chatting, com.sparta.quokkatravel.domain.chat.entity.QChatting>createList("chattings", com.sparta.quokkatravel.domain.chat.entity.Chatting.class, com.sparta.quokkatravel.domain.chat.entity.QChatting.class, PathInits.DIRECT2);

    public final ListPath<com.sparta.quokkatravel.domain.coupon.entity.CouponUser, com.sparta.quokkatravel.domain.coupon.entity.QCouponUser> couponUsers = this.<com.sparta.quokkatravel.domain.coupon.entity.CouponUser, com.sparta.quokkatravel.domain.coupon.entity.QCouponUser>createList("couponUsers", com.sparta.quokkatravel.domain.coupon.entity.CouponUser.class, com.sparta.quokkatravel.domain.coupon.entity.QCouponUser.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isDelete = createBoolean("isDelete");

    public final StringPath name = createString("name");

    public final StringPath password = createString("password");

    public final StringPath phoneNumber = createString("phoneNumber");

    public final ListPath<com.sparta.quokkatravel.domain.reservation.entity.Reservation, com.sparta.quokkatravel.domain.reservation.entity.QReservation> reservations = this.<com.sparta.quokkatravel.domain.reservation.entity.Reservation, com.sparta.quokkatravel.domain.reservation.entity.QReservation>createList("reservations", com.sparta.quokkatravel.domain.reservation.entity.Reservation.class, com.sparta.quokkatravel.domain.reservation.entity.QReservation.class, PathInits.DIRECT2);

    public final ListPath<com.sparta.quokkatravel.domain.review.entity.Review, com.sparta.quokkatravel.domain.review.entity.QReview> reviews = this.<com.sparta.quokkatravel.domain.review.entity.Review, com.sparta.quokkatravel.domain.review.entity.QReview>createList("reviews", com.sparta.quokkatravel.domain.review.entity.Review.class, com.sparta.quokkatravel.domain.review.entity.QReview.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final EnumPath<UserRole> userRole = createEnum("userRole", UserRole.class);

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

