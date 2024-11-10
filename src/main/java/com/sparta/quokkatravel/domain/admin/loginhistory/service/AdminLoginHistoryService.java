package com.sparta.quokkatravel.domain.admin.loginhistory.service;

import com.sparta.quokkatravel.domain.admin.loginhistory.dto.AdminLoginHistoryResponseDto;
import com.sparta.quokkatravel.domain.admin.loginhistory.entity.LoginHistory;
import com.sparta.quokkatravel.domain.admin.loginhistory.repository.LoginHistoryRepository;
import com.sparta.quokkatravel.domain.user.entity.User;
import com.sparta.quokkatravel.domain.user.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminLoginHistoryService {

    private final LoginHistoryRepository loginHistoryRepository;
    private final UserRepository userRepository; // 추가

    @PersistenceContext
    private EntityManager entityManager; // EntityManager 주입

    // 모든 로그인 기록 조회
    public List<AdminLoginHistoryResponseDto> getAllLoginHistory() {
        List<LoginHistory> loginHistories = loginHistoryRepository.findAllByOrderByLoginTimeDesc();
        return  loginHistories.stream()
                .map(AdminLoginHistoryResponseDto::new)
                .collect(Collectors.toList());
    }

    // 특정 사용자의 로그인 기록 조회
    public List<AdminLoginHistoryResponseDto> getLoginHistoryByUserId(Long userId) {
        List<LoginHistory> loginHistories = loginHistoryRepository.findByUserId(userId);
        return loginHistories.stream()
                .map(AdminLoginHistoryResponseDto::new)
                .collect(Collectors.toList());
    }

    // 새로운 로그인 기록 저장 메서드
    @Transactional
    public void saveLoginHistory(Long userId, String ipAddress) {
        User managedUser = userRepository.findById(userId) // userId로 User 조회
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 사용자를 찾을 수 없습니다: " + userId));
        LoginHistory loginHistory = new LoginHistory(managedUser, ipAddress, LocalDateTime.now());
        loginHistoryRepository.save(loginHistory); // 조회된 User를 사용하여 로그인 기록 저장
    }
}
