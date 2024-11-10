package com.sparta.quokkatravel.domain.coupon.service.admin;

import com.sparta.quokkatravel.domain.admin.coupon.dto.AdminCouponRequestDto;
import com.sparta.quokkatravel.domain.admin.coupon.dto.AdminCouponResponseDto;
import com.sparta.quokkatravel.domain.admin.coupon.repository.AdminCouponRepository;
import com.sparta.quokkatravel.domain.admin.coupon.service.AdminCouponService;
import com.sparta.quokkatravel.domain.common.jwt.CustomUserDetails;
import com.sparta.quokkatravel.domain.coupon.entity.Coupon;
import com.sparta.quokkatravel.domain.coupon.entity.CouponType;
import com.sparta.quokkatravel.domain.event.entity.Event;
import com.sparta.quokkatravel.domain.event.repository.EventRepository;
import com.sparta.quokkatravel.domain.user.entity.User;
import com.sparta.quokkatravel.domain.user.entity.UserRole;
import com.sparta.quokkatravel.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class CouponCreateTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AdminCouponRepository adminCouponRepository;

    @Mock
    private EventRepository eventRepository;

    @Mock
    private RedissonClient redissonClient;

    @InjectMocks
    private AdminCouponService adminCouponService;

    CustomUserDetails userDetails;
    User userGuest1;
    Coupon coupon;
    AdminCouponRequestDto couponRequestDto;
    Event event;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        userDetails = new CustomUserDetails(new User(1L, "guest@example.com", "password", "guest1", UserRole.GUEST));
        userGuest1 = new User(1L, "guest@example.com", "password", "guest1", UserRole.GUEST);
        coupon = new Coupon("couponName", "content", "EVENT",
                100, "couponCode", 10, 0,
                LocalDate.of(2024, 12, 10), LocalDate.of(2024, 12, 31), userGuest1);
        event = new Event("name", "content");
        ReflectionTestUtils.setField(event, "id", 1L);
    }


    @Test
    public void 쿠폰_등록_성공() {

        // Given
        couponRequestDto = new AdminCouponRequestDto(
                "couponCode", "couponName", "couponContent", CouponType.EVENT, 1L,
                10, 10, 0,
                LocalDate.of(2024, 12, 10), LocalDate.of(2024, 12, 31)

        );

        String couponKey = couponRequestDto.getCouponCode();

        when(userRepository.findByEmailOrElseThrow(userDetails.getEmail())).thenReturn(userGuest1);
        when(eventRepository.findById(anyLong())).thenReturn(Optional.of(event)); // 이벤트 객체를 반환하도록 설정
        when(adminCouponRepository.save(any(Coupon.class))).thenReturn(coupon); // Coupon 저장 동작 모의 설정
        RBucket<Object> mockBucket = mock(RBucket.class);
        when(redissonClient.getBucket(couponKey)).thenReturn(mockBucket); // getBucket 호출 시 mockBucket 반환 설정


        // When
        AdminCouponResponseDto result = adminCouponService.createCoupon(userDetails.getEmail(), couponRequestDto);

        // Then
        assertNotNull(result);
        assertEquals("couponCode", result.getCouponCode()); // 쿠폰 코드 확인
        assertEquals("couponName", result.getCouponName()); // 쿠폰 이름 확인
        assertEquals(CouponType.EVENT, result.getCouponType()); // 쿠폰 타입 확인
        assertEquals(10, result.getDiscountRate()); // 할인율 확인
        assertEquals(0, result.getDiscountAmount()); // 할인 금액 확인
        assertEquals(LocalDate.of(2024, 12, 10), result.getValidFrom()); // 유효 시작일 확인
        assertEquals(LocalDate.of(2024, 12, 31), result.getValidUntil()); // 유효 마감일 확인
        verify(redissonClient, times(1)).getBucket(couponKey); // getBucket 메서드 호출 확인
        verify(mockBucket, times(1)).set(couponRequestDto.getVolume()); // set 메서드에 volume 값이 전달되었는지 확인
    }
}