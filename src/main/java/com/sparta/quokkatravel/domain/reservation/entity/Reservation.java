package com.sparta.quokkatravel.domain.reservation.entity;

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

    @Column(nullable = false)
    private LocalDate reservationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_id", referencedColumnName = "id")
    private Payment payment;


    public Reservation() {}

    public Reservation(LocalDate startDate, LocalDate endDate, Long numberOfGuests, User user, Room room) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.numberOfGuests = numberOfGuests;
        this.totalPrice = calculateTotalPrice(startDate, endDate, room);
        this.user = user;
        this.room = room;
    }

    public void update(LocalDate startDate, LocalDate endDate, Long numberOfGuests) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.numberOfGuests = numberOfGuests;
    }

    public Long calculateTotalPrice(LocalDate startDate, LocalDate endDate, Room room) {

        Long pricePerNight = room.getPricePerNight();

        if(numberOfGuests < room.getCapacity()) {
            pricePerNight += (room.getCapacity() - numberOfGuests) * room.getPricePerOverCapacity();
        }

        Long totalDays =  ChronoUnit.DAYS.between(startDate, endDate) + 1;

        return pricePerNight * totalDays;
    }
}
