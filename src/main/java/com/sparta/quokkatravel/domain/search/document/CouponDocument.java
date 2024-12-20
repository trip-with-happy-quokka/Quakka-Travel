package com.sparta.quokkatravel.domain.search.document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sparta.quokkatravel.domain.coupon.entity.Coupon;
import com.sparta.quokkatravel.domain.coupon.entity.CouponStatus;
import com.sparta.quokkatravel.domain.coupon.entity.CouponType;
import com.sparta.quokkatravel.domain.search.dto.AccommodationDto;
import com.sparta.quokkatravel.domain.search.dto.EventDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.UUID;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@Document(indexName = "coupons")
public class CouponDocument {

    @Id
    private String id = UUID.randomUUID().toString();

    @Field(type = FieldType.Long)
    private Long couponId;

    @Field(type = FieldType.Text)
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
    private String validFrom;

    @Field(type = FieldType.Date)
    private String validUntil;

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
        this.validFrom = coupon.getValidFrom().toString();
        this.validUntil = coupon.getValidUntil().toString();
        this.accommodation = (coupon.getAccommodation() != null) ? new AccommodationDto(coupon.getAccommodation()).getName() : null;
        this.event = (coupon.getEvent() != null) ? new EventDto(coupon.getEvent()).getName() : null;
        this.createdBy = coupon.getCreatedBy().getEmail();
    }

    public void update(Coupon coupon) {
        this.name = coupon.getName();
        this.content = coupon.getContent();
        this.couponType = coupon.getCouponType();
        this.volume = coupon.getVolume();
        this.code = coupon.getCode();
        this.couponStatus = coupon.getCouponStatus();
        this.validFrom = coupon.getValidFrom().toString();
        this.validUntil = coupon.getValidUntil().toString();
        this.accommodation =(coupon.getAccommodation() != null) ? new AccommodationDto(coupon.getAccommodation()).getName() : null;
        this.event = (coupon.getEvent() != null) ? new EventDto(coupon.getEvent()).getName() : null;
        this.createdBy = coupon.getCreatedBy().getEmail();
    }
}
