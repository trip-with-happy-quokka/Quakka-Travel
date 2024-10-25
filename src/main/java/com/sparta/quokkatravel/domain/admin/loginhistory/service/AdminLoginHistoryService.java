package com.sparta.quokkatravel.domain.admin.loginhistory.service;

import com.sparta.quokkatravel.domain.admin.loginhistory.dto.AdminLoginHistoryResponseDto;
import com.sparta.quokkatravel.domain.admin.loginhistory.entity.LoginHistory;
import com.sparta.quokkatravel.domain.admin.loginhistory.repository.LoginHistoryRepository;
import com.sparta.quokkatravel.domain.admin.reservation.service.AdminReservationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminLoginHistoryService {

    private final LoginHistoryRepository loginHistoryRepository;

    public AdminLoginHistoryService(LoginHistoryRepository loginHistoryRepository) {
        this.loginHistoryRepository = loginHistoryRepository;
    }

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
}
