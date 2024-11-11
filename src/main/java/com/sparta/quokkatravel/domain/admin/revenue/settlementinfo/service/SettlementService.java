package com.sparta.quokkatravel.domain.admin.revenue.settlementinfo.service;

import com.sparta.quokkatravel.domain.accommodation.entity.Accommodation;
import com.sparta.quokkatravel.domain.accommodation.repository.AccommodationRepository;
import com.sparta.quokkatravel.domain.admin.revenue.revenuedetail.dto.RevenueDetailResponseDto;
import com.sparta.quokkatravel.domain.admin.revenue.settlementinfo.dto.SettlementRequestDto;
import com.sparta.quokkatravel.domain.admin.revenue.settlementinfo.dto.SettlementResponseDto;
import com.sparta.quokkatravel.domain.admin.revenue.settlementinfo.entity.SettlementInfo;
import com.sparta.quokkatravel.domain.admin.revenue.settlementinfo.entity.SettlementStatus;
import com.sparta.quokkatravel.domain.admin.revenue.settlementinfo.repository.SettlementRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SettlementService {

    private final SettlementRepository settlementRepository;
    private final AccommodationRepository accommodationRepository;

    public SettlementService(SettlementRepository settlementRepository, AccommodationRepository accommodationRepository) {
       this.settlementRepository = settlementRepository;
       this.accommodationRepository = accommodationRepository;
    }

    // 특정 정산 ID로 정산 정보 조회
    @Transactional(readOnly = true)
    public SettlementResponseDto getSettlementById(Long id) {
        SettlementInfo settlementInfo = settlementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("정산 정보를 찾을 수 없습니다."));
        return convertToSettlementResponseDto(settlementInfo);
    }

    // 특정 정산 정보 삭제
    @Transactional
    public void deleteSettlement(Long id) {
        if (!settlementRepository.existsById(id)) {
            throw new RuntimeException("삭제할 정산 정보를 찾을 수 없습니다.");
        }
        settlementRepository.deleteById(id);
    }

    // 새로운 정산 생성 및 저장
    @Transactional
    public SettlementInfo createSettlement(SettlementRequestDto requestDto) {
        Accommodation accommodation = accommodationRepository.findById(requestDto.getAccommodationId())
                .orElseThrow(() -> new RuntimeException("숙소 정보를 찾을 수 없습니다."));

        SettlementInfo settlementInfo = new SettlementInfo(
                accommodation,
                requestDto.getTotalIncome(),
                requestDto.getTotalReservationCount(),
                requestDto.getCanceledReservationCount(),
                requestDto.getRefundAmount(),
                requestDto.getPlatformCommission(),
                requestDto.getPartnerCommission(),
                requestDto.getTaxAmount(),
                SettlementStatus.IN_PROGRESS,
                requestDto.getSettlementPeriodStart(),
                requestDto.getSettlementPeriodEnd()
        );

        return settlementRepository.save(settlementInfo);
    }

    // 정산 정보 업데이트 (정산 ID 기준)
    @Transactional
    public void updateSettlement(Long id, SettlementRequestDto requestDto) {
        SettlementInfo settlementInfo = settlementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("정산 정보를 찾을 수 없습니다."));

        settlementInfo.update(
                requestDto.getTotalIncome(),
                requestDto.getTotalReservationCount(),
                requestDto.getCanceledReservationCount(),
                requestDto.getRefundAmount(),
                requestDto.getPlatformCommission(),
                requestDto.getPartnerCommission(),
                requestDto.getTaxAmount(),
                requestDto.getSettlementPeriodStart(),
                requestDto.getSettlementPeriodEnd()
        );

        settlementRepository.save(settlementInfo);
    }

    // 특정 기간 동안의 정산 조회
    public List<SettlementResponseDto> getSettlementsByDateRange(LocalDate startDate, LocalDate endDate) {
        return settlementRepository.findBySettlementPeriodStartBetween(startDate, endDate).stream()
                .map(this::convertToSettlementResponseDto)
                .collect(Collectors.toList());
    }

    // 월별 통계 조회 (연도-월 형식으로 입력)
    public List<SettlementResponseDto> getMonthlyStatistics(YearMonth yearMonth) {
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();
        return getSettlementsByDateRange(startDate, endDate);
    }

    // 정산 정보를 SettlementResponseDto로 변환
    private SettlementResponseDto convertToSettlementResponseDto(SettlementInfo settlementInfo) {
        List<RevenueDetailResponseDto> revenueDetails = Optional.ofNullable(settlementInfo.getRevenueDetails())
                .orElse(Collections.emptyList()) // null 체크 및 빈 리스트 반환
                .stream()
                .map(revenue -> new RevenueDetailResponseDto(
                        revenue.getProductName(),
                        revenue.getProductPrice(),
                        revenue.getReservation().getStartDate(),
                        revenue.getCoupon() != null ? revenue.getCoupon().getName() : "No Coupon",
                        revenue.getPaymentMethod()))
                .collect(Collectors.toList());

        return new SettlementResponseDto(
                settlementInfo.getSettlementId(),
                settlementInfo.getAccommodation().getId(),
                settlementInfo.getTotalIncome(),
                settlementInfo.getTotalReservationCount(),
                settlementInfo.getCanceledReservationCount(),
                settlementInfo.getRefundAmount(),
                settlementInfo.getPlatformCommission(),
                settlementInfo.getPartnerCommission(),
                settlementInfo.getTaxAmount(),
                settlementInfo.getSettlementPeriodStart(),
                settlementInfo.getSettlementPeriodEnd(),
                revenueDetails
        );
    }
}