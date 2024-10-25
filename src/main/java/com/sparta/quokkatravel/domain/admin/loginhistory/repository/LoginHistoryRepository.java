package com.sparta.quokkatravel.domain.admin.loginhistory.repository;

import com.sparta.quokkatravel.domain.admin.loginhistory.entity.LoginHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoginHistoryRepository extends JpaRepository<LoginHistory, Long> {

    // 특정 사용자 ID로 로그인 기록 조회
    List<LoginHistory> findByUserId(Long userId);

    //모든 로그인 기록 조회
    List<LoginHistory> findAllByOrderByLoginTimeDesc();
}
