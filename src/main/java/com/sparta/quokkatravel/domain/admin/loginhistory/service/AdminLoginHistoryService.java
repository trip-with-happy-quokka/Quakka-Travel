package com.sparta.quokkatravel.domain.admin.loginhistory.service;

import com.sparta.quokkatravel.domain.admin.loginhistory.dto.AdminLoginHistoryResponseDto;
import com.sparta.quokkatravel.domain.admin.loginhistory.entity.LoginHistory;
import com.sparta.quokkatravel.domain.admin.loginhistory.repository.LoginHistoryRepository;
import com.sparta.quokkatravel.domain.user.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminLoginHistoryService {

    private final LoginHistoryRepository loginHistoryRepository;

    public AdminLoginHistoryService(LoginHistoryRepository loginHistoryRepository) {
        this.loginHistoryRepository = loginHistoryRepository;
    }

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
    public void saveLoginHistory(User user, String ipAddress) {
        User managedUser = entityManager.find(User.class, user.getId()); // User를 영속성 컨텍스트에서 조회
        if (managedUser != null) { // 조회가 성공했을 때만 처리
            LoginHistory loginHistory = new LoginHistory(managedUser, ipAddress, LocalDateTime.now());
            loginHistoryRepository.save(loginHistory);
        }
    }
}
