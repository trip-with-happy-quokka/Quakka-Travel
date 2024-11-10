package com.sparta.quokkatravel.domain.payment.entity;

import com.sparta.quokkatravel.domain.common.shared.Timestamped;
import com.sparta.quokkatravel.domain.payment.dto.PaymentResponseDto;
import com.sparta.quokkatravel.domain.reservation.entity.Reservation;
import com.sparta.quokkatravel.domain.room.entity.Room;
import com.sparta.quokkatravel.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Table(indexes = {
        @Index(name = "idx_payment_user", columnList = "user_id"),
        @Index(name = "idx_payment_paymentKey", columnList = "paymentKey" ),
})
public class Payment extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id", nullable = false, unique = true)
    private Long paymentId;

    @Column(nullable = false , name = "pay_amount")
    private Long amount;

    @Column(nullable = false , name = "pay_name")
    private String orderName;

    @Column(nullable = false , name = "order_id")
    private String orderId;

    private boolean paySuccessYN;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @Column
    private String paymentKey;
    @Column
    private String failReason;

    @Column
    private boolean cancelYN;

    @Column
    private String cancelReason;

    public PaymentResponseDto toPaymentResponseDto() { // DB에 저장하게 될 결제 관련 정보들
        return PaymentResponseDto.builder()
                .amount(amount)
                .orderName(orderName)
                .orderId(orderId)
                .customerEmail(user.getEmail())
                .customerName(user.getNickname())
                .createdAt(String.valueOf(getCreatedAt()))
                .cancelYN(cancelYN)
                .failReason(failReason)
                .build();
    }

    public Payment(User user, Reservation reservation) {
        this.amount = reservation.getTotalPrice();
        this.orderName = reservation.getId().toString();
        this.orderId = UUID.randomUUID().toString();
        this.user = user;
        this.reservation = reservation;
        this.paySuccessYN = false;

    }
}