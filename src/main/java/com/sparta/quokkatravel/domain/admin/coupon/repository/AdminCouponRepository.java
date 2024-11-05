package com.sparta.quokkatravel.domain.admin.coupon.repository;

import com.sparta.quokkatravel.domain.coupon.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminCouponRepository extends JpaRepository<Coupon, Long> {
    // 쿠폰 코드 중복 여부 확인 메서드
    boolean existsByCode(String Code);
}