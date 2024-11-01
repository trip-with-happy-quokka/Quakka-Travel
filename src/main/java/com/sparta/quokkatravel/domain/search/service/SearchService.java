package com.sparta.quokkatravel.domain.search.service;

import com.sparta.quokkatravel.domain.coupon.entity.CouponStatus;
import com.sparta.quokkatravel.domain.coupon.entity.CouponType;
import com.sparta.quokkatravel.domain.search.document.AccommodationDocument;
import com.sparta.quokkatravel.domain.search.dto.SearchAccommodationRes;
import com.sparta.quokkatravel.domain.search.dto.SearchCouponRes;

import java.io.IOException;
import java.util.List;

public interface SearchService {

    // 숙소
    List<AccommodationDocument> searchAccommodations(String name, String address, Long rating) throws IOException;

    // 쿠폰
    List<SearchCouponRes> searchCoupons(String name, CouponType couponType, CouponStatus couponStatus);

    void deleteDocuments();
}
