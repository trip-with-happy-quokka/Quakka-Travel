package com.sparta.quokkatravel.domain.admin.reservation.repository;

import com.sparta.quokkatravel.domain.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AdminReservationRepository extends JpaRepository<Reservation, Long> {
}