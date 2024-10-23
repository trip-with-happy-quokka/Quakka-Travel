package com.sparta.quokkatravel.domain.event.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QEvent is a Querydsl query type for Event
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEvent extends EntityPathBase<Event> {

    private static final long serialVersionUID = -203556651L;

    public static final QEvent event = new QEvent("event");

    public final StringPath content = createString("content");

    public final ListPath<com.sparta.quokkatravel.domain.coupon.entity.Coupon, com.sparta.quokkatravel.domain.coupon.entity.QCoupon> coupons = this.<com.sparta.quokkatravel.domain.coupon.entity.Coupon, com.sparta.quokkatravel.domain.coupon.entity.QCoupon>createList("coupons", com.sparta.quokkatravel.domain.coupon.entity.Coupon.class, com.sparta.quokkatravel.domain.coupon.entity.QCoupon.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public QEvent(String variable) {
        super(Event.class, forVariable(variable));
    }

    public QEvent(Path<? extends Event> path) {
        super(path.getType(), path.getMetadata());
    }

    public QEvent(PathMetadata metadata) {
        super(Event.class, metadata);
    }

}

