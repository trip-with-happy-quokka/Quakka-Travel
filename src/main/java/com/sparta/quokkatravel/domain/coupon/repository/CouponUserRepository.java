package com.sparta.quokkatravel.domain.coupon.repository;

import com.sparta.quokkatravel.domain.coupon.entity.CouponUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponUserRepository extends JpaRepository<CouponUser, Long> {
}
