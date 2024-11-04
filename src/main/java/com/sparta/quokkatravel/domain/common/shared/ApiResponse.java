package com.sparta.quokkatravel.domain.common.shared;

import lombok.Getter;

@Getter
public class ApiResponse<T> {
    private String status;
    private String message;
    private T data;

    public ApiResponse(String status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public ApiResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }

    // 200
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>("success", message, data);
    }

    public static <T> ApiResponse<T> success(String message) {
        return new ApiResponse<>("success", message);
    }

    // 201 - Created (리소스 생성 성공)
    public static <T> ApiResponse<T> created(String message, T data) {
        return new ApiResponse<>("created", message, data);
    }

    // 202 - accepted (요청이 접수되었지만 처리중)
    public static <T> ApiResponse<T> accepted(String message, T data) {
        return new ApiResponse<>("accepted", message, data);
    }

    // 204 - no content (성공했으나 데이터 반환은 없음)
    public static <T> ApiResponse<T> noContent(String message) {
        return new ApiResponse<>("no_content", message);
    }
}
