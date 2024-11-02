package com.sparta.quokkatravel.domain.common.elasticsearch.entity_event;

public class CouponUpdatedEvent extends AbstractEntityEvent<Long> {
    public CouponUpdatedEvent(Object source, Long couponId) {
        super(source, couponId);
    }
}
