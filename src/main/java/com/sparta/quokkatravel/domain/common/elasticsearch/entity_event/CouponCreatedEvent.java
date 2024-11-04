package com.sparta.quokkatravel.domain.common.elasticsearch.entity_event;

public class CouponCreatedEvent extends AbstractEntityEvent<Long> {
    public CouponCreatedEvent(Object source, Long couponId) {
        super(source, couponId);
    }
}
