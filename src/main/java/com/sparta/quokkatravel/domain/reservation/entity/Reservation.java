package com.sparta.quokkatravel.domain.reservation.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.quokkatravel.domain.coupon.entity.Coupon;
import com.sparta.quokkatravel.domain.room.entity.Room;
import com.sparta.quokkatravel.domain.common.timestamped.Timestamped;
import com.sparta.quokkatravel.domain.payment.entity.Payment;
import com.sparta.quokkatravel.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Entity
@Getter
public class Reservation extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private Long numberOfGuests;

    @Column(nullable = false)
    private Long totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReservationStatus status = ReservationStatus.PENDING;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    @JsonBackReference
    private Room room;

    @OneToOne(mappedBy = "reservation")
    private Payment payment;


    public Reservation() {}

    public Reservation(LocalDate startDate, LocalDate endDate, Long numberOfGuests, User user, Room room, Coupon coupon) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.numberOfGuests = numberOfGuests;
        this.totalPrice = calculateTotalPrice(startDate, endDate, room, coupon);
        this.user = user;
        this.room = room;
    }

    public void update(LocalDate startDate, LocalDate endDate, Long numberOfGuests) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.numberOfGuests = numberOfGuests;
    }

    public Long calculateTotalPrice(LocalDate startDate, LocalDate endDate, Room room, Coupon coupon) {

        Long pricePerNight = room.getPricePerNight();

        if(numberOfGuests < room.getCapacity()) {
            pricePerNight += (room.getCapacity() - numberOfGuests) * room.getPricePerOverCapacity();
        }

        Long totalDays =  ChronoUnit.DAYS.between(startDate, endDate) + 1;

        long totalprice = pricePerNight * totalDays;

        if(coupon != null) {
            if(coupon.getDiscountRate() != null) {
                totalprice = totalprice * (100 - coupon.getDiscountRate()) / 100;
            } else if(coupon.getDiscountAmount() != null) {
                totalprice = totalprice - coupon.getDiscountAmount();
            }
        }

        return totalprice;
    }
}
