package com.sparta.quokkatravel.domain.common.elasticsearch.listener;

import com.sparta.quokkatravel.domain.common.elasticsearch.processor.CouponEventProcessor;
import com.sparta.quokkatravel.domain.coupon.entity.Coupon;
import com.sparta.quokkatravel.domain.search.document.CouponDocument;
import org.springframework.stereotype.Component;

@Component
public class CouponEventListener extends AbstractEntityEventListener<Coupon, CouponDocument, Long> {
    public CouponEventListener(CouponEventProcessor processor) {
        super(processor);
    }
}
