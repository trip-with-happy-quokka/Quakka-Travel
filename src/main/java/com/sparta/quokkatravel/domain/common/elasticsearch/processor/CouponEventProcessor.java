package com.sparta.quokkatravel.domain.common.elasticsearch.processor;

import com.sparta.quokkatravel.domain.common.elasticsearch.mapper.CouponMapper;
import com.sparta.quokkatravel.domain.common.elasticsearch.mapper.EntityToDocumentMapper;
import com.sparta.quokkatravel.domain.coupon.entity.Coupon;
import com.sparta.quokkatravel.domain.coupon.repository.CouponRepository;
import com.sparta.quokkatravel.domain.search.document.CouponDocument;
import com.sparta.quokkatravel.domain.search.repository.CouponSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CouponEventProcessor implements EntityEventProcessor<Coupon, CouponDocument, Long> {

    private final CouponRepository couponRepository;
    private final CouponSearchRepository couponSearchRepository;
    private final CouponMapper couponMapper;

    @Override
    public CrudRepository<Coupon, Long> getRepository() {
        return couponRepository;
    }

    @Override
    public ElasticsearchRepository<CouponDocument, Long> getElasticsearchRepository() {
        return couponSearchRepository;
    }

    @Override
    public EntityToDocumentMapper<Coupon, CouponDocument> getMapper() {
        return couponMapper;
    }
}
