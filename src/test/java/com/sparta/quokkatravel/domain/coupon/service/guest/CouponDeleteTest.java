package com.sparta.quokkatravel.domain.coupon.service.guest;

import com.sparta.quokkatravel.domain.common.exception.NotFoundException;
import com.sparta.quokkatravel.domain.coupon.dto.response.CouponDeleteResponseDto;
import com.sparta.quokkatravel.domain.coupon.entity.Coupon;
import com.sparta.quokkatravel.domain.coupon.repository.CouponRepository;
import com.sparta.quokkatravel.domain.coupon.service.CouponServiceImpl;
import com.sparta.quokkatravel.domain.search.document.CouponDocument;
import com.sparta.quokkatravel.domain.search.repository.CouponSearchRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CouponDeleteTest {

    @InjectMocks
    private CouponServiceImpl couponService;

    @Mock
    private CouponRepository couponRepository;

    @Mock
    private CouponSearchRepository couponSearchRepository;

    private Coupon coupon;

    @BeforeEach
    public void setup() {
        coupon = new Coupon();
        ReflectionTestUtils.setField(coupon, "id", 1L);
        ReflectionTestUtils.setField(coupon, "name", "Test Coupon");
        ReflectionTestUtils.setField(coupon, "code", "TESTCODE");
        ReflectionTestUtils.setField(coupon, "isDeleted", false);
        ReflectionTestUtils.setField(coupon, "deletedAt", null);
    }

    @Test
    public void 쿠폰삭제_성공() {
        // given
        Long couponId = 1L;
        String email = "test@example.com";

        when(couponRepository.findById(couponId)).thenReturn(Optional.of(coupon));
        when(couponSearchRepository.findByCouponIdOrElseThrow(couponId)).thenReturn(new CouponDocument());

        // when
        CouponDeleteResponseDto responseDto = couponService.deleteCoupon(email, couponId);

        // then
        assertNotNull(responseDto);
        assertEquals(couponId, responseDto.getCouponId());
        assertEquals("Test Coupon", responseDto.getCouponName());
        assertTrue(responseDto.getIsDeleted());
        assertNotNull(responseDto.getDeletedAt());

        verify(couponRepository, times(1)).save(any(Coupon.class));
        verify(couponSearchRepository, times(1)).delete(any(CouponDocument.class));
    }

    @Test
    public void 쿠폰삭제_실패() {
        // given
        Long couponId = 1L;
        String email = "test@example.com";

        when(couponRepository.findById(couponId)).thenReturn(Optional.empty());

        // when & then
        assertThrows(NotFoundException.class, () -> couponService.deleteCoupon(email, couponId));

        verify(couponRepository, never()).save(any(Coupon.class));
        verify(couponSearchRepository, never()).delete(any(CouponDocument.class));
    }
}
