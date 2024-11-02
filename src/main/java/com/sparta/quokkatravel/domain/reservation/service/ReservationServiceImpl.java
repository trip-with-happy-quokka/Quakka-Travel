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
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
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

        notificationService.sendRealTimeNotification(" 예약 생성 완료 ", room.getName().toString() + " 방이 예약되었습니다. ");

        return new ReservationResponseDto(reservation);
    }

    // 예약 단건 조회
    @Override
    public ReservationResponseDto getReservation(String email, Long id) {

        User user = userRepository.findByEmailOrElseThrow(email);

        Reservation reservation = user.getReservations().stream()
                .filter(res -> res.getId().equals(id))
                .findFirst()
                .orElseThrow();

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

        User user = userRepository.findByEmailOrElseThrow(email);
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new NotFoundException("room is not found"));
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow();

        if(!reservation.getUser().equals(user)) {
            throw new AccessDeniedException("You are not the owner of this reservation");
        }

        reservation.update(reservationRequestDto.getStartDate(), reservationRequestDto.getEndDate(), reservationRequestDto.getNumberOfGuests());

        notificationService.sendRealTimeNotification(" 예약 수정 완료 ", room.getName().toString() + " 방의 예약 정보가 수정되었습니다. ");

        return new ReservationResponseDto(reservation);
    }

    // 예약 삭제
    @Override
    @Transactional
    public String cancelReservation(String email, Long roomId, Long reservationId) throws AccessDeniedException {

        User user = userRepository.findByEmailOrElseThrow(email);
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new NotFoundException("room is not found"));
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow();

        if(!reservation.getUser().equals(user)) {
            throw new AccessDeniedException("You are not the owner of this reservation");
        }

        reservationRepository.deleteById(reservationId);

        notificationService.sendRealTimeNotification(" 예약 취소 완료 ", room.getName().toString() + " 방이 취소되었습니다. ");

        return "Reservation deleted";
    }

}
