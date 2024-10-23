package com.sparta.quokkatravel.domain.coupon.service;

import com.sparta.quokkatravel.domain.coupon.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl {

    private final CouponRepository couponRepository;
}
