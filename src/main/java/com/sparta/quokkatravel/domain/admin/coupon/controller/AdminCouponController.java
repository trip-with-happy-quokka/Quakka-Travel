package com.sparta.quokkatravel.domain.admin.coupon.controller;

import com.sparta.quokkatravel.domain.admin.coupon.dto.AdminCouponCreateRequestDto;
import com.sparta.quokkatravel.domain.admin.coupon.dto.AdminCouponResponseDto;
import com.sparta.quokkatravel.domain.admin.coupon.service.AdminCouponService;
import com.sparta.quokkatravel.domain.common.advice.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/coupons")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
@Tag(name = "AdminCoupon", description = "Admin 쿠폰 관련 컨트롤러")
public class AdminCouponController {

    private final AdminCouponService adminCouponService;

    // 모든 쿠폰 목록 조회 (관리자 전용)
    @GetMapping
    @Operation(summary = "쿠폰 목록 조회", description = "관리자가 모든 쿠폰 목록을 조회하는 API")
    public ResponseEntity<?> getAllCoupons() {
        List<AdminCouponResponseDto> coupons = adminCouponService.getAllCoupons();
        return ResponseEntity.ok(ApiResponse.success("쿠폰 목록 조회 성공", coupons));
    }

    // 쿠폰 발급 (관리자 전용)
    @PostMapping
    @Operation(summary = "쿠폰 발급", description = "관리자가 쿠폰을 발급하는 API")
    public ResponseEntity<?> createCoupon(@RequestBody AdminCouponCreateRequestDto couponRequestDto) {
        AdminCouponResponseDto coupon = adminCouponService.createCoupon(couponRequestDto);
        return ResponseEntity.ok(ApiResponse.created("쿠폰 발급 성공", coupon));
    }

    // 쿠폰 삭제 (관리자 전용)
    @DeleteMapping("/{couponId}")
    @Operation(summary = "쿠폰 삭제", description = "관리자가 쿠폰을 삭제하는 API")
    public ResponseEntity<?> deleteCoupon(@PathVariable Long couponId) {
        adminCouponService.deleteCoupon(couponId);
        return ResponseEntity.ok(ApiResponse.success("쿠폰 삭제 성공"));
    }
}
