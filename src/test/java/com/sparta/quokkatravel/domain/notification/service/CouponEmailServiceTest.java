package com.sparta.quokkatravel.domain.notification.service;

import com.sparta.quokkatravel.domain.coupon.entity.Coupon;
import com.sparta.quokkatravel.domain.coupon.entity.CouponType;
import com.sparta.quokkatravel.domain.email.service.CouponEmailService;
import com.sparta.quokkatravel.domain.email.service.EmailSendService;
import com.sparta.quokkatravel.domain.user.entity.User;
import com.sparta.quokkatravel.domain.user.entity.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

class CouponEmailServiceTest {

    @Mock
    private EmailSendService emailSendService;

    @InjectMocks
    private CouponEmailService couponEmailService;

    private User user;
    private Coupon coupon;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // 테스트용 데이터 설정
        user = new User("test@example.com", "password123", "testUser", UserRole.GUEST);
        coupon = new Coupon("COUPON123", "Sample Coupon", 1000, LocalDate.now(), LocalDate.now().plusDays(30), CouponType.EVENT, "Sample Content");
    }

    @Test
    void testSendCouponRegistrationEmail() {
        // Expected values
        String expectedSubject = "쿠폰 등록이 성공적으로 완료되었습니다!";
        String expectedMessage = "\n고객님의 새로운 쿠폰 등록 정보는 다음과 같습니다.\n"
                + "\n[유저 닉네임] : " + user.getNickname()
                + "\n[쿠폰 이름] : " + coupon.getName()
                + "\n[쿠폰 코드] : " + coupon.getCode()
                + "\n[할인율] : 없음"
                + "\n[할인 금액] : " + coupon.getDiscountAmount() + "원"
                + "\n[쿠폰 유효 기간] : " + coupon.getValidFrom() + " ~ " + coupon.getValidUntil()
                + "\n\n쿼카트래블을 이용해주셔서 감사합니다. :)";

        // Method call
        couponEmailService.sendCouponRegistrationEmail(user, coupon);

        // Capture and verify email sending
        ArgumentCaptor<String> emailCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> subjectCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);

        verify(emailSendService).sendSimpleMessageAsync(emailCaptor.capture(), subjectCaptor.capture(), messageCaptor.capture());

        // Assertions
        assertEquals(user.getEmail(), emailCaptor.getValue());
        assertEquals(expectedSubject, subjectCaptor.getValue());
        assertEquals(expectedMessage, messageCaptor.getValue());
    }

    @Test
    void testSendCouponRedeemEmail() {
        // Expected values
        String expectedSubject = "쿠폰 사용이 성공적으로 완료되었습니다!";
        String expectedMessage = "\n고객님께서 사용하신 쿠폰 정보는 다음과 같습니다.\n"
                + "\n[유저 닉네임] : " + user.getNickname()
                + "\n[쿠폰 이름] : " + coupon.getName()
                + "\n[쿠폰 코드] : " + coupon.getCode()
                + "\n[할인율] : 없음"
                + "\n[할인 금액] : " + coupon.getDiscountAmount() + "원"
                + "\n[쿠폰 유효 기간] : " + coupon.getValidFrom() + " ~ " + coupon.getValidUntil()
                + "\n\n쿼카트래블을 이용해주셔서 감사합니다. :)";

        // Method call
        couponEmailService.sendCouponRedeemEmail(user, coupon);

        // Capture and verify email sending
        ArgumentCaptor<String> emailCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> subjectCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);

        verify(emailSendService).sendSimpleMessageAsync(emailCaptor.capture(), subjectCaptor.capture(), messageCaptor.capture());

        // Assertions
        assertEquals(user.getEmail(), emailCaptor.getValue());
        assertEquals(expectedSubject, subjectCaptor.getValue());
        assertEquals(expectedMessage, messageCaptor.getValue());
    }

    @Test
    void testSendCouponDeletionEmail() {
        // Expected values
        String expectedSubject = "쿠폰이 성공적으로 삭제되었습니다.";
        String expectedMessage = "\n삭제된 쿠폰 정보는 다음과 같습니다.\n"
                + "\n[유저 닉네임] : " + user.getNickname()
                + "\n[쿠폰 이름] : " + coupon.getName()
                + "\n[쿠폰 코드] : " + coupon.getCode()
                + "\n[할인율] : 없음"
                + "\n[할인 금액] : " + coupon.getDiscountAmount() + "원"
                + "\n[쿠폰 유효 기간] : " + coupon.getValidFrom() + " ~ " + coupon.getValidUntil()
                + "\n\n쿼카트래블을 이용해주셔서 감사합니다. :)";

        // Method call
        couponEmailService.sendCouponDeletionEmail(user, coupon);

        // Capture and verify email sending
        ArgumentCaptor<String> emailCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> subjectCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);

        verify(emailSendService).sendSimpleMessageAsync(emailCaptor.capture(), subjectCaptor.capture(), messageCaptor.capture());

        // Assertions
        assertEquals(user.getEmail(), emailCaptor.getValue());
        assertEquals(expectedSubject, subjectCaptor.getValue());
        assertEquals(expectedMessage, messageCaptor.getValue());
    }
}
