package com.sparta.quokkatravel.domain.reservation.repository;

import aj.org.objectweb.asm.commons.Remapper;
import com.sparta.quokkatravel.domain.reservation.entity.Reservation;
import com.sparta.quokkatravel.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Page<Reservation> findByUser(User user, Pageable pageable);
}
