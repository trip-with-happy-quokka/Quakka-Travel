package com.sparta.quokkatravel.domain.admin.revenuedetail.service;

import com.sparta.quokkatravel.domain.admin.revenue.revenuedetail.dto.RevenueDetailRequestDto;
import com.sparta.quokkatravel.domain.admin.revenue.revenuedetail.entity.RevenueDetail;
import com.sparta.quokkatravel.domain.admin.revenue.revenuedetail.repository.RevenueDetailRepository;
import com.sparta.quokkatravel.domain.admin.revenue.revenuedetail.service.RevenueDetailService;
import com.sparta.quokkatravel.domain.admin.revenue.settlementinfo.entity.SettlementInfo;
import com.sparta.quokkatravel.domain.admin.revenue.settlementinfo.repository.SettlementRepository;
import com.sparta.quokkatravel.domain.coupon.entity.Coupon;
import com.sparta.quokkatravel.domain.coupon.repository.CouponRepository;
import com.sparta.quokkatravel.domain.reservation.entity.Reservation;
import com.sparta.quokkatravel.domain.reservation.repository.ReservationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RevenueDetailServiceTest {

    @Mock
    private RevenueDetailRepository revenueDetailRepository;

    @Mock
    private SettlementRepository settlementRepository;

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private CouponRepository couponRepository;

    @InjectMocks
    private RevenueDetailService revenueDetailService;

    @Test
    void createRevenueDetail_정상적으로_생성() {
        // given
        Long settlementId = 1L;
        Long reservationId = 2L;
        Long couponId = 3L;

        // Mock 데이터 설정
        SettlementInfo settlementInfo = new SettlementInfo();
        Reservation reservation = new Reservation();
        Coupon coupon = new Coupon();

        // ReflectionTestUtils를 사용하여 DTO 필드 값 설정
        RevenueDetailRequestDto requestDto = new RevenueDetailRequestDto();
        ReflectionTestUtils.setField(requestDto, "settlementId", settlementId);
        ReflectionTestUtils.setField(requestDto, "reservationId", reservationId);
        ReflectionTestUtils.setField(requestDto, "productName", "Sample Product");
        ReflectionTestUtils.setField(requestDto, "productPrice", new BigDecimal("500.00"));
        ReflectionTestUtils.setField(requestDto, "paymentMethod", "Credit Card");
        ReflectionTestUtils.setField(requestDto, "additionalIncome", new BigDecimal("50.00"));
        ReflectionTestUtils.setField(requestDto, "refundAmount", new BigDecimal("0.00"));
        ReflectionTestUtils.setField(requestDto, "couponId", couponId);

        // Mock 동작 설정
        given(settlementRepository.findById(settlementId)).willReturn(Optional.of(settlementInfo));
        given(reservationRepository.findById(reservationId)).willReturn(Optional.of(reservation));
        given(couponRepository.findById(couponId)).willReturn(Optional.of(coupon));

        // when
        revenueDetailService.createRevenueDetail(requestDto);

        // then
        ArgumentCaptor<RevenueDetail> captor = ArgumentCaptor.forClass(RevenueDetail.class);
        verify(revenueDetailRepository, times(1)).save(captor.capture());
        RevenueDetail capturedDetail = captor.getValue();

        // 필드 값 검증
        assertEquals("Sample Product", capturedDetail.getProductName());
        assertEquals(new BigDecimal("500.00"), capturedDetail.getProductPrice());
        assertEquals("Credit Card", capturedDetail.getPaymentMethod());
        assertEquals(new BigDecimal("50.00"), capturedDetail.getAdditionalIncome());
        assertEquals(new BigDecimal("0.00"), capturedDetail.getRefundAmount());
        assertEquals(coupon, capturedDetail.getCoupon());
    }

    @Test
    void createRevenueDetail_정산정보없으면_예외발생() {
        // given
        Long settlementId = 1L;
        RevenueDetailRequestDto requestDto = new RevenueDetailRequestDto();
        ReflectionTestUtils.setField(requestDto, "settlementId", settlementId);

        // Mock 동작 설정 - 정산 정보가 없는 경우
        given(settlementRepository.findById(settlementId)).willReturn(Optional.empty());

        // when & then
        assertThrows(RuntimeException.class, () -> revenueDetailService.createRevenueDetail(requestDto)); // 예외 발생 검증
        verify(settlementRepository, times(1)).findById(settlementId); // 호출 검증
    }

    @Test
    void updateRevenueDetail_정상적으로_업데이트() {
        // given
        Long revenueId = 1L;
        Long couponId = 3L;
        RevenueDetail revenueDetail = new RevenueDetail();
        Coupon coupon = new Coupon();

        RevenueDetailRequestDto requestDto = new RevenueDetailRequestDto();
        ReflectionTestUtils.setField(requestDto, "productName", "Updated Product");
        ReflectionTestUtils.setField(requestDto, "productPrice", new BigDecimal("600.00"));
        ReflectionTestUtils.setField(requestDto, "paymentMethod", "Debit Card");
        ReflectionTestUtils.setField(requestDto, "additionalIncome", new BigDecimal("30.00"));
        ReflectionTestUtils.setField(requestDto, "refundAmount", new BigDecimal("10.00"));
        ReflectionTestUtils.setField(requestDto, "couponId", couponId);

        given(revenueDetailRepository.findById(revenueId)).willReturn(Optional.of(revenueDetail));
        given(couponRepository.findById(couponId)).willReturn(Optional.of(coupon));

        // when
        revenueDetailService.updateRevenueDetail(revenueId, requestDto);

        // then
        verify(revenueDetailRepository, times(1)).findById(revenueId);
        verify(revenueDetailRepository, times(1)).save(revenueDetail);
        assertEquals("Updated Product", revenueDetail.getProductName());
        assertEquals(new BigDecimal("600.00"), revenueDetail.getProductPrice());
        assertEquals("Debit Card", revenueDetail.getPaymentMethod());
        assertEquals(new BigDecimal("30.00"), revenueDetail.getAdditionalIncome());
        assertEquals(new BigDecimal("10.00"), revenueDetail.getRefundAmount());
        assertEquals(coupon, revenueDetail.getCoupon());
    }

    @Test
    void updateRevenueDetail_수익내역없으면_예외발생() {
        // given
        Long revenueId = 1L;
        RevenueDetailRequestDto requestDto = new RevenueDetailRequestDto();

        given(revenueDetailRepository.findById(revenueId)).willReturn(Optional.empty());

        // when & then
        assertThrows(RuntimeException.class, () -> revenueDetailService.updateRevenueDetail(revenueId, requestDto));
        verify(revenueDetailRepository, times(1)).findById(revenueId);
    }

    @Test
    void deleteRevenueDetail_정상적으로_삭제() {
        // given
        Long revenueId = 1L;

        // when
        revenueDetailService.deleteRevenueDetail(revenueId);

        // then
        verify(revenueDetailRepository, times(1)).deleteById(revenueId);
    }
}
