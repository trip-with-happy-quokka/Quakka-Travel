//package com.sparta.quokkatravel.domain.search.service;
//
//import com.sparta.quokkatravel.domain.coupon.entity.CouponStatus;
//import com.sparta.quokkatravel.domain.coupon.entity.CouponType;
//import com.sparta.quokkatravel.domain.search.document.AccommodationDocument;
//import com.sparta.quokkatravel.domain.search.dto.SearchAccommodationRes;
//import com.sparta.quokkatravel.domain.search.dto.SearchCouponRes;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//
//import java.util.List;
//
//public interface SearchService {
//
//    // 숙소
//    List<SearchAccommodationRes> searchAccommodations(String name, String address, Long rating);
//
//    // 쿠폰
//    List<SearchCouponRes> searchCoupons(String name, CouponType couponType, CouponStatus couponStatus);
//}
