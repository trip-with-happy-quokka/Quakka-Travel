package com.sparta.quokkatravel.domain.admin.loginhistory.dto;

import com.sparta.quokkatravel.domain.admin.loginhistory.entity.LoginHistory;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AdminLoginHistoryResponseDto {

    private Long userId;
    private String nickname;
    private String ipAddress;
    private LocalDateTime loginTime;

    public AdminLoginHistoryResponseDto(LoginHistory loginHistory) {
        this.userId = loginHistory.getUser().getId();
        this.nickname = loginHistory.getUser().getNickname();
        this.ipAddress = loginHistory.getIpAddress();
        this.loginTime = loginHistory.getLoginTime();
    }
}
