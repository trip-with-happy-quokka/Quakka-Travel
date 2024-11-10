package com.sparta.quokkatravel.domain.common.elasticsearch.entity_event;

public class CouponDeletedEvent extends AbstractEntityEvent<Long> {
    public CouponDeletedEvent(Object source, Long couponId) {
        super(source, couponId);
    }
}
