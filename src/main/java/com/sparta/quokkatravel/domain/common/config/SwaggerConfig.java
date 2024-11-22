package com.sparta.quokkatravel.domain.common.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    // OpenAPI 객체를 Bean으로 등록하여 Swagger 설정을 초기화합니다.
    @Bean
    public OpenAPI openAPI() {
        String jwt = "JWT"; // JWT라는 이름의 보안 스키마를 정의합니다.

        // 보안 요구사항 설정: "JWT"를 사용하여 보안 요구사항을 설정.
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwt);

        // 보안 스키마를 구성합니다. 이 보안 스키마는 HTTP 타입으로 "bearer" 토큰을 사용.
        Components components = new Components().addSecuritySchemes(jwt, new SecurityScheme()
                .name(jwt) // 보안 스키마의 이름
                .type(SecurityScheme.Type.HTTP) // HTTP 타입의 인증을 사용
                .scheme("bearer") // Bearer 인증을 사용
                .bearerFormat("JWT") // Bearer 인증에 사용하는 포맷을 JWT로 지정
        );

        // OpenAPI 객체를 생성하여 정보, 보안 설정, 컴포넌트를 추가합니다.
        return new OpenAPI()
                .components(new Components()) // 빈 컴포넌트를 추가 (불필요하지만 추가한 후)
                .info(apiInfo()) // API에 대한 정보 추가
                .addSecurityItem(securityRequirement) // 보안 요구사항 추가
                .components(components); // JWT 인증 스키마 추가
    }

    // API의 기본 정보를 설정합니다. 제목, 설명, 버전을 명시합니다.
    private Info apiInfo() {
        return new Info()
                .title("API Test") // API의 제목을 "API Test"로 설정
                .description("Let's practice Swagger UI") // API에 대한 설명을 추가
                .version("1.0.0"); // API의 버전을 1.0.0으로 설정
    }
}
