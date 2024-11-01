package com.sparta.quokkatravel.domain.admin.useractivity.controller;

import com.sparta.quokkatravel.domain.admin.useractivity.dto.AdminUserActivityResponseDto;
import com.sparta.quokkatravel.domain.admin.useractivity.dto.AdminUserStatusUpdateRequestDto;
import com.sparta.quokkatravel.domain.admin.useractivity.service.AdminUserActivityService;
import com.sparta.quokkatravel.domain.common.shared.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/admin/users")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
@Tag(name = "AdminUserActivity", description = "Admin 사용자 활동 관련 컨트롤러")
public class AdminUserActivityController {

    private final AdminUserActivityService adminUserActivityService;

    // 특정 사용자 활동 목록 조회
    @GetMapping("/{userId}/activities")
    @Operation(summary = "사용자 활동 조회", description = "관리자가 특정 사용자의 활동 내역을 조회하는 API")
    public ResponseEntity<?> getUserActivities(@PathVariable Long userId) {
        List<AdminUserActivityResponseDto> activities = adminUserActivityService.getUserActivities(userId);
        return ResponseEntity.ok(ApiResponse.success("사용자 활동 조회 성공", activities));
    }

    // 특정 사용자 상태 변경
    @PostMapping("/{userId}/status")
    @Operation(summary = "사용자 상태 변경", description = "관리자가 특정 사용자의 상태를 변경하는 API")
    public ResponseEntity<?> updateUserStatus(@PathVariable Long userId, @RequestBody AdminUserStatusUpdateRequestDto statusUpdateDto) {
        adminUserActivityService.updateUserStatus(userId, statusUpdateDto);
        return ResponseEntity.ok(ApiResponse.success("사용자 상태 변경 성공"));
    }
}