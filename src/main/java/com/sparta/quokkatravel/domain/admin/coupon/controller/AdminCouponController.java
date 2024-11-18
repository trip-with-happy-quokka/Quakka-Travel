package com.sparta.quokkatravel.domain.admin.coupon.controller;

import com.sparta.quokkatravel.domain.admin.coupon.dto.AdminCouponRequestDto;
import com.sparta.quokkatravel.domain.admin.coupon.dto.AdminCouponResponseDto;
import com.sparta.quokkatravel.domain.admin.coupon.dto.CouponToUserReq;
import com.sparta.quokkatravel.domain.admin.coupon.dto.CouponToUserRes;
import com.sparta.quokkatravel.domain.admin.coupon.service.AdminCouponService;
import com.sparta.quokkatravel.domain.admin.coupon.service.CouponProducerService;
import com.sparta.quokkatravel.domain.common.jwt.CustomUserDetails;
import com.sparta.quokkatravel.domain.common.shared.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/admin/coupons")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
@Tag(name = "AdminCoupon", description = "Admin 쿠폰 관련 컨트롤러")
public class AdminCouponController {

    private final AdminCouponService adminCouponService;
    private final CouponProducerService couponProducerService;

    // 모든 쿠폰 목록 조회 (관리자 전용)
    @GetMapping
    @Operation(summary = "쿠폰 목록 조회", description = "관리자가 모든 쿠폰 목록을 조회하는 API")
    public ResponseEntity<?> getAllCoupons() {
        List<AdminCouponResponseDto> coupons = adminCouponService.getAllCoupons();
        return ResponseEntity.ok(ApiResponse.success("쿠폰 목록 조회 성공", coupons));
    }

    // 단일 쿠폰 조회 (관리자 전용)
    @GetMapping("/{couponId}")
    @Operation(summary = "단일 쿠폰 조회", description = "관리자가 특정 쿠폰의 세부 정보를 조회하는 API")
    public ResponseEntity<?> getCoupon(@PathVariable Long couponId) {
        AdminCouponResponseDto coupon = adminCouponService.getCoupon(couponId);
        return ResponseEntity.ok(ApiResponse.success("쿠폰 조회 성공", coupon));
    }

    // 쿠폰 발급 (관리자 전용)
    // 같은 UUID 번호로 일정 수량 만큼 발급
    @PostMapping
    @Operation(summary = "쿠폰 발급", description = "관리자가 쿠폰을 발급하는 API")
    public ResponseEntity<?> createCoupon(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody AdminCouponRequestDto couponRequestDto) {
        AdminCouponResponseDto coupon = adminCouponService.createCoupon(customUserDetails.getEmail(), couponRequestDto);
        return ResponseEntity.ok(ApiResponse.created("쿠폰 발급 성공", coupon));
    }

    // 쿠폰 발급 2
    // 특정 유저에게 쿠폰 발급
    // RabbitMQ 사용
    @PostMapping("/send")
    @Operation(summary = "특정 유저에게 쿠폰 발급", description = "관리자가 특정 유저에게 쿠폰을 발급하는 API")
    public ResponseEntity<?> createCouponToUser(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody CouponToUserReq couponMessageReq) {
        CouponToUserRes couponMessageRes = couponProducerService.createCouponToUser(customUserDetails.getEmail(), couponMessageReq);
        return ResponseEntity.ok(ApiResponse.success("유저에게 쿠폰 발급 성공", couponMessageRes));
    }

    // 쿠폰 수정 (관리자 전용)
    @PutMapping("/{couponId}")
    @Operation(summary = "쿠폰 수정", description = "관리자가 쿠폰을 수정하는 API")
    public ResponseEntity<?> updateCoupon(@PathVariable Long couponId, @RequestBody AdminCouponRequestDto couponRequestDto) {
        AdminCouponResponseDto updatedCoupon = adminCouponService.updateCoupon(couponId, couponRequestDto);
        return ResponseEntity.ok(ApiResponse.success("쿠폰 수정 성공", updatedCoupon));
    }

    // 쿠폰 삭제 (관리자 전용)
    @DeleteMapping("/{couponId}")
    @Operation(summary = "쿠폰 삭제", description = "관리자가 쿠폰을 삭제하는 API")
    public ResponseEntity<?> deleteCoupon(@PathVariable Long couponId) {
        adminCouponService.deleteCoupon(couponId);
        return ResponseEntity.ok(ApiResponse.success("쿠폰 삭제 성공"));
    }
}
