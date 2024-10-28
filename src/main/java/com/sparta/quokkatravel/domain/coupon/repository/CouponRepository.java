package com.sparta.quokkatravel.domain.coupon.repository;

import com.sparta.quokkatravel.domain.coupon.entity.Coupon;
import com.sparta.quokkatravel.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CouponRepository extends JpaRepository<Coupon, Long> {


    List<Coupon> findAllByOwner(User user);
    Optional<Coupon> findByCode(String code);

}
