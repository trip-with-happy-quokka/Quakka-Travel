package com.sparta.quokkatravel.domain.admin.loginhistory.controller;

import com.sparta.quokkatravel.domain.admin.loginhistory.dto.AdminLoginHistoryResponseDto;
import com.sparta.quokkatravel.domain.admin.loginhistory.entity.LoginHistory;
import com.sparta.quokkatravel.domain.admin.loginhistory.service.AdminLoginHistoryService;
import com.sparta.quokkatravel.domain.common.advice.ApiResponse;
import com.sparta.quokkatravel.domain.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/login-history")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
@Tag(name = "AdminLoginHistory", description = "Admin 로그인 기록 관련 컨트롤러")
public class AdminLoginHistoryController {

    private final AdminLoginHistoryService adminLoginHistoryService;

    //모든 로그인 기록 조회
    @GetMapping
    @Operation(summary = "로그인 기록 조회", description = "관리자가 모든 로그인 기록을 조회하는 API")
    public ResponseEntity<?> getAllLoginHistory() {
        List<AdminLoginHistoryResponseDto> loginHistories = adminLoginHistoryService.getAllLoginHistory();
        return ResponseEntity.ok(ApiResponse.success("로그인 기록 조회 성공", loginHistories));
    }

    //특정 사용자 로그인 기록 조회
    @GetMapping("/user/{userId}")
    @Operation(summary = "특정 사용자 로그인 기록 조회", description = "관리자가 특정 사용자의 로그인 기록을 조회하는 API")
    public ResponseEntity<?> getLoginHistoryByUserId(@PathVariable Long userId) {
        List<AdminLoginHistoryResponseDto> loginHistories = adminLoginHistoryService.getLoginHistoryByUserId(userId);
        return ResponseEntity.ok(ApiResponse.success("특정 사용자 로그인 기록 조회 성공", loginHistories));
    }
}
