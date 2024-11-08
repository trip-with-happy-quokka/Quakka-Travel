package com.sparta.quokkatravel.domain.reservation.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sparta.quokkatravel.domain.coupon.entity.Coupon;
import com.sparta.quokkatravel.domain.room.entity.Room;
import com.sparta.quokkatravel.domain.common.shared.Timestamped;
import com.sparta.quokkatravel.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Entity
@Getter
@AllArgsConstructor
@Builder
//@Table(indexes = {
//        @Index(name = "idx_reservation_user", columnList = "user_id"),
//        @Index(name = "idx_reservation_room", columnList = "room_id")
//})
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



    public Reservation() {}

    public Reservation(LocalDate startDate, LocalDate endDate, Long numberOfGuests, User user, Room room, Coupon coupon) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.numberOfGuests = numberOfGuests;
        this.totalPrice = calculateTotalPrice(startDate, endDate, room, coupon);
        this.user = user;
        this.room = room;
    }

    public void update(LocalDate startDate, LocalDate endDate, Long numberOfGuests, Room room, Coupon coupon) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.numberOfGuests = numberOfGuests;
        this.room = room;
        this.totalPrice = calculateTotalPrice(startDate, endDate, room, coupon);
    }

    public void updateStatus(ReservationStatus status) {
        this.status = status;
    }

    public Long calculateTotalPrice(LocalDate startDate, LocalDate endDate, Room room, Coupon coupon) {

        Long pricePerNight = room.getPricePerNight();

        if(room.getCapacity() < numberOfGuests) {
            pricePerNight += (numberOfGuests - room.getCapacity()) * room.getPricePerOverCapacity();
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
