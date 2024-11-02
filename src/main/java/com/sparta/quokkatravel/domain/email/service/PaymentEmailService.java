//package com.sparta.quokkatravel.domain.email.service;
//
//import com.sparta.quokkatravel.domain.payment.entity.Payment;
//import com.sparta.quokkatravel.domain.user.entity.User;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class PaymentEmailService {
//
//    private final EmailSendService emailSendService;
//
//    // 결제 요청 시 호스트와 사용자에게 이메일 전송
//    public void sendPaymentRequestEmail(User host, User user, Payment payment) {
//        // 호스트에게 전송될 제목과 내용
//        String hostSubject = "결제 요청 알림: " + user.getNickname() + " 님이 결제를 요청했습니다.";
//        String hostMessage = generateMessage("새로운 결제 요청 정보는 다음과 같습니다.", user, payment);
//
//        // 사용자에게 전송될 제목과 내용
//        String userSubject = "결제 요청이 성공적으로 접수되었습니다.";
//        String userMessage = generateMessage("고객님의 결제 요청 정보는 다음과 같습니다.", user, payment);
//
//        // 호스트에게 전송
//        emailSendService.sendSimpleMessageAsync(host.getEmail(), hostSubject, hostMessage);
//
//        // 사용자에게 전송
//        emailSendService.sendSimpleMessageAsync(user.getEmail(), userSubject, userMessage);
//    }
//
//    // 결제 성공 시 호스트와 사용자에게 이메일 전송 -> 호스트에게만 결제 완료 보내기
//    public void sendPaymentSuccessEmail(User host, User user, Payment payment) {
//        String hostSubject = "결제 성공 알림: " + user.getNickname() + " 님의 결제가 완료되었습니다.";
//        String hostMessage = generateMessage("성공한 결제 정보는 다음과 같습니다.", user, payment);
//
//        emailSendService.sendSimpleMessageAsync(host.getEmail(), hostSubject, hostMessage);
//    }
//
//    // 결제 취소 시 호스트와 사용자에게 이메일 전송 ->
//    public void sendPaymentCancelEmail(User host, User user, Payment payment) {
//        String hostSubject = "결제 취소 알림: " + user.getNickname() + " 님의 결제가 취소되었습니다.";
//        String hostMessage = generateMessage("취소된 결제 정보는 다음과 같습니다.", user, payment);
//
//        String userSubject = "결제가 취소되었습니다.";
//        String userMessage = generateMessage("취소된 결제 정보는 다음과 같습니다.", user, payment);
//
//        emailSendService.sendSimpleMessageAsync(host.getEmail(), hostSubject, hostMessage);
//        emailSendService.sendSimpleMessageAsync(user.getEmail(), userSubject, userMessage);
//    }
//
//    // 공통 메시지 생성 메서드
//    private String generateMessage(String actionMessage, User user, Payment payment) {
//        return "\n" + actionMessage + "\n"
//                + "\n[결제자 닉네임] : " + user.getNickname()
//                + "\n[결제자 이메일주소] : " + user.getEmail()
//                + "\n[결제 금액] : " + payment.getAmount()
//                + "\n[결제 키] : " + payment.getPaymentKey()
//                + "\n\n쿼카트래블을 이용해주셔서 감사합니다. :)";
//    }
//}
//
