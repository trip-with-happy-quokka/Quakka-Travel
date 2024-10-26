package com.sparta.quokkatravel.domain.coupon.repository;

import com.sparta.quokkatravel.domain.coupon.entity.Coupon;
import com.sparta.quokkatravel.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

    Coupon findByCode(String code);

    List<Coupon> findAllByOwner(User user);
}
