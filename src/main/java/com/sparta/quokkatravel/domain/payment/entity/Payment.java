package com.sparta.quokkatravel.domain.payment.entity;

import com.sparta.quokkatravel.domain.payment.dto.PaymentRequestDto;
import com.sparta.quokkatravel.domain.reservation.entity.Reservation;
import com.sparta.quokkatravel.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(indexes = {
        @Index(name = "idx_payment_user", columnList = "user_id"),
        @Index(name = "idx_payment_reservation", columnList = "reservation_id"),
        @Index(name = "idx_payment_paymentKey", columnList = "paymentKey")
})
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PayStatus payStatus; // 결제 상태 SUCCESS, FAILED, PENDING

    @Column(nullable = false)
    private PayType payType; // 결제 수단 CARD, BANK

    @Column(nullable = false)
    private Long amount; // 결제 금액

    @Column(nullable = false)
    private LocalDateTime paymentDate; // 결제 일시

    @Column(unique = true)
    private String paymentKey; // 외부 결제 시스템에서 제공하는 고유 결제 키

    @Column
    private String failReason; // 결제 실패 시 실패 사유


    @Column
    private boolean cancelYN; // 결제 취소 여부
    @Column
    private String cancelReason; // 결제 취소 사유


    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToOne(mappedBy = "payment")
    @JoinColumn(name = "reservation_id", nullable = false)
    private Reservation reservation;


    public Payment(PayType payType, User user, Reservation reservation) {
        this.amount = reservation.getTotalPrice();
        this.payType = payType;
        this.user = user;
        this.reservation = reservation;
    }
}
