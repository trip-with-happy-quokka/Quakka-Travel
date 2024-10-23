package com.sparta.quokkatravel.domain.payment.service;

import com.sparta.quokkatravel.domain.common.config.TossPaymentConfig;
import com.sparta.quokkatravel.domain.common.dto.CustomUserDetails;
import com.sparta.quokkatravel.domain.payment.dto.PaymentRequestDto;
import com.sparta.quokkatravel.domain.payment.dto.PaymentResponseDto;
import com.sparta.quokkatravel.domain.payment.dto.PaymentSuccessResponseDto;
import com.sparta.quokkatravel.domain.payment.entity.Payment;
import com.sparta.quokkatravel.domain.payment.repository.PaymentRepository;
import com.sparta.quokkatravel.domain.reservation.entity.Reservation;
import com.sparta.quokkatravel.domain.reservation.repository.ReservationRepository;
import com.sparta.quokkatravel.domain.user.entity.User;
import com.sparta.quokkatravel.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;
    private final TossPaymentConfig tossPaymentConfig;

    // 결제 요청 생성
    @Override
    public PaymentResponseDto requestTossPayment(CustomUserDetails customUserDetails, Long reservationId, PaymentRequestDto paymentRequestDto) {

        User user = userRepository.findByEmailOrElseThrow(customUserDetails.getEmail());
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow();

        Payment payment = new Payment(paymentRequestDto.getPayType(), user, reservation);
        paymentRepository.save(payment);
        return new PaymentResponseDto(payment);
    }

    @Override
    public PaymentSuccessResponseDto tossPaymentSuccess(String paymentKey, String orderId, Long amount) {
        return null;
    }

    // 결제 승인 요청
    @Override
    public PaymentSuccessResponseDto requestPaymentAccept(String paymentKey, String orderId, Long amount) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = getHeaders();
        Map<String, Object> params = new HashMap<>();
        params.put("paymentKey", paymentKey);
        params.put("orderId", orderId);

        PaymentSuccessResponseDto paymentSuccessResponseDto = null;
        try {
            // 토스 API 요청을 보내면서 Map 을 요청 본문으로 사용
            paymentSuccessResponseDto = restTemplate.postForObject(TossPaymentConfig.URL + paymentKey,
                    new HttpEntity<>(params, headers), PaymentSuccessResponseDto.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send payment request", e);
        }

        return paymentSuccessResponseDto;
    }

    @Override
    public Slice<Payment> findAllChargingHistories(String username, Pageable pageable) {
        return null;
    }

    @Override
    public PaymentResponseDto verifyPayment(String orderId, Long amount) {
        return null;
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        String encodedAuthKey = new String(
                Base64.getEncoder().encode((tossPaymentConfig.getTestSecreteApiKey() + ":").getBytes(StandardCharsets.UTF_8)));

        headers.setBasicAuth(encodedAuthKey);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        return headers;
    }
}
