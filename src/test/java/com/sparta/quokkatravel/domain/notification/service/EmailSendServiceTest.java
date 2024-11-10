package com.sparta.quokkatravel.domain.notification.service;

import com.sparta.quokkatravel.domain.email.service.EmailSendService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EmailSendServiceTest {

    @Mock
    private JavaMailSender emailSender;

    @InjectMocks
    private EmailSendService emailSendService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void sendSimpleMessageAsync_Success() {
        // given
        String to = "test@example.com";
        String subject = "Test Subject";
        String text = "This is a test email message.";

        // when
        emailSendService.sendSimpleMessageAsync(to, subject, text);

        // then
        verify(emailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    void sendSimpleMessageAsync_ExceptionHandling() {
        // given
        String to = "test@example.com";
        String subject = "Test Subject";
        String text = "This is a test email message.";

        // 예외 발생을 모의(mock)합니다.
        doThrow(new RuntimeException("Email sending failed")).when(emailSender).send(any(SimpleMailMessage.class));

        // when
        emailSendService.sendSimpleMessageAsync(to, subject, text);

        // then
        verify(emailSender, times(1)).send(any(SimpleMailMessage.class));
        // 로그 출력을 검증하거나 추가로 예외 처리 로직을 테스트할 수 있습니다.
    }
}
