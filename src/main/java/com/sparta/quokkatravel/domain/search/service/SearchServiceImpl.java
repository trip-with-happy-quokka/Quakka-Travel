//package com.sparta.quokkatravel.domain.search.service;
//
//import com.sparta.quokkatravel.domain.coupon.entity.CouponStatus;
//import com.sparta.quokkatravel.domain.coupon.entity.CouponType;
//import com.sparta.quokkatravel.domain.search.dto.SearchAccommodationRes;
//import com.sparta.quokkatravel.domain.search.dto.SearchCouponRes;
//import com.sparta.quokkatravel.domain.search.repository.AccommodationSearchRepository;
//import com.sparta.quokkatravel.domain.search.repository.CouponSearchRepository;
//import org.springframework.boot.autoconfigure.web.ServerProperties;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class SearchServiceImpl implements SearchService {
//
//    private final ServerProperties serverProperties;
//    private final AccommodationSearchRepository accommodationSearchRepository;
//    private final CouponSearchRepository couponSearchRepository;
//
//    public SearchServiceImpl(ServerProperties serverProperties, AccommodationSearchRepository accommodationSearchRepository, CouponSearchRepository couponSearchRepository) {
//        this.serverProperties = serverProperties;
//        this.accommodationSearchRepository = accommodationSearchRepository;
//        this.couponSearchRepository = couponSearchRepository;
//    }
//
//    @Override
//    public List<SearchAccommodationRes> searchAccommodations(String name, String address, Long rating) {
//        return accommodationSearchRepository.findByNameContainingAndAddressAndRating(name, address, rating).stream().map(SearchAccommodationRes::new).toList();
//    }
//
//    @Override
//    public List<SearchCouponRes> searchCoupons(String name, CouponType couponType, CouponStatus couponStatus) {
//        return couponSearchRepository.findByNameContainingAndCouponTypeAndCouponStatus(name, couponType, couponStatus).stream().map(SearchCouponRes::new).toList();
//    }
//}
