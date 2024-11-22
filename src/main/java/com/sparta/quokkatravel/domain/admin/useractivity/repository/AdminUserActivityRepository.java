package com.sparta.quokkatravel.domain.admin.useractivity.repository;

import com.sparta.quokkatravel.domain.admin.useractivity.entity.UserActivity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminUserActivityRepository extends JpaRepository<UserActivity, Long> {
    List<UserActivity> findByUserId(Long userId);
}