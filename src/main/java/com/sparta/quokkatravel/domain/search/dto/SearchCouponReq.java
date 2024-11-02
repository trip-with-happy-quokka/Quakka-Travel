package com.sparta.quokkatravel.domain.search.dto;

import com.sparta.quokkatravel.domain.coupon.entity.CouponStatus;
import com.sparta.quokkatravel.domain.coupon.entity.CouponType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SearchCouponReq {
    private String name;
    private CouponType couponType;
    private CouponStatus couponStatus;
}
