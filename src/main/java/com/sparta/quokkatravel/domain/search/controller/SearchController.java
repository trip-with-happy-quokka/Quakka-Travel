package com.sparta.quokkatravel.domain.search.controller;

import com.sparta.quokkatravel.domain.accommodation.dto.GuestAccommodationResponseDto;
import com.sparta.quokkatravel.domain.admin.coupon.dto.AdminCouponResponseDto;
import com.sparta.quokkatravel.domain.common.advice.ApiResponse;
import com.sparta.quokkatravel.domain.common.dto.CustomUserDetails;
import com.sparta.quokkatravel.domain.coupon.entity.CouponStatus;
import com.sparta.quokkatravel.domain.coupon.entity.CouponType;
import com.sparta.quokkatravel.domain.search.dto.SearchAccommodationRes;
import com.sparta.quokkatravel.domain.search.dto.SearchCouponRes;
import com.sparta.quokkatravel.domain.search.service.SearchService;
import com.sparta.quokkatravel.domain.search.service.SearchServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/search")
public class SearchController {


    private final SearchService searchService;


    // 숙소 검색(이름)
    @GetMapping("/accommodations/name")
    @Operation(summary = "숙소 검색", description = "숙소 검색 API, 근데 주소와 별점를 곁들인")
    public ResponseEntity<?> searchAccommodation(@RequestParam(required = false) String name,
                                                 @RequestParam(required = false) String address,
                                                 @RequestParam(required = false) Long rating) {

        List<SearchAccommodationRes> accommodations = searchService.searchAccommodations(name, address, rating);
        return ResponseEntity.ok(ApiResponse.success("숙소 조회 성공", accommodations));
    }


    // 쿠폰 검색 (관리자 전용)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/coupon")
    @Operation(summary = "쿠폰 검색", description = "쿠폰 검색 API, 근데 타입과 상태를 곁들인")
    public ResponseEntity<?> searchCoupon(@RequestParam Optional<String> name,
                                          @RequestParam Optional<CouponType> couponType,
                                          @RequestParam Optional<CouponStatus> couponStatus) {
        List<SearchCouponRes> coupons = searchService.searchCoupons(name.orElse(""), couponType.orElse(null), couponStatus.orElse(null));
        return ResponseEntity.ok(ApiResponse.success("", coupons));
    }

    // 이벤트 검색


}
