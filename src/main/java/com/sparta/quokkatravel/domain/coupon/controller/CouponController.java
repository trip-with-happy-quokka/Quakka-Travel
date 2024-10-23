package com.sparta.quokkatravel.domain.coupon.controller;

import com.sparta.quokkatravel.domain.coupon.service.CouponService;
import com.sparta.quokkatravel.domain.coupon.service.CouponServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/vi")
@RequiredArgsConstructor
@Tag(name = "Coupon", description = "쿠폰 관련 컨트롤러")
public class CouponController {

    private final CouponServiceImpl couponServiceImpl;

}
