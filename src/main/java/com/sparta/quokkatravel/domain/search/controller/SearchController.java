package com.sparta.quokkatravel.domain.search.controller;

import com.sparta.quokkatravel.domain.common.shared.ApiResponse;
import com.sparta.quokkatravel.domain.search.document.AccommodationDocument;
import com.sparta.quokkatravel.domain.search.document.CouponDocument;
import com.sparta.quokkatravel.domain.search.dto.SearchAccommodationRequestDto;
import com.sparta.quokkatravel.domain.search.dto.SearchCouponReq;
import com.sparta.quokkatravel.domain.search.service.SearchService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/search")
public class SearchController {


    private final SearchService searchService;


    // 숙소 검색(이름)
    @GetMapping("/accommodations")
    @Operation(summary = "숙소 검색", description = "숙소 검색 API, 근데 주소와 별점를 곁들인")
    public ResponseEntity<?> searchAccommodation(@RequestBody SearchAccommodationRequestDto searchAccommodationReq) throws IOException {

        List<AccommodationDocument> accommodations = searchService.searchAccommodations(searchAccommodationReq.getName(), searchAccommodationReq.getAddress(), searchAccommodationReq.getRating());
        return ResponseEntity.ok(ApiResponse.success("숙소 조회 성공", accommodations));
    }


    // 쿠폰 검색 (관리자 전용)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/coupons")
    @Operation(summary = "쿠폰 검색", description = "쿠폰 검색 API, 근데 타입과 상태를 곁들인")
    public ResponseEntity<?> searchCoupon(@RequestBody SearchCouponReq searchCouponReq) throws IOException {
        List<CouponDocument> coupons = searchService.searchCoupons(searchCouponReq.getName(), searchCouponReq.getCouponType(), searchCouponReq.getCouponStatus());
        return ResponseEntity.ok(ApiResponse.success("", coupons));
    }

    // 이벤트 검색

}
