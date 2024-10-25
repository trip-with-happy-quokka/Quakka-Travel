package com.sparta.quokkatravel.domain.coupon.repository;

import com.sparta.quokkatravel.domain.coupon.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

    Coupon findByCode(String code);
}
