package com.sparta.quokkatravel.domain.coupon.service;

import com.sparta.quokkatravel.domain.accommodation.entity.Accommodation;
import com.sparta.quokkatravel.domain.accommodation.repository.AccommodationRepository;
import com.sparta.quokkatravel.domain.common.dto.CustomUserDetails;
import com.sparta.quokkatravel.domain.common.exception.BadRequestException;
import com.sparta.quokkatravel.domain.common.exception.NotFoundException;
import com.sparta.quokkatravel.domain.coupon.dto.request.CouponCodeRequestDto;
import com.sparta.quokkatravel.domain.coupon.dto.request.CouponRequestDto;
import com.sparta.quokkatravel.domain.coupon.dto.response.CouponCodeResponseDto;
import com.sparta.quokkatravel.domain.coupon.dto.response.CouponDeleteResponseDto;
import com.sparta.quokkatravel.domain.coupon.dto.response.CouponRedeemResponseDto;
import com.sparta.quokkatravel.domain.coupon.dto.response.CouponResponseDto;
import com.sparta.quokkatravel.domain.coupon.entity.Coupon;
import com.sparta.quokkatravel.domain.coupon.entity.CouponStatus;
import com.sparta.quokkatravel.domain.coupon.repository.CouponRepository;
import com.sparta.quokkatravel.domain.email.service.CouponEmailService;
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
    private final CouponEmailService couponEmailService;

    // 행사 쿠폰 발급 메서드
    @Override
    @Transactional
    public CouponResponseDto createEventCoupon(String email, Long eventId, CouponRequestDto couponRequestDto) {

        // customUserDetails 에서 생성자 정보 불러오기
        User user = userRepository.findByEmailOrElseThrow(email);

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
                CouponStatus.ISSUED,
                couponRequestDto.getDiscountRate(),
                couponRequestDto.getDiscountAmount(),
                couponRequestDto.getValidFrom(),
                couponRequestDto.getValidUntil(),
                event,
                user
        );

        // 쿠폰 레퍼지토리에 쿠폰 데이터를 저장 (save)
        Coupon savedCoupon = couponRepository.save(newCoupon);

        // 쿠폰을 CouponResponseDto 로 반환
        return new CouponResponseDto(
                savedCoupon.getId(),
                savedCoupon.getName(),
                savedCoupon.getCouponType(),
                newCouponCode,
                savedCoupon.getCouponStatus(),
                savedCoupon.getDiscountRate(),
                savedCoupon.getDiscountAmount(),
                savedCoupon.getValidFrom(),
                savedCoupon.getValidFrom(),
                savedCoupon.getCreatedAt(),
                savedCoupon.getUpdatedAt()
        );
    }

    // 숙소 쿠폰 발급 메서드
    @Override
    @Transactional
    public CouponResponseDto createAccommodationCoupon(String email, Long accommodationId, CouponRequestDto couponRequestDto) {

        // customUserDetails 에서 생성자 정보 불러오기
        User user = userRepository.findByEmailOrElseThrow(email);

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
                CouponStatus.ISSUED,
                couponRequestDto.getDiscountRate(),
                couponRequestDto.getDiscountAmount(),
                couponRequestDto.getValidFrom(),
                couponRequestDto.getValidUntil(),
                accommodation,
                user
        );

        // 쿠폰 레퍼지토리에 쿠폰 데이터를 저장 (save)
        Coupon savedCoupon = couponRepository.save(newCoupon);

        // 쿠폰을 CouponResponseDto 로 반환
        return new CouponResponseDto(
                savedCoupon.getId(),
                savedCoupon.getName(),
                savedCoupon.getCouponType(),
                newCouponCode,
                savedCoupon.getCouponStatus(),
                savedCoupon.getDiscountRate(),
                savedCoupon.getDiscountAmount(),
                savedCoupon.getValidFrom(),
                savedCoupon.getValidUntil(),
                savedCoupon.getCreatedAt(),
                savedCoupon.getUpdatedAt()
        );
    }

    // 쿠폰 등록 메서드
    @Override
    @Transactional
    public CouponCodeResponseDto registerCoupon(String email, Long userId, CouponCodeRequestDto couponCodeRequestDto){

        // userId 로 User 조회
        User user = userRepository.findByEmailOrElseThrow(email);

        // 쿠폰 코드로 쿠폰 찾기
        Coupon coupon = couponRepository.findByCode(couponCodeRequestDto.getCouponCode())
                .orElseThrow(() -> new NotFoundException("coupon is not found"));

        // 쿠폰 소유자 및 등록일자 등록
        // 쿠폰 사용 가능 상태로 변경
        coupon.registerCoupon(user);

        // 쿠폰 등록 후 유저에게 이메일 전송
        couponEmailService.sendCouponRegistrationEmail(user, coupon);

        return new CouponCodeResponseDto(
                coupon.getId(),
                coupon.getName(),
                coupon.getCode(),
                coupon.getCouponStatus(),
                coupon.getValidFrom(),
                coupon.getValidUntil()
        );
    }

    @Override
    public CouponRedeemResponseDto redeemCoupon(String email, Long userId, Long couponId){

        // userId 로 User 조회
        User user = userRepository.findByEmailOrElseThrow(email);

        // couponId 로 Coupon 조회
        Coupon coupon = couponRepository.findById(couponId).orElseThrow(()-> new NotFoundException("쿠폰 조회 불가"));

        // coupon 상태 => 사용됨으로 변경
        coupon.redeemCoupon();

        // 쿠폰 사용 후 유저에게 이메일 전송
        couponEmailService.sendCouponRedeemEmail(user, coupon);

        return new CouponRedeemResponseDto(
                coupon.getId(),
                coupon.getName(),
                coupon.getCode(),
                coupon.getCouponStatus(),
                coupon.getDiscountRate(),
                coupon.getDiscountAmount(),
                coupon.getValidFrom(),
                coupon.getValidUntil()
        );
    }

    // 내 쿠폰 전체 조회
    @Override
    public List<CouponResponseDto> getAllMyCoupons(String email, Long userId) {

        // User 정보 조회
        User user = userRepository.findByEmailOrElseThrow(email);

        // 유저 정보 일치하는지 확인
        if(!email.equals(user.getEmail())){
            throw new BadRequestException("본인 쿠폰만 조회 가능합니다.");
        }

        List<Coupon> coupons = couponRepository.findAllByOwner(user);

        return coupons.stream().map(coupon -> new CouponResponseDto(
                coupon.getId(),
                coupon.getName(),
                coupon.getCouponType(),
                coupon.getCode(),
                coupon.getCouponStatus(),
                coupon.getDiscountRate(),
                coupon.getDiscountAmount(),
                coupon.getValidFrom(),
                coupon.getValidUntil(),
                coupon.getCreatedAt(),
                coupon.getUpdatedAt()
        )).toList();
    }

    // 쿠폰 삭제
    @Override
    @Transactional
    public CouponDeleteResponseDto deleteCoupon(String email, Long couponId) {

        // couponId 로 해당 쿠폰 조회
        Coupon coupon = couponRepository.findById(couponId).orElseThrow(() -> new NotFoundException("해당 쿠폰 조회 불가"));

        // 쿠폰 삭제여부 변경
        coupon.deleteCoupon();
        couponRepository.save(coupon);

        // 쿠폰 삭제 후 유저에게 이메일 전송
        couponEmailService.sendCouponDeletionEmail(coupon.getOwner(), coupon);

        return new CouponDeleteResponseDto(
                coupon.getId(),
                coupon.getName(),
                coupon.getCode(),
                coupon.getIsDeleted(),
                coupon.getDeletedAt()
        );
    }

}
