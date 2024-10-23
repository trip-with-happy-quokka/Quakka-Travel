package com.sparta.quokkatravel.domain.coupon.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.quokkatravel.domain.coupon.entity.Coupon;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class CouponRepositorySupport extends QuerydslRepositorySupport {

    private final JPAQueryFactory queryFactory;

    public CouponRepositorySupport(JPAQueryFactory queryFactory) {
        super(Coupon.class);
        this.queryFactory = queryFactory;
    }
}
