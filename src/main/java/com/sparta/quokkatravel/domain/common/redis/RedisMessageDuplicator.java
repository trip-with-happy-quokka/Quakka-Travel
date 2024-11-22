package com.sparta.quokkatravel.domain.common.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Duration;

@Component
@Slf4j
public class RedisMessageDuplicator {
    private final RedisTemplate<String, String> redisTemplate;
    private static final Duration EXPIRATION_TIME = Duration.ofSeconds(30); // 30초로 변경

    public RedisMessageDuplicator(RedisTemplate<String, String> customStringRedisTemplate) {
        this.redisTemplate = customStringRedisTemplate;
    }

    public boolean isNewMessage(String message) {
        String messageWithTimestamp = message + "_" + System.currentTimeMillis();
        String messageHash = calculateHash(messageWithTimestamp);
        log.debug("생성된 메시지 해시: {}", messageHash);

        String key = "processed_message:" + messageHash;

        try {
            Boolean isNew = redisTemplate.opsForValue().setIfAbsent(key, "1", EXPIRATION_TIME);

            if (Boolean.TRUE.equals(isNew)) {
                log.debug("새로운 메시지 감지: {}", messageWithTimestamp);
                return true;
            } else {
                log.debug("중복 메시지 감지: {}", messageWithTimestamp);
                return false;
            }
        } catch (Exception e) {
            log.error("Redis 작업 중 오류 발생", e);
            // Redis 연결 오류 시 메시지를 새 메시지로 처리
            return true;
        }
    }

    private String calculateHash(String message) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(message.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(encodedhash);
        } catch (Exception e) {
            log.error("메시지 해시 계산 중 오류 발생", e);
            throw new RuntimeException("메시지 해시 계산 중 오류 발생", e);
        }
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}