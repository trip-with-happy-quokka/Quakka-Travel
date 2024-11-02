package com.sparta.quokkatravel.domain.search.repository;

import com.sparta.quokkatravel.domain.common.exception.NotFoundException;
import com.sparta.quokkatravel.domain.coupon.entity.CouponStatus;
import com.sparta.quokkatravel.domain.coupon.entity.CouponType;
import com.sparta.quokkatravel.domain.search.document.AccommodationDocument;
import com.sparta.quokkatravel.domain.search.document.CouponDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.util.List;
import java.util.Optional;

@EnableElasticsearchRepositories(basePackages = "com/sparta/quokkatravel/domain/search/repository")
public interface CouponSearchRepository extends ElasticsearchRepository<CouponDocument, Long> {

    List<CouponDocument> findByNameContainingAndCouponTypeAndCouponStatus(String name, CouponType couponType, CouponStatus couponStatus);

    Optional<CouponDocument> findByCouponId(Long couponId);

    default CouponDocument findByCouponIdOrElseThrow(Long couponId) {
        return findByCouponId(couponId).orElseThrow(() -> new NotFoundException("Coupon Not Found"));
    }

}
