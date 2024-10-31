package com.sparta.quokkatravel.domain.coupon.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class CouponRequestDto {

    @NotBlank(message = "쿠폰 명을 입력해주세요.")
    private String couponName;

    @NotBlank(message = "쿠폰 내용을 입력해주세요.")
    private String couponContent;

    @NotBlank(message = "쿠폰 타입을 입력해주세요. ( Accommodation 또는 Event )")
    private String couponType;

    @Min(0)
    @NotBlank(message = "발행할 쿠폰 수량을 입력해주세요.")
    private Integer volume;

    @Min(value = 0, message = "할인율은 0이상 100이하로 입력해주세요.")
    @Max(value = 100, message = "할인율은 0이상 100이하로 입력해주세요.")
    private int discountRate;

    @Min(value = 0, message = "할인 금액은 0 이상의 값으로 입력해주세요.")
    private int discountAmount;

    @NotNull(message = "쿠폰의 사용가능 시작일을 설정해주세요.")
    private LocalDate validFrom;

    @NotNull(message = "쿠폰의 사용가능 마감일을 설정해주세요.")
    private LocalDate validUntil;
}
