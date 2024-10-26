package com.sparta.quokkatravel.domain.payment.dto;

import com.sparta.quokkatravel.domain.payment.entity.PayStatus;
import com.sparta.quokkatravel.domain.payment.entity.PayType;
import com.sparta.quokkatravel.domain.payment.entity.Payment;
import com.sparta.quokkatravel.domain.reservation.dto.ReservationResponseDto;
import com.sparta.quokkatravel.domain.reservation.dto.UserDto;
import com.sparta.quokkatravel.domain.reservation.entity.Reservation;
import com.sparta.quokkatravel.domain.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PaymentResponseDto {

    private Long paymentId;        // 결제 ID (DB에서 생성된 고유 식별자)
    private int amount;           // 결제 금액
    private PayStatus paymentStatus;  // 결제 상태 (SUCCESS, FAILED, PENDING)
    private PayType paymentType;
    private String paymentKey;     // 결제 시스템에서 발급된 고유 결제 키
    private String failReason;     // 결제 실패 사유 (결제가 실패한 경우)
    private boolean cancelYN;      // 결제 취소 여부
    private String cancelReason;   // 결제 취소 사유 (취소된 경우)
    private LocalDateTime paymentDate; // 결제 일시

    // 사용자 정보
    private UserDto user;     // 결제한 사용자의 이름

    // 예약 정보
    private ReservationDto reservation;


    public PaymentResponseDto(Payment payment) {
        this.paymentId = payment.getId();
        this.amount = payment.getAmount();
        this.paymentStatus = payment.getPayStatus();
        this.paymentType = payment.getPayType();
        this.paymentKey = payment.getPaymentKey();
        this.failReason = payment.getFailReason();
        this.cancelYN = payment.isCancelYN();
        this.cancelReason = payment.getCancelReason();
        this.paymentDate = payment.getCreatedAt();

        this.user = new UserDto(payment.getUser());

        this.reservation = new ReservationDto(payment.getReservation());
    }
}
