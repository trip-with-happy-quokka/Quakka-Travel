package com.sparta.quokkatravel.domain.admin.useractivity.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AdminUserStatusUpdateRequestDto {

    private String status;  // 새로운 상태 값을 받을 필드

    public AdminUserStatusUpdateRequestDto(String status) {
        this.status = status;
    }
}