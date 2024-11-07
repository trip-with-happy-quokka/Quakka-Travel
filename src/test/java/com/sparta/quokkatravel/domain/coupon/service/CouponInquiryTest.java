package com.sparta.quokkatravel.domain.coupon.service;

import com.sparta.quokkatravel.domain.common.jwt.CustomUserDetails;
import com.sparta.quokkatravel.domain.coupon.dto.response.CouponResponseDto;
import com.sparta.quokkatravel.domain.coupon.entity.Coupon;
import com.sparta.quokkatravel.domain.coupon.repository.CouponRepository;
import com.sparta.quokkatravel.domain.user.entity.User;
import com.sparta.quokkatravel.domain.user.entity.UserRole;
import com.sparta.quokkatravel.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CouponInquiryTest {

    // Mock 생성
    @Mock
    private UserRepository userRepository;
    @Mock
    private CouponRepository couponRepository;

    // Mock 이 주입된 서비스 객체 생성
    @InjectMocks
    private CouponServiceImpl couponService;

    private CustomUserDetails userDetails;
    private User userGuest1;
    private Coupon coupon;
    private List<Coupon> couponList;

    @BeforeEach
    void setUp() {
        userDetails = new CustomUserDetails(new User(1L, "guest@example.com", "password", "guest1", UserRole.GUEST));
        userGuest1 = new User(1L, "guest@example.com", "password", "guest1", UserRole.GUEST);
        coupon = new Coupon("name", "content", "EVENT",
                100, "code", 10, 0,
                LocalDate.of(2024, 12, 10), LocalDate.of(2024, 12, 31), userGuest1);
        couponList = new ArrayList<>(List.of(coupon));
    }

    @Test
    public void 내쿠폰_전체조회_성공_테스트() {

        // given
        when(userRepository.findByEmailOrElseThrow(userDetails.getEmail())).thenReturn(userGuest1);
        when(couponRepository.findAllByOwner(userGuest1)).thenReturn(couponList);

        // when
        List<CouponResponseDto> result = couponService.getAllMyCoupons(userDetails.getEmail(), userGuest1.getId());

        // then
        assertEquals(1, result.size());
        assertEquals("name", result.get(0).getCouponName());
        verify(userRepository, times(1)).findByEmailOrElseThrow(userDetails.getEmail());
        verify(couponRepository, times(1)).findAllByOwner(userGuest1);

    }
}
