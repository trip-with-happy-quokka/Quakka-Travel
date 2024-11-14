package com.sparta.quokkatravel.domain.reservation.service;

import com.sparta.quokkatravel.domain.common.monitoring.MetricService;
import com.sparta.quokkatravel.domain.coupon.entity.Coupon;
import com.sparta.quokkatravel.domain.coupon.repository.CouponRepository;
import com.sparta.quokkatravel.domain.notification.service.SlackNotificationService;
import com.sparta.quokkatravel.domain.reservation.dto.ReservationRequestDto;
import com.sparta.quokkatravel.domain.reservation.dto.ReservationResponseDto;
import com.sparta.quokkatravel.domain.reservation.entity.Reservation;
import com.sparta.quokkatravel.domain.reservation.repository.ReservationRepository;
import com.sparta.quokkatravel.domain.room.entity.Room;
import com.sparta.quokkatravel.domain.room.repository.RoomRepository;
import com.sparta.quokkatravel.domain.user.entity.User;
import com.sparta.quokkatravel.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoomRepository roomRepository;
    @Mock
    private CouponRepository couponRepository;
    @Mock
    private SlackNotificationService notificationService;
    @Mock
    private MetricService metricService;

    @InjectMocks
    private ReservationServiceImpl reservationService;

    private User user;
    private Room room;
    private Coupon coupon;
    private Reservation reservation;
    private ReservationResponseDto reservationResponseDto;

    @BeforeEach
    void setUp() {
        user = new User();
        ReflectionTestUtils.setField(user, "id", 1L);
        ReflectionTestUtils.setField(user, "email", "test@example.com");

        room = new Room();
        ReflectionTestUtils.setField(room, "id", 1L);
        ReflectionTestUtils.setField(room, "name", "Test Room");
        ReflectionTestUtils.setField(room, "description", "Test Room Description");
        ReflectionTestUtils.setField(room, "capacity", 2L);
        ReflectionTestUtils.setField(room, "pricePerOverCapacity", 10000L);
        ReflectionTestUtils.setField(room, "pricePerNight", 20000L);

        coupon = new Coupon();
        ReflectionTestUtils.setField(coupon, "id", 1L);
        ReflectionTestUtils.setField(coupon, "name", "Test Coupon");
        ReflectionTestUtils.setField(coupon, "code", "Test Code");

        reservation = new Reservation(LocalDate.now(), LocalDate.now().plusDays(1), 2L, user, room, coupon);
        ReflectionTestUtils.setField(reservation, "id", 1L);
    }

    @Test
    void 예약_생성_성공() {
        ReservationRequestDto reservationRequestDto = new ReservationRequestDto(
                LocalDate.now(), LocalDate.now().plusDays(1), 2L, "Test Code"
        );

        given(userRepository.findByEmailOrElseThrow(user.getEmail())).willReturn(user);
        given(roomRepository.findById(room.getId())).willReturn(Optional.of(room));
        given(couponRepository.findByCode(coupon.getCode())).willReturn(Optional.of(coupon));
        given(reservationRepository.save(any(Reservation.class))).willReturn(reservation);

        // when
        ReservationResponseDto reservationResponseDto = reservationService.createReservation(user.getEmail(), room.getId(), reservationRequestDto);

        // then
        assertNotNull(reservationResponseDto);
        assertEquals(reservation.getTotalPrice(), reservationResponseDto.getTotalPrice());
        verify(metricService).incrementCounter(
                eq("reservation.create.count"), anyString(), anyMap()
        );
        verify(notificationService).sendRealTimeNotification(" 예약 생성 완료 ", "Test Room 방이 예약되었습니다. ");
    }

    @Test
    void 예약_단건_조회_성공() {
        // given
        given(userRepository.findByEmailOrElseThrow(user.getEmail())).willReturn(user);
        given(reservationRepository.findById(reservation.getId())).willReturn(Optional.of(reservation));

        // when
        ReservationResponseDto reservationResponseDto = reservationService.getReservation(user.getEmail(), reservation.getId());

        // then
        assertNotNull(reservationResponseDto);
        assertEquals(reservation.getTotalPrice(), reservationResponseDto.getTotalPrice());
        assertEquals(user.getEmail(), reservationResponseDto.getUser().getEmail());
    }

    @Test
    void 예약_전체_조회_성공() {
        // given
        PageRequest pageable = PageRequest.of(0, 10);
        Page<Reservation> reservationPage = new PageImpl<>(Collections.singletonList(reservation), pageable, 1);

        given(userRepository.findByEmailOrElseThrow(user.getEmail())).willReturn(user);
        given(reservationRepository.findByUser(user, pageable)).willReturn(reservationPage);

        // when
        Page<ReservationResponseDto> reservationResponseDtoPage = reservationService.getAllReservation(user.getEmail(), pageable);

        // then
        assertNotNull(reservationResponseDtoPage);
        assertEquals(1, reservationResponseDtoPage.getTotalElements());
        assertEquals(reservation.getTotalPrice(), reservationResponseDtoPage.getContent().get(0).getTotalPrice());
    }

    @Test
    void 예약_수정_성공() {
        // given
        ReservationRequestDto reservationRequestDto = new ReservationRequestDto(
                LocalDate.now().plusDays(1), LocalDate.now().plusDays(2), 3L, "Test Code"
        );

        given(reservationRepository.findById(reservation.getId())).willReturn(Optional.of(reservation));
        given(roomRepository.findById(room.getId())).willReturn(Optional.of(room));
        given(couponRepository.findByCode(coupon.getCode())).willReturn(Optional.of(coupon));

        // when
        ReservationResponseDto updatedReservation = reservationService.updateReservation(user.getEmail(), room.getId(), reservation.getId(), reservationRequestDto);

        // then
        assertNotNull(updatedReservation);
        assertEquals(3L, updatedReservation.getNumberOfGuests());
        verify(notificationService).sendRealTimeNotification(" 예약 수정 완료 ", "Test Room 방의 예약 정보가 수정되었습니다. ");
    }

    @Test
    void 예약_삭제_성공() {
        // given
        given(reservationRepository.findById(reservation.getId())).willReturn(Optional.of(reservation));

        // when
        String response = reservationService.cancelReservation(user.getEmail(), room.getId(), reservation.getId());

        // then
        assertEquals("Reservation deleted", response);
        verify(metricService).incrementCounter(eq("reservation.cancel.count"), anyString(), anyMap());
        verify(notificationService).sendRealTimeNotification(" 예약 취소 완료 ", "Test Room 방이 취소되었습니다. ");
    }
}
