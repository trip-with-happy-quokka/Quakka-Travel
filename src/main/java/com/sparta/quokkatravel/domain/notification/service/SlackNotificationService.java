package com.sparta.quokkatravel.domain.notification.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class SlackNotificationService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final RedisTemplate<String, Object> redisTemplate;


    public void sendRealTimeNotification(String eventType,String message) {
        try {
            // Redis에 알림 발행
            log.info("실시간 알림 전송 중: {} - {}", eventType, message);
            redisTemplate.convertAndSend("notifications." + eventType, message);
            log.info("Redis 채널로 실시간 알림 전송 완료: {}", eventType);

        } catch (Exception e) {
            log.error("실시간 알림 전송 중 오류 발생: ", e);
        }
    } // 레디스 펍섭 기능을 사용해서 특성 메서드를 발송하는

    public void sendSlackNotification(String webhookUrl, String message) {
        try {

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, Object> payload = new HashMap<>();
            payload.put("text", message);

            String jsonPayload = objectMapper.writeValueAsString(payload);
            log.debug("Slack용 JSON 페이로드 준비 완료: {}", jsonPayload);

            HttpEntity<String> request = new HttpEntity<>(jsonPayload, headers);

            log.debug("Slack으로 요청 전송 중...");
            String response = restTemplate.postForObject(webhookUrl, request, String.class);
            log.info("Slack 알림 전송 성공. 응답: {}", response);
        } catch (Exception e) {
            log.error("Slack 알림 전송 중 오류 발생. Webhook URL: {}, 메시지: {}, 오류: ", webhookUrl, message, e);
        }
    } // 슬랙에 페이로드에 맞게 보내줘야함: 슬랙용으로 보내는 것 -> 공통 코드임

    private String extractMessage(String jsonMessage) {
        try {
            JsonNode rootNode = objectMapper.readTree(jsonMessage);
            if (rootNode.isObject()) {
                // 메시지가 포함된 필드를 찾음
                for (String fieldName : new String[]{"message", "text", "content"}) {
                    JsonNode node = rootNode.get(fieldName);
                    if (node != null && node.isTextual()) {
                        return node.asText();
                    }
                }
                // 특정 필드를 찾지 못한 경우, 전체 JSON을 메시지로 사용
                return rootNode.toString();
            } else if (rootNode.isTextual()) {
                return rootNode.asText(); // 루트가 텍스트 노드인 경우 바로 반환
            } else {
                return rootNode.toString(); // 기타 경우에는 전체 JSON 문자열 반환
            }
        } catch (Exception e) {
            log.error("Error parsing JSON message: ", e);
            return jsonMessage; // 파싱 실패 시 원본 메시지 반환
        }
    } // 알림을 보내기 위해 제이슨 형식의 메세지가 필요한데 이때 텍스트만 추출 (data 안에 있는 부분들만 추출함) - 메세지 또는 컨텍스 필드에 있는것만 반환
}