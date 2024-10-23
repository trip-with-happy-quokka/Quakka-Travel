package com.sparta.quokkatravel.domain.payment.dto;

import com.sparta.quokkatravel.domain.payment.entity.PayStatus;
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
    private Long amount;           // 결제 금액
    private String paymentStatus;  // 결제 상태 (SUCCESS, FAILED, PENDING)
    private String paymentKey;     // 결제 시스템에서 발급된 고유 결제 키
    private boolean successYN;
    private String failReason;     // 결제 실패 사유 (결제가 실패한 경우)
    private boolean cancelYN;      // 결제 취소 여부
    private String cancelReason;   // 결제 취소 사유 (취소된 경우)
    private LocalDateTime paymentDate; // 결제 일시

    // 사용자 정보
    private String userEmail;      // 결제한 사용자의 이메일
    private String userName;       // 결제한 사용자의 이름

    // 예약 정보
    private Long reservationId;    // 예약 ID (관련 예약 정보)
    private LocalDate reservationDate; // 예약 일시

    public PaymentResponseDto(Long paymentId, Long amount, PayStatus paymentStatus, String paymentKey, boolean successYN, String failReason, boolean cancelYN, String cancelReason, LocalDateTime paymentDate, User user, Reservation reservation) {
        this.paymentId = paymentId;
        this.amount = amount;
        this.paymentStatus = paymentStatus.name();
        this.paymentKey = paymentKey;
        this.successYN = successYN;
        this.failReason = failReason;
        this.cancelYN = cancelYN;
        this.cancelReason = cancelReason;
        this.paymentDate = paymentDate;

        this.userEmail = user.getEmail();
        this.userName = user.getName();

        this.reservationId = reservation.getId();
        this.reservationDate = reservation.getReservationDate();
    }
}
