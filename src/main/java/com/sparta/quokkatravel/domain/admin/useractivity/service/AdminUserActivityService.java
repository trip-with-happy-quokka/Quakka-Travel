package com.sparta.quokkatravel.domain.admin.useractivity.service;

import com.sparta.quokkatravel.domain.admin.useractivity.dto.AdminUserActivityResponseDto;
import com.sparta.quokkatravel.domain.admin.useractivity.dto.AdminUserStatusUpdateRequestDto;
import com.sparta.quokkatravel.domain.admin.useractivity.entity.UserActivity;
import com.sparta.quokkatravel.domain.admin.useractivity.repository.AdminUserActivityRepository;
import com.sparta.quokkatravel.domain.user.entity.User;
import com.sparta.quokkatravel.domain.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminUserActivityService {

    private final AdminUserActivityRepository adminUserActivityRepository;
    private final UserRepository userRepository;  // User 관련 저장소를 추가

    public AdminUserActivityService(AdminUserActivityRepository adminUserActivityRepository, UserRepository userRepository) {
        this.adminUserActivityRepository = adminUserActivityRepository;
        this.userRepository = userRepository;
    }

    // 특정 사용자 활동 조회
    public List<AdminUserActivityResponseDto> getUserActivities(Long userId) {
        List<UserActivity> activities = adminUserActivityRepository.findByUserId(userId);
        return activities.stream()
                .map(AdminUserActivityResponseDto::new)
                .collect(Collectors.toList());
    }

    // 사용자 상태 업데이트
    public void updateUserStatus(Long userId, AdminUserStatusUpdateRequestDto statusUpdateDto) {
        // 사용자 ID로 사용자 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다."));

        // 상태 업데이트 후 저장
        user.updateStatus(statusUpdateDto.getStatus());  // User 엔티티에 상태 업데이트 메서드 추가 필요
        userRepository.save(user);

        // 상태 업데이트 활동 기록 추가 (UserActivity 저장)
        UserActivity activity = new UserActivity(user, "STATUS_CHANGE", LocalDateTime.now(), "관리자가 상태를 변경하였습니다.");
        adminUserActivityRepository.save(activity);
    }
}