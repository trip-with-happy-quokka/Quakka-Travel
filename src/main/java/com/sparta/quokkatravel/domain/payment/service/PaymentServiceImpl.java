package com.sparta.quokkatravel.domain.payment.service;

import ch.qos.logback.core.spi.ErrorCodes;
import com.sparta.quokkatravel.domain.common.config.TossPaymentConfig;
import com.sparta.quokkatravel.domain.payment.dto.ChargingHistoryDto;
import com.sparta.quokkatravel.domain.payment.dto.PaymentResponseDto;
import com.sparta.quokkatravel.domain.payment.dto.PaymentSuccessDto;
import com.sparta.quokkatravel.domain.payment.entity.Payment;
import com.sparta.quokkatravel.domain.payment.repository.PaymentRepository;
import com.sparta.quokkatravel.domain.reservation.entity.Reservation;
import com.sparta.quokkatravel.domain.reservation.entity.ReservationStatus;
import com.sparta.quokkatravel.domain.reservation.repository.ReservationRepository;
import com.sparta.quokkatravel.domain.user.entity.User;
import com.sparta.quokkatravel.domain.user.repository.UserRepository;
import com.sparta.quokkatravel.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.data.domain.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;


@Service
@Transactional
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final TossPaymentConfig tossPaymentConfig;
    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;

    // 결제 요청 처리 메서드
    // 사용자의 결제정보를 받아서 검증하고 데이터베이스에 결제 내역 저장
    public PaymentResponseDto requestTossPayment(String userEmail, Long reservationId) {
        User user = userRepository.findByEmailOrElseThrow(userEmail);
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow();

        Payment payment = new Payment(user, reservation);

        if (payment.getAmount() < 1000) {
            throw new RuntimeException("INVALID_PAYMENT_AMOUNT");
        }
        payment.setUser(user);
        Payment savedPayment = paymentRepository.save(payment);
        return savedPayment.toPaymentResponseDto();
    }

    // 결제 승인시 호출되는 메서드
    // orderId, amount를 검증하고 결제를 승인해서 최종 결제 상탤르 업데이트함
    @Transactional
    public PaymentSuccessDto tossPaymentSuccess(String paymentKey, String orderId, Long amount) {
        Payment payment = verifyPayment(orderId, amount);
        PaymentSuccessDto result = requestPaymentAccept(paymentKey, orderId, amount);
        payment.setPaymentKey(paymentKey);
        payment.setPaySuccessYN(true);

        Reservation reservation = payment.getReservation();
        reservation.updateStatus(ReservationStatus.CONFIRMED);
        return result;
    }

    // 결제 승인 요청 메서드
    // 승인 url을 호출해서 결제 승인을 요청
    @Transactional
    public PaymentSuccessDto requestPaymentAccept(String paymentKey, String orderId, Long amount) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = getHeaders();
        JSONObject params = new JSONObject();
        params.put("orderId", orderId);
        params.put("amount", amount);

        PaymentSuccessDto result = null;
        try {
            result = restTemplate.postForObject(TossPaymentConfig.URL + paymentKey,
                    new HttpEntity<>(params, headers),
                    PaymentSuccessDto.class);
        } catch (Exception e) {
            throw new RuntimeException("ALREADY_APPROVED");
        }

        return result;

    }

    // orderId, amount를 기반으로 결제 정보를 검증하는 메서드
    public Payment verifyPayment(String orderId, Long amount) {
        Payment payment = paymentRepository.findByOrderId(orderId).orElseThrow(() -> {
            throw new RuntimeException("PAYMENT_NOT_FOUND");
        });
        if (!payment.getAmount().equals(amount)) {
            throw new RuntimeException("PAYMENT_AMOUNT_EXP");
        }
        return payment;
    }

    // 결제 실패시 호출되는 메서드
    // 실패 원인 기록 및 결제 상태 업데이트
    @Transactional
    public void tossPaymentFail(String code, String message, String orderId) {
        Payment payment = paymentRepository.findByOrderId(orderId).orElseThrow(() -> {
            throw new RuntimeException("PAYMENT_NOT_FOUND");
        });
        payment.setPaySuccessYN(false);
        payment.setFailReason(message);

        Reservation reservation = payment.getReservation();
        reservation.updateStatus(ReservationStatus.CANCELED);
    }


    @Transactional
    public Map cancelPayment(Long userId, String paymentKey, String cancelReason) {
        Payment payment = paymentRepository.findByPaymentKey(paymentKey).orElseThrow(() -> {
            throw new RuntimeException("PAYMENT_NOT_FOUND");
        });

        if(!payment.getUser().getId().equals(userId)) {
            throw new RuntimeException("PAYMENT_NOT_FOUND");
        }

        // 결제 취소 요청을 Toss Payments API에 보냄
        Map<String, Object> response = tossPaymentCancel(paymentKey, cancelReason);

        // 결제 상태를 취소로 업데이트
        payment.setCancelYN(true);
        payment.setCancelReason(cancelReason);
        paymentRepository.save(payment);

        Reservation reservation = payment.getReservation();
        reservation.updateStatus(ReservationStatus.REFUNDED);

        return response;
    }

    // 결제 취소 메서드
    // paymentKey와 cancelReason으로 취소를 진행
    public Map tossPaymentCancel(String paymentKey, String cancelReason) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = getHeaders();
        JSONObject params = new JSONObject();
        params.put("cancelReason", cancelReason);

        return restTemplate.postForObject(TossPaymentConfig.URL + paymentKey + "/cancel",
                new HttpEntity<>(params, headers),
                Map.class);
    }

    // 결제 내역 조회 메서드
    @Override
    public Page<ChargingHistoryDto> findAllChargingHistories(String username, Pageable pageable) {
        Page<Payment> page = paymentRepository.findAllByUser_Email(username, PageRequest.of(
                pageable.getPageNumber(), pageable.getPageSize(), Sort.Direction.DESC, "paymentId"));
        List<ChargingHistoryDto> dtoList = page.stream()
                .map(ChargingHistoryDto::new)
                .collect(Collectors.toList());
        return new PageImpl<>(dtoList, pageable, page.getTotalElements());
    }

    // HTTP 헤더 설정 메서드
    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        String auth = tossPaymentConfig.getTestSecretKey() + ":";
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8));
        headers.set("Authorization", "Basic " + encodedAuth);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        return headers;
    }
}