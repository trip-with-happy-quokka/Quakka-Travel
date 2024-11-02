package com.sparta.quokkatravel.domain.common.elasticsearch.mapper;

import com.sparta.quokkatravel.domain.coupon.entity.Coupon;
import com.sparta.quokkatravel.domain.search.document.CouponDocument;
import org.springframework.stereotype.Component;

@Component
public class CouponMapper implements EntityToDocumentMapper<Coupon, CouponDocument> {

    @Override
    public CouponDocument toDocument(Coupon entity) {
        return new CouponDocument(entity);
    }
}
