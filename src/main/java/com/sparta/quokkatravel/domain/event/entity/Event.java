package com.sparta.quokkatravel.domain.event.entity;

import com.sparta.quokkatravel.domain.coupon.entity.EventCoupon;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "event_title", nullable = false)
    private String name;

    @Column(name = "event_content", nullable = false)
    private String content;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EventCoupon> coupons = new ArrayList<>();
}
