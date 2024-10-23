package com.sparta.quokkatravel.domain.reservation.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.quokkatravel.domain.accommodation.entity.Accommodation;
import com.sparta.quokkatravel.domain.reservation.entity.QReservation;
import com.sparta.quokkatravel.domain.reservation.entity.Reservation;
import com.sparta.quokkatravel.domain.user.entity.User;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ReservationRepositorySupport extends QuerydslRepositorySupport {

    private final JPAQueryFactory queryFactory;

    public ReservationRepositorySupport(JPAQueryFactory queryFactory) {
        super(Accommodation.class);
        this.queryFactory = queryFactory;
    }

    public Optional<Reservation> findByUserAndReservationId(User user, Long reservationId) {
        QReservation qReservation = QReservation.reservation;

        Reservation reservation = queryFactory
                .selectFrom(qReservation)
                .where(qReservation.user.eq(user).and(qReservation.id.eq(reservationId)))
                .fetchOne();

        return Optional.ofNullable(reservation);
    }
}
