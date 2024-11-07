package com.sparta.quokkatravel.domain.coupon.service.guest;

import com.sparta.quokkatravel.domain.common.jwt.CustomUserDetails;
import com.sparta.quokkatravel.domain.coupon.dto.response.CouponDeleteResponseDto;
import com.sparta.quokkatravel.domain.coupon.entity.Coupon;
import com.sparta.quokkatravel.domain.coupon.repository.CouponRepository;
import com.sparta.quokkatravel.domain.coupon.service.CouponService;
import com.sparta.quokkatravel.domain.coupon.service.CouponServiceImpl;
import com.sparta.quokkatravel.domain.search.repository.CouponSearchRepository;
import com.sparta.quokkatravel.domain.user.entity.User;
import com.sparta.quokkatravel.domain.user.entity.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CouponDeleteTest {

    @Mock
    private CouponRepository couponRepository;

    @InjectMocks
    private CouponServiceImpl couponService;

    CustomUserDetails userDetails;
    User userGuest1;
    Coupon coupon;

    @BeforeEach
    void setUp() {
        userDetails = new CustomUserDetails(new User(1L, "guest@example.com", "password", "guest1", UserRole.GUEST));
        userGuest1 = new User(1L, "guest@example.com", "password", "guest1", UserRole.GUEST);
        coupon = new Coupon("name", "content", "EVENT",
                100, "code", 10, 0,
                LocalDate.of(2024, 12, 10), LocalDate.of(2024, 12, 31), userGuest1);
    }

    @Test
    public void 쿠폰_삭제_성공() {

        // given
        when(couponRepository.findById(coupon.getId())).thenReturn(Optional.ofNullable(coupon));

        // when
        CouponDeleteResponseDto result = couponService.deleteCoupon("test@example.com", coupon.getId());

        // Then
        assertTrue(coupon.getIsDeleted());
        assertEquals(coupon.getId(), result.getCouponId());
        assertEquals(coupon.getName(), result.getCouponName());
        assertEquals(coupon.getCode(), result.getCouponCode());
        verify(couponRepository, times(1)).save(coupon);
    }
}
