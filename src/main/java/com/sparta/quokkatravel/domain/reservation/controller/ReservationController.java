package com.sparta.quokkatravel.domain.reservation.controller;

import com.sparta.quokkatravel.domain.reservation.repository.ReservationRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/guest")
@PreAuthorize("hasRole('ROLE_GUEST') or hasRole('ROLE_ADMIN')")
@RequiredArgsConstructor
@Tag(name = "Reservation", description = "예약 관련 컨트롤러")
public class ReservationController {

    private final ReservationRepository reservationRepository;

    // 에약 작성

    // 에약 전체 조회

    // 예약 단건 조회

    // 예약 수정

    // 예약 취소


}
