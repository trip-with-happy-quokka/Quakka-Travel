package com.sparta.quokkatravel.domain.admin.useractivity.service;

import com.sparta.quokkatravel.domain.admin.useractivity.dto.AdminUserActivityResponseDto;
import com.sparta.quokkatravel.domain.admin.useractivity.dto.AdminUserStatusUpdateRequestDto;
import com.sparta.quokkatravel.domain.admin.useractivity.entity.UserActivity;
import com.sparta.quokkatravel.domain.admin.useractivity.repository.AdminUserActivityRepository;
import com.sparta.quokkatravel.domain.user.entity.User;
import com.sparta.quokkatravel.domain.user.entity.UserRole;
import com.sparta.quokkatravel.domain.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminUserActivityService {

    private final AdminUserActivityRepository adminUserActivityRepository;
    private final UserRepository userRepository;

    public AdminUserActivityService(AdminUserActivityRepository adminUserActivityRepository, UserRepository userRepository) {
        this.adminUserActivityRepository = adminUserActivityRepository;
        this.userRepository = userRepository;
    }

    // 특정 사용자 활동 조회 (email 기반)
    public List<AdminUserActivityResponseDto> getUserActivities(String email, UserRole role) {
        // userRole 확인 후 ADMIN이 아닌 경우 접근 제한
        if (role != UserRole.ADMIN) {
            throw new AccessDeniedException("관리자 권한이 없습니다.");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        // INACTIVE 또는 SUSPENDED 상태 접근 제한
        if ("INACTIVE".equals(user.getStatus()) || "SUSPENDED".equals(user.getStatus())) {
            throw new AccessDeniedException("비활성화되었거나 정지된 계정으로 활동을 조회할 수 없습니다.");
        }

        return adminUserActivityRepository.findByUserId(user.getId()).stream()
                .map(AdminUserActivityResponseDto::new)
                .collect(Collectors.toList());
    }

    // 사용자 상태 업데이트 (email 기반)
    public void updateUserStatus(String email, AdminUserStatusUpdateRequestDto statusUpdateDto, UserRole role) {
        // userRole 확인 후 ADMIN이 아닌 경우 접근 제한
        if (role != UserRole.ADMIN) {
            throw new AccessDeniedException("관리자 권한이 없습니다.");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        user.updateStatus(statusUpdateDto.getStatus());
        userRepository.save(user);

        UserActivity activity = new UserActivity(user, "STATUS_CHANGE", LocalDateTime.now(), "관리자가 상태를 변경하였습니다.");
        adminUserActivityRepository.save(activity);
    }
}