package com.sparta.quokkatravel.domain.admin.revenue.revenuedetail.service;

import com.sparta.quokkatravel.domain.admin.revenue.revenuedetail.dto.RevenueDetailRequestDto;
import com.sparta.quokkatravel.domain.admin.revenue.revenuedetail.entity.RevenueDetail;
import com.sparta.quokkatravel.domain.admin.revenue.revenuedetail.repository.RevenueDetailRepository;
import com.sparta.quokkatravel.domain.admin.revenue.settlementinfo.entity.SettlementInfo;
import com.sparta.quokkatravel.domain.admin.revenue.settlementinfo.repository.SettlementRepository;
import com.sparta.quokkatravel.domain.coupon.entity.Coupon;
import com.sparta.quokkatravel.domain.coupon.repository.CouponRepository;
import com.sparta.quokkatravel.domain.reservation.entity.Reservation;
import com.sparta.quokkatravel.domain.reservation.repository.ReservationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RevenueDetailService {

    private final RevenueDetailRepository revenueDetailRepository;
    private final SettlementRepository settlementRepository;
    private final ReservationRepository reservationRepository;
    private final CouponRepository couponRepository;

    public RevenueDetailService(RevenueDetailRepository revenueDetailRepository, SettlementRepository settlementRepository,
                                ReservationRepository reservationRepository, CouponRepository couponRepository) {
        this.revenueDetailRepository = revenueDetailRepository;
        this.settlementRepository = settlementRepository;
        this.reservationRepository = reservationRepository;
        this.couponRepository = couponRepository;
    }

    // 새로운 수익 내역 생성
    @Transactional
    public RevenueDetail createRevenueDetail(RevenueDetailRequestDto requestDto) {
        SettlementInfo settlementInfo = settlementRepository.findById(requestDto.getSettlementId())
                .orElseThrow(() -> new RuntimeException("정산 정보를 찾을 수 없습니다."));

        Reservation reservation = reservationRepository.findById(requestDto.getReservationId())
                .orElseThrow(() -> new RuntimeException("예약 정보를 찾을 수 없습니다."));

        Coupon coupon = couponRepository.findById(requestDto.getCouponId()).orElse(null);

        RevenueDetail revenueDetail = new RevenueDetail(
                settlementInfo,
                reservation,
                requestDto.getProductName(),
                requestDto.getProductPrice(),
                requestDto.getPaymentMethod(),
                requestDto.getAdditionalIncome(),
                requestDto.getRefundAmount(),
                coupon
        );

        return revenueDetailRepository.save(revenueDetail);
    }

    // 수익 내역 수정
    @Transactional
    public void updateRevenueDetail(Long id, RevenueDetailRequestDto requestDto) {
        RevenueDetail revenueDetail = revenueDetailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("수익 내역을 찾을 수 없습니다."));

        Coupon coupon = couponRepository.findById(requestDto.getCouponId()).orElse(null);

        revenueDetail.update(
                requestDto.getProductName(),
                requestDto.getProductPrice(),
                requestDto.getPaymentMethod(),
                requestDto.getAdditionalIncome(),
                requestDto.getRefundAmount(),
                coupon
        );

        revenueDetailRepository.save(revenueDetail);
    }

    // 수익 내역 삭제
    @Transactional
    public void deleteRevenueDetail(Long id) {
        // 특정 수익 내역 ID로 데이터베이스에서 해당 수익 내역을 삭제
        revenueDetailRepository.deleteById(id);
    }
}