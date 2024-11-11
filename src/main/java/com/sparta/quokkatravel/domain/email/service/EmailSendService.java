package com.sparta.quokkatravel.domain.email.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailSendService {


    private final JavaMailSender emailSender;
    private static final Logger logger = LoggerFactory.getLogger(EmailSendService.class);

    @Async("taskExecutor")
    public void sendSimpleMessageAsync(String to, String subject, String text) {
        logger.info("Started sending email to: {}", to);  // 이메일 전송 시작 로그 추가
        // 프록시 여부와 실제 클래스 확인
        boolean isProxy = AopUtils.isAopProxy(this);
        Class<?> targetClass = AopUtils.getTargetClass(this);
        System.out.println("Is proxy: " + isProxy);
        System.out.println("Actual class: " + targetClass.getName());
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
}
