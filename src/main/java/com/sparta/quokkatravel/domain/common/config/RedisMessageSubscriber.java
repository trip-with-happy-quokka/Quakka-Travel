package com.sparta.quokkatravel.domain.common.config;

import com.sparta.quokkatravel.domain.notification.service.NotificationService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;
import org.springframework.data.redis.connection.Message;

import java.net.InetAddress;


@Component
@Slf4j
public class RedisMessageSubscriber implements MessageListener {
    @Value("${slack.webhook-url}")
    String slackWebhookUrl;


    private final NotificationService notificationService;
    private final RedisMessageDuplicator deduplicator;


    public RedisMessageSubscriber(NotificationService notificationService, RedisMessageDuplicator deduplicator) {
        this.notificationService = notificationService;
        this.deduplicator = deduplicator;
    } // 생성자 주입

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            String currentIp = InetAddress.getLocalHost().getHostAddress(); // 현재 서버 IP 가져오기
            log.info("현재 서버 IP: {}", currentIp);

            String notification = new String(message.getBody());
            log.info("수신된 메시지: {}", notification);

            if (deduplicator.isNewMessage(notification)) {
                log.debug("Slack 알림 전송 시도 중...");
                notificationService.sendSlackNotification(slackWebhookUrl, notification);
                log.info("Slack으로 알림 전송 완료: {}", notification);
            } else {
                log.info("중복 메시지 감지, 무시됨: {}", notification);
            }
        } catch (Exception e) {
            log.error("메시지 처리 중 오류 발생: ", e);
        }
    } // 메세지: 수신된 메세지 본문 / 바이트: 패턴의미
}