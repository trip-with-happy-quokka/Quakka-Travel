package com.sparta.quokkatravel.domain.email.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationEmailSendService {


    private final JavaMailSender emailSender;
    private static final Logger logger = LoggerFactory.getLogger(ReservationEmailSendService.class);

    @Async
    public void sendSimpleMessageAsync(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);

            logger.info("Sending email to: {}", to);  // 이메일 수신자 정보 로깅
            emailSender.send(message);
            logger.info("Email sent successfully to: {}", to);  // 성공 메시지 로깅

        } catch (Exception e) {
            logger.error("Failed to send email to: {}", to, e);  // 오류 발생 시 로그 기록
        }
    }

    public void sendReservationConfirmation(String to, String reservationDetails) {
        sendSimpleMessageAsync(to, "예약 생성 성공", "고객님의 예약 정보가 다음과 같이 생성되었습니다. " + reservationDetails);
    }

    public void sendReservationUpdate(String to, String updateDetails) {
        sendSimpleMessageAsync(to, "예약 업데이트 성공", "고객님의 예약 정보가 다음과 같이 수정되었습니다." + updateDetails);
    }

    public void sendReservationCancellation(String to, String s) {
        sendSimpleMessageAsync(to, "예약 취소 성공", "고객님의 예약이 취소되었습니다.");
    }

//    public void sendCouponIssued(String to, String couponCode) {
//        sendSimpleMessageAsync(to, "Coupon Issued", "You have received a new coupon: " + couponCode);
//    }

}
