package com.sparta.quokkatravel.domain.email.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationEmailSendService {


    private final JavaMailSender emailSender;

    @Async
    public void sendSimpleMessageAsync(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
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
