package com.sparta.quokkatravel.domain.payment.service;

import com.sparta.quokkatravel.domain.common.dto.CustomUserDetails;
import com.sparta.quokkatravel.domain.payment.dto.PaymentRequestDto;
import com.sparta.quokkatravel.domain.payment.entity.Payment;
import com.sparta.quokkatravel.domain.payment.repository.PaymentRepository;
import com.sparta.quokkatravel.domain.reservation.entity.Reservation;
import com.sparta.quokkatravel.domain.reservation.repository.ReservationRepository;
import com.sparta.quokkatravel.domain.user.entity.User;
import com.sparta.quokkatravel.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;

    @Override
    public Payment requestPayment(CustomUserDetails userDetails, PaymentRequestDto requestDto) {

        User user = userRepository.findByEmailOrElseThrow(userDetails.getEmail());
        Reservation reservation = reservationRepository.findById(requestDto.getReservationId()).orElseThrow();

        Payment payment = new Payment(requestDto.getAmount(), requestDto.getPayType(), user, reservation);

        return null;
    }
}
