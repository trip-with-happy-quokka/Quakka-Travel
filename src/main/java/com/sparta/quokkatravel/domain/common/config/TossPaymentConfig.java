package com.sparta.quokkatravel.domain.common.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class TossPaymentConfig {

    @Value("${payments.toss.test_client_api_key}")
    private String testClientApiKey;

    @Value("${payments.toss.test_secrete_api_key}")
    private String testSecreteApiKey;

    @Value("${payments.toss.success_url}")
    private String successUrl;

    @Value("${payments.toss.fail_url}")
    private String failUrl;

    // 토스 페이먼트에 결제 승인 요청을 보낼 URL
    public static final String URL = "https://api.tosspayments.com/v1/payments/";
}
