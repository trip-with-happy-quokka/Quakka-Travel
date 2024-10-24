package com.sparta.quokkatravel.domain.payment.dto;

import com.sparta.quokkatravel.domain.payment.entity.PayStatus;
import com.sparta.quokkatravel.domain.payment.entity.PayType;
import com.sparta.quokkatravel.domain.reservation.entity.Reservation;
import com.sparta.quokkatravel.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PaymentCreateRequestDto {

    private PayStatus payStatus; // 결제 상태 SUCCESS, FAILED, PENDING
    private PayType payType; // 결제 수단 CARD, BANK

    public PaymentCreateRequestDto(PayStatus payStatus, PayType payType) {
        this.payStatus = payStatus;
        this.payType = payType;
    }
}
