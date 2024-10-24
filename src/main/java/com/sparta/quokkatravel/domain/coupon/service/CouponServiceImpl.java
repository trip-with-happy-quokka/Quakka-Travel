package com.sparta.quokkatravel.domain.coupon.service;

import com.sparta.quokkatravel.domain.accommodation.entity.Accommodation;
import com.sparta.quokkatravel.domain.accommodation.repository.AccommodationRepository;
import com.sparta.quokkatravel.domain.common.dto.CustomUserDetails;
import com.sparta.quokkatravel.domain.common.exception.NotFoundException;
import com.sparta.quokkatravel.domain.coupon.dto.request.CouponRequestDto;
import com.sparta.quokkatravel.domain.coupon.dto.response.CouponDeleteResponseDto;
import com.sparta.quokkatravel.domain.coupon.dto.response.CouponResponseDto;
import com.sparta.quokkatravel.domain.coupon.entity.Coupon;
import com.sparta.quokkatravel.domain.coupon.repository.CouponRepository;
import com.sparta.quokkatravel.domain.event.entity.Event;
import com.sparta.quokkatravel.domain.event.repository.EventRepository;
import com.sparta.quokkatravel.domain.user.entity.User;
import com.sparta.quokkatravel.domain.user.repository.UserRepository;
import com.sun.jdi.request.InvalidRequestStateException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;
    private final EventRepository eventRepository;
    private final AccommodationRepository accommodationRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public CouponResponseDto createEventCoupon(CustomUserDetails customUserDetails, Long eventId, CouponRequestDto couponRequestDto) {

        // customUserDetails 에서 생성자 정보 불러오기
//        User user = userRepository.findByEmail(customUserDetails.getEmail()).orElseThrow(
//                ()-> new InvalidRequestStateException("가입되지 않은 이메일 유저입니다.")
//        );

        // eventId 로 event 조회
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException("해당 행사 조회 불가"));

        // UUID 로 쿠폰코드 발급
        String newCouponCode = new Coupon().createCouponCode();

        // RequestDto 데이터를 Coupon 에 전달
        Coupon newCoupon = new Coupon(
                couponRequestDto.getCouponName(),
                couponRequestDto.getCouponContent(),
                couponRequestDto.getCouponType(),
                newCouponCode,
                couponRequestDto.getDiscountRate(),
                couponRequestDto.getDiscountAmount(),
                couponRequestDto.getValidFrom(),
                couponRequestDto.getValidUntil(),
                event
        );

        // 쿠폰 레퍼지토리에 쿠폰 데이터를 저장 (save)
        Coupon savedCoupon = couponRepository.save(newCoupon);

        // 쿠폰을 CouponResponseDto 로 반환
        return new CouponResponseDto(
                savedCoupon.getId(),
                savedCoupon.getName(),
                savedCoupon.getCouponType(),
                newCouponCode,
                savedCoupon.getDiscountRate(),
                savedCoupon.getDiscountAmount(),
                savedCoupon.getValidFrom(),
                savedCoupon.getValidFrom(),
                savedCoupon.getIsAvailable(),
                savedCoupon.getIsDeleted(),
                savedCoupon.getCreatedAt(),
                savedCoupon.getUpdatedAt()
        );
    }

    @Override
    @Transactional
    public CouponResponseDto createAccommodationCoupon(CustomUserDetails customUserDetails, Long accommodationId, CouponRequestDto couponRequestDto) {

        // customUserDetails 에서 생성자 정보 불러오기

        // accommodationId 로 accommodation 조회
        Accommodation accommodation = accommodationRepository.findById(accommodationId).orElseThrow(() -> new NotFoundException("해당 숙소 조회 불가"));

        // UUID 로 쿠폰코드 발급
        String newCouponCode = new Coupon().createCouponCode();

        // RequestDto 데이터를 Coupon 에 전달
        Coupon newCoupon = new Coupon(
                couponRequestDto.getCouponName(),
                couponRequestDto.getCouponContent(),
                couponRequestDto.getCouponType(),
                newCouponCode,
                couponRequestDto.getDiscountRate(),
                couponRequestDto.getDiscountAmount(),
                couponRequestDto.getValidFrom(),
                couponRequestDto.getValidUntil(),
                accommodation
        );

        // 쿠폰 레퍼지토리에 쿠폰 데이터를 저장 (save)
        Coupon savedCoupon = couponRepository.save(newCoupon);

        // 쿠폰을 CouponResponseDto 로 반환
        return new CouponResponseDto(
                savedCoupon.getId(),
                savedCoupon.getName(),
                savedCoupon.getCouponType(),
                newCouponCode,
                savedCoupon.getDiscountRate(),
                savedCoupon.getDiscountAmount(),
                savedCoupon.getValidFrom(),
                savedCoupon.getValidUntil(),
                savedCoupon.getIsAvailable(),
                savedCoupon.getIsDeleted(),
                savedCoupon.getCreatedAt(),
                savedCoupon.getUpdatedAt()
        );
    }

    @Override
    public List<CouponResponseDto> getAllCoupons(CustomUserDetails customUserDetails) {

        List<Coupon> coupons = couponRepository.findAll();

        return coupons.stream().map(coupon -> new CouponResponseDto(
                coupon.getId(),
                coupon.getName(),
                coupon.getCouponType(),
                coupon.getCode(),
                coupon.getDiscountRate(),
                coupon.getDiscountAmount(),
                coupon.getValidFrom(),
                coupon.getValidUntil(),
                coupon.getIsAvailable(),
                coupon.getIsDeleted(),
                coupon.getCreatedAt(),
                coupon.getUpdatedAt()
        )).toList();
    }

    @Override
    @Transactional
    public CouponDeleteResponseDto deleteCoupon(CustomUserDetails customUserDetails, Long couponId) {

        // couponId 로 해당 쿠폰 조회
        Coupon coupon = couponRepository.findById(couponId).orElseThrow(() -> new NotFoundException("해당 쿠폰 조회 불가"));

        // 쿠폰 삭제여부 변경
        coupon.deleteCoupon();
        couponRepository.save(coupon);

        return new CouponDeleteResponseDto(
                coupon.getId(),
                coupon.getName(),
                coupon.getCode(),
                coupon.getIsDeleted(),
                coupon.getDeletedAt()
        );
    }

}
