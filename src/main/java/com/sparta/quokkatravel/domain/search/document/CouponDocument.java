package com.sparta.quokkatravel.domain.search.document;

import com.querydsl.core.annotations.QueryEntity;
import com.sparta.quokkatravel.domain.accommodation.entity.Accommodation;
import com.sparta.quokkatravel.domain.coupon.entity.Coupon;
import com.sparta.quokkatravel.domain.coupon.entity.CouponStatus;
import com.sparta.quokkatravel.domain.coupon.entity.CouponType;
import com.sparta.quokkatravel.domain.event.entity.Event;
import com.sparta.quokkatravel.domain.search.dto.AccommodationDto;
import com.sparta.quokkatravel.domain.search.dto.EventDto;
import com.sparta.quokkatravel.domain.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@QueryEntity
@Document(indexName = "coupon")
public class CouponDocument {

    @Id
    private String id;

    @Field(type = FieldType.Long)
    private Long couponId;

    @Field(type = FieldType.Text, analyzer = "custom_analyzer", searchAnalyzer = "whitespace")
    private String name;

    @Field(type = FieldType.Text)
    private String content;

    @Field(type = FieldType.Keyword)
    private CouponType couponType;

    @Field(type = FieldType.Integer)
    private Integer volume;

    @Field(type = FieldType.Keyword)
    private String code;

    @Field(type = FieldType.Keyword)
    private CouponStatus couponStatus;

    @Field(type = FieldType.Date)
    private LocalDate validFrom;

    @Field(type = FieldType.Date)
    private LocalDate validUntil;

    @Field(type = FieldType.Text)
    private String accommodation;

    @Field(type = FieldType.Text)
    private String event;

    @Field(type = FieldType.Text)
    private String createdBy;

    public CouponDocument(Coupon coupon) {
        this.couponId = coupon.getId();
        this.name = coupon.getName();
        this.content = coupon.getContent();
        this.couponType = coupon.getCouponType();
        this.volume = coupon.getVolume();
        this.code = coupon.getCode();
        this.couponStatus = coupon.getCouponStatus();
        this.validFrom = coupon.getValidFrom();
        this.validUntil = coupon.getValidUntil();
        this.accommodation = new AccommodationDto(coupon.getAccommodation()).getName();
        this.event = new EventDto(coupon.getEvent()).getName();
        this.createdBy = coupon.getCreatedBy().getEmail();
    }

}
