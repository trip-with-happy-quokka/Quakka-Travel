package com.sparta.quokkatravel.domain.email.service;

import com.sparta.quokkatravel.domain.coupon.entity.Coupon;
import com.sparta.quokkatravel.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CouponEmailService {

    private final EmailSendService emailSendService;

    // 쿠폰 등록 시 유저에게 이메일 전송
    public void sendCouponRegistrationEmail(User user, Coupon coupon) {
        String subject = "쿠폰 등록이 성공적으로 완료되었습니다!";
        String message = generateMessage("고객님의 새로운 쿠폰 등록 정보는 다음과 같습니다.", user, coupon);
        emailSendService.sendSimpleMessageAsync(user.getEmail(), subject, message);
    }

    // 쿠폰 사용 시 유저에게 이메일 전송
    public void sendCouponRedeemEmail(User user, Coupon coupon) {
        String subject = "쿠폰 사용이 성공적으로 완료되었습니다!";
        String message = generateMessage("고객님께서 사용하신 쿠폰 정보는 다음과 같습니다.", user, coupon);
        emailSendService.sendSimpleMessageAsync(user.getEmail(), subject, message);
    }

    // 쿠폰 삭제 시 유저에게 이메일 전송
    public void sendCouponDeletionEmail(User user, Coupon coupon) {
        String subject = "쿠폰이 성공적으로 삭제되었습니다.";
        String message = generateMessage("삭제된 쿠폰 정보는 다음과 같습니다.", user, coupon);
        emailSendService.sendSimpleMessageAsync(user.getEmail(), subject, message);
    }

    // 공통 메시지 생성 메서드
    private String generateMessage(String actionMessage, User user, Coupon coupon) {
        return "\n" + actionMessage + "\n"
                + "\n[유저 닉네임] : " + user.getNickname()
                + "\n[쿠폰 이름] : " + coupon.getName()
                + "\n[쿠폰 코드] : " + coupon.getCode()
                + "\n[할인율] : " + (coupon.getDiscountRate() != null ? coupon.getDiscountRate() + "%" : "없음")
                + "\n[할인 금액] : " + (coupon.getDiscountAmount() != null ? coupon.getDiscountAmount() + "원" : "없음")
                + "\n[쿠폰 유효 기간] : " + coupon.getValidFrom() + " ~ " + coupon.getValidUntil()
                + "\n\n쿼카트래블을 이용해주셔서 감사합니다. :)";
    }
}
