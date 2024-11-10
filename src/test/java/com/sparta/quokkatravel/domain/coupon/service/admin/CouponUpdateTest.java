package com.sparta.quokkatravel.domain.coupon.service.admin;

import com.sparta.quokkatravel.domain.accommodation.entity.Accommodation;
import com.sparta.quokkatravel.domain.admin.coupon.dto.AdminCouponRequestDto;
import com.sparta.quokkatravel.domain.admin.coupon.dto.AdminCouponResponseDto;
import com.sparta.quokkatravel.domain.admin.coupon.repository.AdminCouponRepository;
import com.sparta.quokkatravel.domain.admin.coupon.service.AdminCouponService;
import com.sparta.quokkatravel.domain.coupon.entity.Coupon;
import com.sparta.quokkatravel.domain.coupon.entity.CouponType;
import com.sparta.quokkatravel.domain.event.entity.Event;
import com.sparta.quokkatravel.domain.user.entity.User;
import com.sparta.quokkatravel.domain.user.entity.UserRole;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CouponUpdateTest {

    @InjectMocks
    private AdminCouponService couponService;

    @Mock
    private AdminCouponRepository adminCouponRepository;

    private Coupon coupon;
    private AdminCouponRequestDto couponRequestDto;
    private User userGuest1;

    @BeforeEach
    public void setup() {
        // 초기 쿠폰 설정
        userGuest1 = new User(1L, "guest@example.com", "password", "guest1", UserRole.GUEST);

        Event event = new Event();
        ReflectionTestUtils.setField(event, "id", 1L);

        coupon = new Coupon("name", "content", "EVENT",
                100, "code", 10, 0,
                LocalDate.of(2024, 12, 10), LocalDate.of(2024, 12, 31), userGuest1);

        // 쿠폰 수정 요청 DTO 설정
        couponRequestDto = new AdminCouponRequestDto(
                "UpdatedCode", "Updated Coupon", "Updated Content", CouponType.EVENT, 1L,
                20, 10, 0, LocalDate.now(), LocalDate.now().plusDays(15)
        );
    }

    @Test
    public void 쿠폰_수정_성공() {
        // given
        Long couponId = 1L;
        when(adminCouponRepository.findById(couponId)).thenReturn(Optional.of(coupon));
        when(adminCouponRepository.save(any(Coupon.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // when
        AdminCouponResponseDto responseDto = couponService.updateCoupon(couponId, couponRequestDto);

        // then
        assertNotNull(responseDto);
        assertEquals("Updated Coupon", responseDto.getCouponName());
        assertEquals("UpdatedCode", responseDto.getCouponCode());
        verify(adminCouponRepository, times(1)).save(coupon);
    }

    @Test
    public void 쿠폰_수정_실패_쿠폰이_없음() {
        // given
        Long invalidCouponId = 999L;
        when(adminCouponRepository.findById(invalidCouponId)).thenReturn(Optional.empty());

        // when & then
        assertThrows(EntityNotFoundException.class, () -> couponService.updateCoupon(invalidCouponId, couponRequestDto));
        verify(adminCouponRepository, never()).save(any(Coupon.class));
    }
}
