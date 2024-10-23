package com.sparta.quokkatravel.domain.event.entity;

import com.sparta.quokkatravel.domain.coupon.entity.Coupon;
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
    private List<Coupon> coupons = new ArrayList<>();

    public Event(String name, String content) {
        this.name = name;
        this.content = content;
    }
}
