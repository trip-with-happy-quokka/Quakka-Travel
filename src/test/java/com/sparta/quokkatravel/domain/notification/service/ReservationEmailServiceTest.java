package com.sparta.quokkatravel.domain.notification.service;

import com.sparta.quokkatravel.domain.accommodation.entity.Accommodation;
import com.sparta.quokkatravel.domain.email.service.EmailSendService;
import com.sparta.quokkatravel.domain.email.service.ReservationEmailService;
import com.sparta.quokkatravel.domain.reservation.entity.Reservation;
import com.sparta.quokkatravel.domain.room.entity.Room;
import com.sparta.quokkatravel.domain.user.entity.User;
import com.sparta.quokkatravel.domain.user.entity.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static org.mockito.Mockito.*;

class ReservationEmailServiceTest {

    @Mock
    private EmailSendService emailSendService;

    @InjectMocks
    private ReservationEmailService reservationEmailService;

    private User host;
    private User guest;
    private Accommodation accommodation;
    private Room room;
    private Reservation reservation;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // 가상의 데이터 설정
        host = new User("host@example.com", "host123", "HostNickname", UserRole.HOST);
        guest = new User("guest@example.com", "guest123", "GuestNickname", UserRole.GUEST);
        accommodation = new Accommodation("Sample Accommodation", "A nice place to stay", "123 Main St");
        room = new Room("Deluxe Room", "Comfortable room with amenities", 4L, 100000L, 20000L);
        reservation = new Reservation(LocalDate.of(2024, 11, 20), LocalDate.of(2024, 11, 25), 4L, guest, room);
    }

    @Test
    void testSendReservationCreationEmail() {
        // When
        reservationEmailService.sendReservationCreationEmail(host, guest.getEmail(), guest, accommodation, room, reservation);

        // Then - 호스트와 게스트에게 메일 전송이 호출되었는지 확인
        verify(emailSendService, times(1)).sendSimpleMessageAsync(
                eq(host.getEmail()), anyString(), anyString()
        );
        verify(emailSendService, times(1)).sendSimpleMessageAsync(
                eq(guest.getEmail()), anyString(), anyString()
        );
    }

    @Test
    void testSendReservationUpdateEmail() {
        // When
        reservationEmailService.sendReservationUpdateEmail(host, guest.getEmail(), guest, accommodation, room, reservation);

        // Then - 호스트와 게스트에게 메일 전송이 호출되었는지 확인
        verify(emailSendService, times(1)).sendSimpleMessageAsync(
                eq(host.getEmail()), anyString(), anyString()
        );
        verify(emailSendService, times(1)).sendSimpleMessageAsync(
                eq(guest.getEmail()), anyString(), anyString()
        );
    }

    @Test
    void testSendReservationCancellationEmail() {
        // When
        reservationEmailService.sendReservationCancellationEmail(host, guest.getEmail(), guest, accommodation, room, reservation);

        // Then - 호스트와 게스트에게 메일 전송이 호출되었는지 확인
        verify(emailSendService, times(1)).sendSimpleMessageAsync(
                eq(host.getEmail()), anyString(), anyString()
        );
        verify(emailSendService, times(1)).sendSimpleMessageAsync(
                eq(guest.getEmail()), anyString(), anyString()
        );
    }
}
