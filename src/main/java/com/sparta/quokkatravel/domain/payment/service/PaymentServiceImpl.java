package com.sparta.quokkatravel.domain.payment.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.quokkatravel.domain.common.dto.CustomUserDetails;
import com.sparta.quokkatravel.domain.payment.dto.PaymentCreateRequestDto;
import com.sparta.quokkatravel.domain.payment.dto.PaymentConfirmRequestDto;
import com.sparta.quokkatravel.domain.payment.dto.PaymentResponseDto;
import com.sparta.quokkatravel.domain.payment.entity.Payment;
import com.sparta.quokkatravel.domain.payment.repository.PaymentRepository;
import com.sparta.quokkatravel.domain.reservation.entity.Reservation;
import com.sparta.quokkatravel.domain.reservation.repository.ReservationRepository;
import com.sparta.quokkatravel.domain.user.entity.User;
import com.sparta.quokkatravel.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;


@Service
@RequiredArgsConstructor
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;
    private final PaymentRepository paymentRepository;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${payments.toss.test_secrete_api_key}")
    private String tossSecretKey;

    public static final String URL = "https://api.tosspayments.com/v1/payments/confirm";


    @Override
    public PaymentResponseDto createPayment(CustomUserDetails userDetails, Long reservationId, PaymentCreateRequestDto paymentCreateRequestDto) {

        User user = userRepository.findByEmailOrElseThrow(userDetails.getEmail());
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow();

        Payment payment = new Payment(paymentCreateRequestDto, user, reservation);
        paymentRepository.save(payment);

        return new PaymentResponseDto(payment);
    }


    @Override
    public PaymentResponseDto approvePayment(String paymentKey, String orderId, int amount) throws Exception {

        String secretKey = tossSecretKey;

        URL url = new URL(URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "Basic " + Base64.getEncoder().encodeToString((secretKey + ":").getBytes()));
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        String requestBody = "{ \"paymentKey\": \"" + paymentKey + "\", \"orderId\": \"" + orderId + "\", \"amount\": " + amount + " }";

        try (OutputStream os = conn.getOutputStream()) {
            os.write(requestBody.getBytes("UTF-8"));
        }

        int responseCode = conn.getResponseCode();
        InputStream responseStream = (responseCode == 200) ? conn.getInputStream() : conn.getErrorStream();
        PaymentResponseDto response = new ObjectMapper().readValue(responseStream, PaymentResponseDto.class);
        responseStream.close();

        if (responseCode == 200) {
            logger.info("Payment successful: {}", response);
            return response;
        } else {
            logger.error("Payment failed: {}", response);
            throw new RuntimeException("결제 실패: " + response);
        }
    }




    @Override
    public PaymentResponseDto confirmPayment(Long paymentId) throws IOException {

        Payment payment = paymentRepository.findById(paymentId).orElseThrow();

        // 헤더 생성
        URL url = new URL(URL); // toss 결제 승인 API 엔드포인트
        String authorizations = getBasicAuth(tossSecretKey);

        // 결제 승인 API 호출
        // API 연결 설정 및 POST 요청 설정
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("Authorization", authorizations);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        PaymentConfirmRequestDto paymentConfirmRequestDto = new PaymentConfirmRequestDto(payment);

        // 요청 데이터 전송
        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(new ObjectMapper().writeValueAsBytes(paymentConfirmRequestDto));

        // 응답 코드 확인 및 응답스트림 선택
        int responseCode = connection.getResponseCode();
        InputStream responseStream = (responseCode == 200) ? connection.getInputStream() : connection.getErrorStream();

        // 응답 처리
        PaymentResponseDto response = new ObjectMapper().readValue(responseStream, PaymentResponseDto.class);
        responseStream.close();

        // 오류 처리
        // 결제 실패용 DTO 필요
        if (responseCode != 200) {
            logger.error("Payment failed: {}", response);
            throw new RuntimeException("결제 실패: " + response);
        }

        // 성공 로그 기록
        logger.info("Payment successful: {}", response);
        return response;
    }

    // 인증 헤더 생성 메서드
    private String getBasicAuth(String widgetSecretKey) {
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] encodedBytes = encoder.encode((widgetSecretKey + ":").getBytes(StandardCharsets.UTF_8));
        return "Basic " + new String(encodedBytes);
    }
}
