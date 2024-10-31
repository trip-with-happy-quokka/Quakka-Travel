package com.sparta.quokkatravel.domain.search.dto;

import com.sparta.quokkatravel.domain.coupon.entity.CouponStatus;
import com.sparta.quokkatravel.domain.coupon.entity.CouponType;
import com.sparta.quokkatravel.domain.search.document.CouponDocument;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class SearchCouponRes {

    private Long id;
    private String name;
    private String content;
    private CouponType couponType;
    private Integer volume;
    private String code;
    private CouponStatus couponStatus;
    private LocalDate validFrom;
    private LocalDate validUntil;
    private String accommodation;
    private String event;
    private String createdBy;

    public SearchCouponRes(CouponDocument couponDocument) {
        this.id = couponDocument.getId();
        this.name = couponDocument.getName();
        this.content = couponDocument.getContent();
        this.couponType = couponDocument.getCouponType();
        this.volume = couponDocument.getVolume();
        this.code = couponDocument.getCode();
        this.couponStatus = couponDocument.getCouponStatus();
        this.validFrom = couponDocument.getValidFrom();
        this.validUntil = couponDocument.getValidUntil();
        this.accommodation = couponDocument.getAccommodation();
        this.event = couponDocument.getEvent();
        this.createdBy = couponDocument.getCreatedBy();
    }
}
