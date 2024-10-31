package com.sparta.quokkatravel.domain.search.repository;

import com.sparta.quokkatravel.domain.coupon.entity.CouponStatus;
import com.sparta.quokkatravel.domain.coupon.entity.CouponType;
import com.sparta.quokkatravel.domain.search.document.CouponDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.util.List;

@EnableElasticsearchRepositories(basePackages = "com/sparta/quokkatravel/domain/search/repository")
public interface CouponSearchRepository extends ElasticsearchRepository<CouponDocument, String> {

    List<CouponDocument> findByNameContainingAndCouponTypeAndCouponStatus(String name, CouponType couponType, CouponStatus couponStatus);
}
