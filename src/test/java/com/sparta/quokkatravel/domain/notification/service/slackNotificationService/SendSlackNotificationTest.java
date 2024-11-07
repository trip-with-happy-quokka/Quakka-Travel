package com.sparta.quokkatravel.domain.notification.service.slackNotificationService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.quokkatravel.domain.notification.service.SlackNotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class SendSlackNotificationTest {

    @InjectMocks
    private SlackNotificationService slackNotificationService;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ObjectMapper objectMapper;

    private String webhookUrl;
    private String message;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        webhookUrl = "https://hooks.slack.com/services/test/webhook";
        message = "Test Slack Notification";
    }

    @Test
    void testSendSlackNotification_Success() throws Exception {
        // Given: JSON payload and mocked object behavior
        Map<String, Object> payload = new HashMap<>();
        payload.put("text", message);
        String jsonPayload = "{\"text\":\"Test Slack Notification\"}";

        // Mocking JSON serialization and RestTemplate behavior
        when(objectMapper.writeValueAsString(payload)).thenReturn(jsonPayload);
        when(restTemplate.postForObject(eq(webhookUrl), any(HttpEntity.class), eq(String.class))).thenReturn("ok");

        // When: call the method and verify no exceptions are thrown
        assertDoesNotThrow(() -> slackNotificationService.sendSlackNotification(webhookUrl, message));

        // Verify that restTemplate was called correctly
        verify(restTemplate, times(1)).postForObject(eq(webhookUrl), any(HttpEntity.class), eq(String.class));
    }

    @Test
    void testSendSlackNotification_Exception() throws Exception {
        // Given: JSON serialization or HTTP request throws an exception
        doThrow(new RuntimeException("Slack 전송 실패")).when(restTemplate).postForObject(eq(webhookUrl), any(HttpEntity.class), eq(String.class));

        // When: method call should handle exception internally, not throw it
        assertDoesNotThrow(() -> slackNotificationService.sendSlackNotification(webhookUrl, message));

        // Verify that the exception was caught and handled
        verify(restTemplate, times(1)).postForObject(eq(webhookUrl), any(HttpEntity.class), eq(String.class));
    }
}



