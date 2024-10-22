package com.sparta.quokkatravel.domain.payment.entity;

import com.sparta.quokkatravel.domain.accommodation.entity.Room;
import com.sparta.quokkatravel.domain.common.timestamped.Timestamped;
import com.sparta.quokkatravel.domain.reservation.entity.Reservation;
import com.sparta.quokkatravel.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Fetch;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
public class Payment extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PayMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PayStatus paymentStatus;

    private LocalDateTime paymentDate;

    @OneToOne(mappedBy = "payment")
    private Reservation reservation;

    public Payment() {}

    public Payment(int amount, PayMethod paymentMethod, PayStatus paymentStatus) {
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
    }

    public void update(int amount, PayMethod paymentMethod, PayStatus paymentStatus) {
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
    }
}
