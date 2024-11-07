package com.sparta.quokkatravel.domain.reservation.service;

import com.sparta.quokkatravel.domain.common.exception.InvalidRequestException;
import com.sparta.quokkatravel.domain.coupon.entity.Coupon;
import com.sparta.quokkatravel.domain.coupon.repository.CouponRepository;
import com.sparta.quokkatravel.domain.notification.service.NotificationService;
import com.sparta.quokkatravel.domain.room.entity.Room;
import com.sparta.quokkatravel.domain.common.exception.NotFoundException;
import com.sparta.quokkatravel.domain.reservation.dto.ReservationRequestDto;
import com.sparta.quokkatravel.domain.reservation.dto.ReservationResponseDto;
import com.sparta.quokkatravel.domain.reservation.entity.Reservation;
import com.sparta.quokkatravel.domain.reservation.repository.ReservationRepository;
import com.sparta.quokkatravel.domain.room.repository.RoomRepository;
import com.sparta.quokkatravel.domain.user.entity.User;
import com.sparta.quokkatravel.domain.user.repository.UserRepository;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.security.access.AccessDeniedException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final CouponRepository couponRepository;
    private final NotificationService notificationService;
    private final MeterRegistry meterRegistry;

    // 예약 생성
    @Override
    @Transactional
    public ReservationResponseDto createReservation(String email, Long roomId, ReservationRequestDto reservationRequestDto) {

        User user = userRepository.findByEmailOrElseThrow(email);
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new NotFoundException("room is not found"));
        Coupon coupon = null;

        if(reservationRequestDto.getCouponCode() != null) {
            coupon = couponRepository.findByCode(reservationRequestDto.getCouponCode()).orElseThrow(() -> new NotFoundException("coupon is not found"));
            if(!Objects.equals(coupon.getAccommodation(), room.getAccommodation())) {
                throw new InvalidRequestException("coupon code is incorrect");
            }
        }

        Reservation reservation = new Reservation(reservationRequestDto.getStartDate(), reservationRequestDto.getEndDate(), reservationRequestDto.getNumberOfGuests(), user, room, coupon);
        reservationRepository.save(reservation);

        // 예약 생성 카운터 증가
        Counter.builder("reservation.create.count")
                .tag("class", this.getClass().getName())
                .tag("method", "createReservation")
                .description("Counts the number of reservation creations")
                .register(meterRegistry)
                .increment();

        notificationService.sendRealTimeNotification(" 예약 생성 완료 ", room.getName().toString() + " 방이 예약되었습니다. ");

        return new ReservationResponseDto(reservation);
    }

    // 예약 단건 조회
    @Override
    public ReservationResponseDto getReservation(String email, Long id) {

        Reservation reservation = reservationRepository.findById(id).orElseThrow();

        if(!reservation.getUser().getEmail().equals(email)) {
            throw new AccessDeniedException("Is not yours");
        }

        return new ReservationResponseDto(reservation);
    }

    // 예약 전체 조회
    @Override
    public Page<ReservationResponseDto> getAllReservation(String email, Pageable pageable) {

        User user = userRepository.findByEmailOrElseThrow(email);

        return reservationRepository.findByUser(user, pageable)
                .map(ReservationResponseDto::new);
    }

    // 예약 수정
    @Override
    @Transactional
    public ReservationResponseDto updateReservation(String email, Long roomId, Long reservationId, ReservationRequestDto reservationRequestDto) throws AccessDeniedException {

        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow();
        Room room = roomRepository.findById(roomId).orElseThrow();
        Coupon coupon = null;

        if(reservationRequestDto.getCouponCode() != null) {
            coupon = couponRepository.findByCode(reservationRequestDto.getCouponCode()).orElseThrow(() -> new NotFoundException("coupon is not found"));
            if(!Objects.equals(coupon.getAccommodation(), room.getAccommodation())) {
                throw new InvalidRequestException("coupon code is incorrect");
            }
        }

        if(!reservation.getUser().getEmail().equals(email)) {
            throw new AccessDeniedException("You are not the owner of this reservation");
        }
        if(!reservation.getRoom().getId().equals(roomId)) {
            throw new AccessDeniedException("This room is different this reservation's contents");
        }

        reservation.update(reservationRequestDto.getStartDate(), reservationRequestDto.getEndDate(), reservationRequestDto.getNumberOfGuests(), room, coupon);

        notificationService.sendRealTimeNotification(" 예약 수정 완료 ", reservation.getRoom().getName() + " 방의 예약 정보가 수정되었습니다. ");

        return new ReservationResponseDto(reservation);
    }

    // 예약 삭제
    @Override
    @Transactional
    public String cancelReservation(String email, Long roomId, Long reservationId) throws AccessDeniedException {

        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow();

        if(!reservation.getUser().getEmail().equals(email)) {
            throw new AccessDeniedException("You are not the owner of this reservation");
        }
        if(!reservation.getRoom().getId().equals(roomId)) {
            throw new AccessDeniedException("This room is different this reservation's contents");
        }

        reservationRepository.deleteById(reservationId);

        // 예약 삭제 카운터 증가
        Counter.builder("reservation.cancel.count")
                .tag("class", this.getClass().getName())
                .tag("method", "cancelReservation")
                .description("Counts the number of reservation cancellations")
                .register(meterRegistry)
                .increment();

        notificationService.sendRealTimeNotification(" 예약 취소 완료 ", reservation.getRoom().getName() + " 방이 취소되었습니다. ");

        return "Reservation deleted";
    }

}
