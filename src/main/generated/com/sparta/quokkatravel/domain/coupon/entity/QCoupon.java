package com.sparta.quokkatravel.domain.coupon.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCoupon is a Querydsl query type for Coupon
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCoupon extends EntityPathBase<Coupon> {

    private static final long serialVersionUID = 1082512827L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCoupon coupon = new QCoupon("coupon");

    public final com.sparta.quokkatravel.domain.common.timestamped.QTimestamped _super = new com.sparta.quokkatravel.domain.common.timestamped.QTimestamped(this);

    public final com.sparta.quokkatravel.domain.accommodation.entity.QAccommodation accommodation;

    public final StringPath code = createString("code");

    public final StringPath content = createString("content");

    public final EnumPath<CouponStatus> couponStatus = createEnum("couponStatus", CouponStatus.class);

    public final EnumPath<CouponType> couponType = createEnum("couponType", CouponType.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final com.sparta.quokkatravel.domain.user.entity.QUser createdBy;

    public final DateTimePath<java.time.LocalDateTime> deletedAt = createDateTime("deletedAt", java.time.LocalDateTime.class);

    public final NumberPath<Integer> discountAmount = createNumber("discountAmount", Integer.class);

    public final NumberPath<Integer> discountRate = createNumber("discountRate", Integer.class);

    public final com.sparta.quokkatravel.domain.event.entity.QEvent event;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isAvailable = createBoolean("isAvailable");

    public final BooleanPath isDeleted = createBoolean("isDeleted");

    public final StringPath name = createString("name");

    public final com.sparta.quokkatravel.domain.user.entity.QUser owner;

    public final DateTimePath<java.time.LocalDateTime> registeredAt = createDateTime("registeredAt", java.time.LocalDateTime.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final DatePath<java.time.LocalDate> validFrom = createDate("validFrom", java.time.LocalDate.class);

    public final DatePath<java.time.LocalDate> validUntil = createDate("validUntil", java.time.LocalDate.class);

    public QCoupon(String variable) {
        this(Coupon.class, forVariable(variable), INITS);
    }

    public QCoupon(Path<? extends Coupon> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCoupon(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCoupon(PathMetadata metadata, PathInits inits) {
        this(Coupon.class, metadata, inits);
    }

    public QCoupon(Class<? extends Coupon> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.accommodation = inits.isInitialized("accommodation") ? new com.sparta.quokkatravel.domain.accommodation.entity.QAccommodation(forProperty("accommodation"), inits.get("accommodation")) : null;
        this.createdBy = inits.isInitialized("createdBy") ? new com.sparta.quokkatravel.domain.user.entity.QUser(forProperty("createdBy")) : null;
        this.event = inits.isInitialized("event") ? new com.sparta.quokkatravel.domain.event.entity.QEvent(forProperty("event")) : null;
        this.owner = inits.isInitialized("owner") ? new com.sparta.quokkatravel.domain.user.entity.QUser(forProperty("owner")) : null;
    }

}

