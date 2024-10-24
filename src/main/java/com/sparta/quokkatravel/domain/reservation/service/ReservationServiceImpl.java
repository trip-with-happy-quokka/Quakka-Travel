package com.sparta.quokkatravel.domain.reservation.service;

import com.sparta.quokkatravel.domain.room.entity.Room;
import com.sparta.quokkatravel.domain.common.dto.CustomUserDetails;
import com.sparta.quokkatravel.domain.common.exception.NotFoundException;
import com.sparta.quokkatravel.domain.reservation.dto.ReservationRequestDto;
import com.sparta.quokkatravel.domain.reservation.dto.ReservationResponseDto;
import com.sparta.quokkatravel.domain.reservation.entity.Reservation;
import com.sparta.quokkatravel.domain.reservation.repository.ReservationRepository;
import com.sparta.quokkatravel.domain.reservation.repository.ReservationRepositorySupport;
import com.sparta.quokkatravel.domain.room.repository.RoomRepository;
import com.sparta.quokkatravel.domain.user.entity.User;
import com.sparta.quokkatravel.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final ReservationRepositorySupport reservationRepositorySupport;

    // 예약 생성
    @Override
    public ReservationResponseDto createReservation(CustomUserDetails userDetails, Long roomId, ReservationRequestDto reservationRequestDto) {

        User user = userRepository.findByEmailOrElseThrow(userDetails.getEmail());
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new NotFoundException("room is not found"));
        Reservation reservation = new Reservation(reservationRequestDto.getStartDate(), reservationRequestDto.getEndDate(), reservationRequestDto.getNumberOfGuests(), user, room);
        reservationRepository.save(reservation);

        return new ReservationResponseDto(reservation);
    }

    // 예약 단건 조회
    @Override
    public ReservationResponseDto getReservation(CustomUserDetails userDetails, Long id) {

        User user = userRepository.findByEmailOrElseThrow(userDetails.getEmail());

        Reservation reservation = user.getReservations().stream()
                .filter(res -> res.getId().equals(id))
                .findFirst()
                .orElseThrow();

        return new ReservationResponseDto(reservation);
    }

    // 예약 전체 조회
    @Override
    public Page<ReservationResponseDto> getAllReservation(CustomUserDetails userDetails, Pageable pageable) {

        User user = userRepository.findByEmailOrElseThrow(userDetails.getEmail());

        return reservationRepository.findByUser(user, pageable)
                .map(ReservationResponseDto::new);
    }

    // 예약 수정
    @Override
    public ReservationResponseDto updateReservation(CustomUserDetails userDetails, Long id, ReservationRequestDto reservationRequestDto) throws AccessDeniedException {

        User user = userRepository.findByEmailOrElseThrow(userDetails.getEmail());
        Reservation reservation = reservationRepository.findById(id).orElseThrow();

        if(!reservation.getUser().equals(user)) {
            throw new AccessDeniedException("You are not the owner of this reservation");
        }

        reservation.update(reservationRequestDto.getStartDate(), reservationRequestDto.getEndDate(), reservationRequestDto.getNumberOfGuests());

        return new ReservationResponseDto(reservation);
    }

    // 예약 삭제
    @Override
    public String deleteReservation(CustomUserDetails userDetails, Long id) throws AccessDeniedException {

        User user = userRepository.findByEmailOrElseThrow(userDetails.getEmail());
        Reservation reservation = reservationRepository.findById(id).orElseThrow();

        if(!reservation.getUser().equals(user)) {
            throw new AccessDeniedException("You are not the owner of this reservation");
        }

        reservationRepository.deleteById(id);

        return "Reservation deleted";
    }

}
