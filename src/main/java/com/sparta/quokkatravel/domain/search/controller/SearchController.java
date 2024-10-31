package com.sparta.quokkatravel.domain.search.controller;

import com.sparta.quokkatravel.domain.accommodation.dto.GuestAccommodationResponseDto;
import com.sparta.quokkatravel.domain.admin.coupon.dto.AdminCouponResponseDto;
import com.sparta.quokkatravel.domain.common.advice.ApiResponse;
import com.sparta.quokkatravel.domain.common.dto.CustomUserDetails;
import com.sparta.quokkatravel.domain.search.dto.SearchAccommodationRes;
import com.sparta.quokkatravel.domain.search.service.SearchServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
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

@RestController
@RequestMapping("/api/v1/search")
public class SearchController {


    private final SearchServiceImpl searchServiceImpl;

    public SearchController(SearchServiceImpl searchServiceImpl) {
        this.searchServiceImpl = searchServiceImpl;
    }

    // 숙소 검색(이름)
    @GetMapping("/accommodations/name")
    @Operation(summary = "숙소 이름 검색", description = "숙소 이름으로 검색하는 API")
    public ResponseEntity<?> searchAccommodation(@RequestParam(name = "keyword", required = false) String keyword,
                                                 @RequestParam(required = false) Pageable pageable) {

        List<SearchAccommodationRes> accommodations = searchServiceImpl.searchAccommodatiosByName(keyword, pageable);
        return ResponseEntity.ok(ApiResponse.success("숙소 조회 성공", accommodations));
    }

    // 숙소 곰샥
    @GetMapping("/accommodations/address")
    @Operation(summary = "숙소 주소 검색", description = "숙소 주소로 검색하는 API")
    public ResponseEntity<?> searchCoupon(@RequestParam(name = "keyword", required = false) String keyword,
                                          @RequestParam(required = false) Pageable pageable) {
        List<SearchAccommodationRes> accommodations = searchServiceImpl.searchAccommodatiosByAddress(keyword, pageable);
        return ResponseEntity.ok(ApiResponse.success("숙소 조회 성공", accommodations));
    }

    // 쿠폰 검색 (관리자 전용)

    // 이벤트 검색


}
