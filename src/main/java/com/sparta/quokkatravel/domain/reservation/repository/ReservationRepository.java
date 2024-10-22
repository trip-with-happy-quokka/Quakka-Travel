package com.sparta.quokkatravel.domain.reservation.repository;

import com.sparta.quokkatravel.domain.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
