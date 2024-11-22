package com.sparta.quokkatravel.domain.admin.coupon.service;

import com.sparta.quokkatravel.domain.accommodation.entity.Accommodation;
import com.sparta.quokkatravel.domain.accommodation.repository.AccommodationRepository;
import com.sparta.quokkatravel.domain.admin.coupon.dto.CouponToUserReq;
import com.sparta.quokkatravel.domain.admin.coupon.dto.CouponToUserRes;
import com.sparta.quokkatravel.domain.admin.coupon.repository.AdminCouponRepository;
import com.sparta.quokkatravel.domain.common.exception.NotFoundException;
import com.sparta.quokkatravel.domain.common.rabbitmq.dto.MessageRes;
import com.sparta.quokkatravel.domain.common.rabbitmq.util.RabbitMqProducerUtil;
import com.sparta.quokkatravel.domain.coupon.entity.Coupon;
import com.sparta.quokkatravel.domain.coupon.entity.CouponType;
import com.sparta.quokkatravel.domain.coupon.repository.CouponRepository;
import com.sparta.quokkatravel.domain.event.entity.Event;
import com.sparta.quokkatravel.domain.event.repository.EventRepository;
import com.sparta.quokkatravel.domain.user.entity.User;
import com.sparta.quokkatravel.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CouponProducerService {

    private final UserRepository userRepository;
    private final CouponRepository couponRepository;
    private final AccommodationRepository accommodationRepository;
    private final EventRepository eventRepository;
    private final AdminCouponRepository adminCouponRepository;
    private final RabbitMqProducerUtil rabbitMqProducerUtil;

    /**
     * [2] Direct Exchange Binding
     * QueueName : "coupon-issue-queue"
     * exchangeName : "coupon-issue-exchange"
     * routingKey : "coupon.key"
     */
    @Transactional
    public CouponToUserRes createCouponToUser(String email, CouponToUserReq couponMessageReq) {

        User createdBy = userRepository.findByEmailOrElseThrow(email);
        User owner = userRepository.findById(couponMessageReq.getUserId())
                .orElseThrow(()-> new NotFoundException("User Not Found"));

        // 중복되는 코드가 나올 확률이 현저히 적지만 대용량 처리에서의 경우를 대비
        String couponCode;
        do {
            couponCode = UUID.randomUUID().toString().replace("-", "").substring(0, 12);
        } while (couponRepository.findByCode(couponCode).isPresent());

        MessageRes messageRes = new MessageRes(couponMessageReq.getCouponName(), couponMessageReq.getCouponContent());

        // RabbitMQ - Direct Exchange Binding
        rabbitMqProducerUtil.sendMessageToQueue("coupon-issue-exchange", "coupon.key", messageRes);

        Coupon coupon = new Coupon(
                couponMessageReq.getCouponName(),
                couponMessageReq.getCouponContent(),
                couponMessageReq.getCouponType().toString(),
                couponCode,
                couponMessageReq.getDiscountRate(),
                couponMessageReq.getDiscountAmount(),
                couponMessageReq.getValidFrom(),
                couponMessageReq.getValidUntil(),
                createdBy,
                owner
        );

        // Coupon Type 유효성 검사 및 조회
        if (Objects.equals(CouponType.ACCOMMODATION, couponMessageReq.getCouponType())) {
            // 숙소 조회
            Accommodation accommodation = accommodationRepository.findById(couponMessageReq.getCouponTargetId())
                    .orElseThrow(() -> new NotFoundException("Accommodation is not found"));
            coupon.addAccommodation(accommodation);
        }
        if (Objects.equals(CouponType.EVENT, couponMessageReq.getCouponType())) {
            // 행사 조회
            Event event = eventRepository.findById(couponMessageReq.getCouponTargetId())
                    .orElseThrow(() -> new NotFoundException("Event is not found"));
            coupon.addEvent(event);
        }

        Coupon savedCoupon = adminCouponRepository.save(coupon);

        return new CouponToUserRes(savedCoupon.getName(), savedCoupon.getContent());
    }
}
