package com.sparta.quokkatravel.domain.admin.settlement.service;

import com.sparta.quokkatravel.domain.accommodation.entity.Accommodation;
import com.sparta.quokkatravel.domain.accommodation.repository.AccommodationRepository;
import com.sparta.quokkatravel.domain.admin.revenue.settlementinfo.dto.SettlementRequestDto;
import com.sparta.quokkatravel.domain.admin.revenue.settlementinfo.dto.SettlementResponseDto;
import com.sparta.quokkatravel.domain.admin.revenue.settlementinfo.entity.SettlementInfo;
import com.sparta.quokkatravel.domain.admin.revenue.settlementinfo.repository.SettlementRepository;
import com.sparta.quokkatravel.domain.admin.revenue.settlementinfo.service.SettlementService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SettlementServiceTest {

    @Mock
    private SettlementRepository settlementRepository;

    @Mock
    private AccommodationRepository accommodationRepository;

    @InjectMocks
    private SettlementService settlementService;

    @Test
    void createSettlement_정상적으로_정산_생성() {
        // given
        Long accommodationId = 1L;
        Accommodation accommodation = new Accommodation();
        ReflectionTestUtils.setField(accommodation, "id", accommodationId);

        // ReflectionTestUtils를 사용하여 private 필드에 값 설정
        SettlementRequestDto requestDto = new SettlementRequestDto();
        ReflectionTestUtils.setField(requestDto, "accommodationId", accommodationId);
        ReflectionTestUtils.setField(requestDto, "totalIncome", new BigDecimal("1000.00"));
        ReflectionTestUtils.setField(requestDto, "totalReservationCount", 10);
        ReflectionTestUtils.setField(requestDto, "canceledReservationCount", 2);
        ReflectionTestUtils.setField(requestDto, "refundAmount", new BigDecimal("200.00"));
        ReflectionTestUtils.setField(requestDto, "platformCommission", new BigDecimal("50.00"));
        ReflectionTestUtils.setField(requestDto, "partnerCommission", new BigDecimal("100.00"));
        ReflectionTestUtils.setField(requestDto, "taxAmount", new BigDecimal("20.00"));
        ReflectionTestUtils.setField(requestDto, "settlementPeriodStart", LocalDate.now().minusDays(30));
        ReflectionTestUtils.setField(requestDto, "settlementPeriodEnd", LocalDate.now());

        // accommodationRepository가 accommodationId로 accommodation을 반환하도록 설정
        given(accommodationRepository.findById(accommodationId)).willReturn(Optional.of(accommodation));

        // when
        settlementService.createSettlement(requestDto);

        // then
        ArgumentCaptor<SettlementInfo> captor = ArgumentCaptor.forClass(SettlementInfo.class);
        verify(settlementRepository, times(1)).save(captor.capture()); // 객체 캡처

        SettlementInfo capturedSettlement = captor.getValue(); // 캡처된 객체 가져오기
        assertEquals(accommodation, capturedSettlement.getAccommodation()); // 필드 값 검증
        assertEquals(new BigDecimal("1000.00"), capturedSettlement.getTotalIncome()); // 필드 값 검증
    }

    @Test
    void createSettlement_숙소정보가_없으면_예외발생() {
        // given
        Long accommodationId = 1L;
        SettlementRequestDto requestDto = new SettlementRequestDto();
        ReflectionTestUtils.setField(requestDto, "accommodationId", accommodationId);

        // accommodationRepository가 빈 값을 반환하도록 설정
        given(accommodationRepository.findById(accommodationId)).willReturn(Optional.empty());

        // when & then
        assertThrows(RuntimeException.class, () -> settlementService.createSettlement(requestDto)); // 예외 발생 여부 검증
        verify(accommodationRepository, times(1)).findById(accommodationId); // accommodationRepository 호출 검증
    }

    @Test
    void getSettlementById_정상적으로_조회() {
        // given
        Long settlementId = 1L;
        SettlementInfo settlementInfo = new SettlementInfo();
        Accommodation accommodation = new Accommodation();

        ReflectionTestUtils.setField(accommodation, "id", 1L);
        ReflectionTestUtils.setField(settlementInfo, "settlementId", settlementId);
        ReflectionTestUtils.setField(settlementInfo, "accommodation", accommodation);
        ReflectionTestUtils.setField(settlementInfo, "revenueDetails", new ArrayList<>()); // 빈 리스트 설정

        given(settlementRepository.findById(settlementId)).willReturn(Optional.of(settlementInfo));

        // when
        SettlementResponseDto responseDto = settlementService.getSettlementById(settlementId);

        // then
        assertEquals(settlementId, responseDto.getSettlementId()); // 정산 ID 검증
        verify(settlementRepository, times(1)).findById(settlementId); // 호출 검증
    }

    @Test
    void getSettlementById_존재하지_않는_정산_정보_조회시_예외발생() {
        // given
        Long settlementId = 1L;
        given(settlementRepository.findById(settlementId)).willReturn(Optional.empty());

        // when & then
        assertThrows(RuntimeException.class, () -> settlementService.getSettlementById(settlementId)); // 예외 발생 여부 검증
        verify(settlementRepository, times(1)).findById(settlementId); // 호출 검증
    }

    @Test
    void updateSettlement_정상적으로_업데이트() {
        // given
        Long settlementId = 1L;
        SettlementRequestDto requestDto = new SettlementRequestDto();
        ReflectionTestUtils.setField(requestDto, "totalIncome", new BigDecimal("1500.00"));
        ReflectionTestUtils.setField(requestDto, "totalReservationCount", 15);
        ReflectionTestUtils.setField(requestDto, "canceledReservationCount", 3);
        ReflectionTestUtils.setField(requestDto, "refundAmount", new BigDecimal("300.00"));
        ReflectionTestUtils.setField(requestDto, "platformCommission", new BigDecimal("75.00"));
        ReflectionTestUtils.setField(requestDto, "partnerCommission", new BigDecimal("150.00"));
        ReflectionTestUtils.setField(requestDto, "taxAmount", new BigDecimal("30.00"));
        ReflectionTestUtils.setField(requestDto, "settlementPeriodStart", LocalDate.now().minusDays(60));
        ReflectionTestUtils.setField(requestDto, "settlementPeriodEnd", LocalDate.now().minusDays(30));

        SettlementInfo existingSettlement = new SettlementInfo();
        ReflectionTestUtils.setField(existingSettlement, "settlementId", settlementId);
        Accommodation accommodation = new Accommodation();
        ReflectionTestUtils.setField(accommodation, "id", 1L);
        ReflectionTestUtils.setField(existingSettlement, "accommodation", accommodation);

        given(settlementRepository.findById(settlementId)).willReturn(Optional.of(existingSettlement));

        // when
        settlementService.updateSettlement(settlementId, requestDto);

        // then
        ArgumentCaptor<SettlementInfo> captor = ArgumentCaptor.forClass(SettlementInfo.class);
        verify(settlementRepository, times(1)).save(captor.capture());

        SettlementInfo updatedSettlement = captor.getValue();
        assertEquals(new BigDecimal("1500.00"), updatedSettlement.getTotalIncome()); // 업데이트된 필드 검증
        assertEquals(15, updatedSettlement.getTotalReservationCount()); // 업데이트된 필드 검증
        assertEquals(3, updatedSettlement.getCanceledReservationCount()); // 업데이트된 필드 검증
    }

    @Test
    void deleteSettlement_정상적으로_삭제() {
        // given
        Long settlementId = 1L;
        given(settlementRepository.existsById(settlementId)).willReturn(true);

        // when
        settlementService.deleteSettlement(settlementId);

        // then
        verify(settlementRepository, times(1)).deleteById(settlementId); // 삭제 호출 검증
    }

    @Test
    void deleteSettlement_존재하지_않는_정산_정보_삭제시_예외발생() {
        // given
        Long settlementId = 1L;
        given(settlementRepository.existsById(settlementId)).willReturn(false);

        // when & then
        assertThrows(RuntimeException.class, () -> settlementService.deleteSettlement(settlementId)); // 예외 발생 여부 검증
        verify(settlementRepository, times(1)).existsById(settlementId); // 존재 여부 확인 호출 검증
    }

    @Test
    void getSettlementsByDateRange_정상적으로_조회() {
        // given
        LocalDate startDate = LocalDate.now().minusDays(30);
        LocalDate endDate = LocalDate.now();
        SettlementInfo settlementInfo = new SettlementInfo();
        Accommodation accommodation = new Accommodation();

        ReflectionTestUtils.setField(accommodation, "id", 1L);
        ReflectionTestUtils.setField(settlementInfo, "accommodation", accommodation);
        ReflectionTestUtils.setField(settlementInfo, "revenueDetails", new ArrayList<>()); // 빈 리스트 설정

        given(settlementRepository.findBySettlementPeriodStartBetween(startDate, endDate))
                .willReturn(List.of(settlementInfo));

        // when
        List<SettlementResponseDto> responseList = settlementService.getSettlementsByDateRange(startDate, endDate);

        // then
        assertEquals(1, responseList.size()); // 조회된 리스트의 크기 검증
        verify(settlementRepository, times(1)).findBySettlementPeriodStartBetween(startDate, endDate); // 호출 검증
    }

    @Test
    void getMonthlyStatistics_정상적으로_조회() {
        // given
        Long userid = 1L;

        YearMonth yearMonth = YearMonth.now();
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();

        SettlementInfo settlementInfo = new SettlementInfo();
        Accommodation accommodation = new Accommodation();

        ReflectionTestUtils.setField(accommodation, "id", 1L); // accommodation의 id 설정
        ReflectionTestUtils.setField(settlementInfo, "accommodation", accommodation);
        ReflectionTestUtils.setField(settlementInfo, "revenueDetails", new ArrayList<>()); // 빈 리스트 설정

        given(settlementRepository.findBySettlementPeriodStartBetween(startDate, endDate))
                .willReturn(List.of(settlementInfo));

        // when
        List<SettlementResponseDto> responseList = settlementService.getMonthlyStatistics(yearMonth,userid);

        // then
        assertEquals(1, responseList.size()); // 월별 통계 조회 결과 검증
        verify(settlementRepository, times(1)).findBySettlementPeriodStartBetween(startDate, endDate); // 호출 검증
    }
}
