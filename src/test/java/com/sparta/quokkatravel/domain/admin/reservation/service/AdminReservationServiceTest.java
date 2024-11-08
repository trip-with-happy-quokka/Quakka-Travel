package com.sparta.quokkatravel.domain.admin.reservation.service;

import com.sparta.quokkatravel.domain.admin.reservation.dto.AdminReservationResponseDto;
import com.sparta.quokkatravel.domain.admin.reservation.repository.AdminReservationRepository;
import com.sparta.quokkatravel.domain.reservation.entity.Reservation;
import com.sparta.quokkatravel.domain.reservation.entity.ReservationStatus;
import com.sparta.quokkatravel.domain.room.entity.Room;
import com.sparta.quokkatravel.domain.user.entity.User;
import com.sparta.quokkatravel.domain.user.entity.UserRole;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AdminReservationServiceTest {

    @Mock
    private AdminReservationRepository adminReservationRepository;

    @InjectMocks
    private AdminReservationService adminReservationService;

    @Test
    void 모든_예약_목록_조회_테스트() {
        // given
        User user = User.builder()
                .id(1L)
                .email("dummy@example.com")
                .password("password")
                .nickname("TestUser")
                .userRole(UserRole.USER)
                .build();

        Room room = Room.builder()
                .id(1L)
                .pricePerNight(100L)
                .capacity(2L)
                .build();

        Reservation reservation = Reservation.builder()
                .id(1L)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(2))
                .numberOfGuests(2L)
                .user(user)
                .room(room)
                .totalPrice(200L)
                .status(ReservationStatus.CONFIRMED)
                .build();

        List<Reservation> reservations = List.of(reservation);
        given(adminReservationRepository.findAll()).willReturn(reservations);

        // when
        List<AdminReservationResponseDto> result = adminReservationService.getAllReservations();

        // then
        assertEquals(1, result.size()); // 반환된 예약 개수가 1인지 검증
        assertEquals(1L, result.get(0).getReservationId()); // 예약 ID 검증
        verify(adminReservationRepository, times(1)).findAll(); // 호출 횟수 검증
    }

    @Test
    void 특정_예약_상세_조회_테스트() {
        // given
        Long reservationId = 1L;
        User user = User.builder()
                .id(1L)
                .email("dummy@example.com")
                .password("password")
                .nickname("TestUser")
                .userRole(UserRole.USER)
                .build();

        Room room = Room.builder()
                .id(1L)
                .pricePerNight(100L)
                .capacity(2L)
                .build();

        Reservation reservation = Reservation.builder()
                .id(1L)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(2))
                .numberOfGuests(2L)
                .user(user)
                .room(room)
                .totalPrice(200L)
                .status(ReservationStatus.CONFIRMED)
                .build();

        given(adminReservationRepository.findById(reservationId)).willReturn(Optional.of(reservation));

        // when
        AdminReservationResponseDto result = adminReservationService.getReservationById(reservationId);

        // then
        assertEquals(reservationId, result.getReservationId()); // 예약 ID 검증
        assertEquals("CONFIRMED", result.getReservationStatus()); // 예약 상태 검증
        verify(adminReservationRepository, times(1)).findById(reservationId); // 호출 횟수 검증
    }

    @Test
    void 예약_상세_조회_실패_테스트() {
        // given
        Long reservationId = 1L;
        given(adminReservationRepository.findById(reservationId)).willReturn(Optional.empty()); // 존재하지 않는 예약 반환

        // when & then
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () ->
                adminReservationService.getReservationById(reservationId));
        assertEquals("예약을 찾을 수 없습니다.", exception.getMessage()); // 예외 메시지가 예상대로인지 검증
        verify(adminReservationRepository, times(1)).findById(reservationId); // 호출 검증
    }

    @Test
    void 예약_삭제_테스트() {
        // given
        Long reservationId = 1L;
        User user = User.builder()
                .id(1L)
                .email("dummy@example.com")
                .password("password")
                .nickname("TestUser")
                .userRole(UserRole.USER)
                .build();

        Room room = Room.builder()
                .id(1L)
                .pricePerNight(100L)
                .capacity(2L)
                .build();

        Reservation reservation = Reservation.builder()
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(2))
                .numberOfGuests(2L)
                .user(user)
                .room(room)
                .build();

        given(adminReservationRepository.findById(reservationId)).willReturn(Optional.of(reservation));

        // when
        adminReservationService.deleteReservation(reservationId);

        // then
        verify(adminReservationRepository, times(1)).findById(reservationId); // 예약 조회 호출 검증
        verify(adminReservationRepository, times(1)).delete(reservation); // 예약 삭제 호출 검증
    }
}
