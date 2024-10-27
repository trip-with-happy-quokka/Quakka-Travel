package com.sparta.quokkatravel.domain.coupon.service;


import com.sparta.quokkatravel.domain.common.dto.CustomUserDetails;
import com.sparta.quokkatravel.domain.coupon.dto.request.CouponRequestDto;
import com.sparta.quokkatravel.domain.coupon.dto.response.CouponResponseDto;
import com.sparta.quokkatravel.domain.coupon.entity.Coupon;
import com.sparta.quokkatravel.domain.coupon.repository.CouponRepository;
import com.sparta.quokkatravel.domain.event.entity.Event;
import com.sparta.quokkatravel.domain.event.repository.EventRepository;
import com.sparta.quokkatravel.domain.user.entity.User;
import com.sparta.quokkatravel.domain.user.entity.UserRole;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CouponServiceTest {

    @InjectMocks
    private CouponServiceImpl couponService;

    @Mock
    private EventRepository eventRepository;

    @Mock
    private CouponRepository couponRepository;

    @Test
    public void 쿠폰_발급_정상동작 () {
        // Given
        long eventId = 1L;
        Event event = new Event("eventTitle", "eventContent");
        ReflectionTestUtils.setField(event, "eventId", eventId);
        Coupon coupon = new Coupon(
                "name", "content", "EVENT",
                "1111-1111-1111", 10, 0,
                LocalDate.of(2024, 11, 25), // 2024-11-25
                LocalDate.of(2024, 12, 25), event);

        given(couponRepository.findById(anyLong())).willReturn(Optional.of(coupon));

        CouponRequestDto couponRequestDto = new CouponRequestDto(
                "couponName",
                "couponContent",
                "EVENT",
                10,
                0,
                LocalDate.of(2024, 11, 25),  // 시작일
                LocalDate.of(2024, 12, 25)   // 종료일
        );


        User user = new User("test1@test.com", "1234", "test", "010-0000-0000", UserRole.ADMIN);
        CustomUserDetails customUserDetails = new CustomUserDetails(user);

        // When: 쿠폰 생성 메서드 호출
        CouponResponseDto couponResponseDto = couponService.createEventCoupon(customUserDetails, eventId, couponRequestDto);

        // Then: 쿠폰 코드가 올바르게 생성되었는지 확인
        assertNotNull(couponResponseDto);
        assertEquals(0,couponResponseDto.getDiscountAmount());


    }
}

