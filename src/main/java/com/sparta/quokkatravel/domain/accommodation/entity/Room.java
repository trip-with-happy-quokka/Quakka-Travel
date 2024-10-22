package com.sparta.quokkatravel.domain.accommodation.entity;

import com.sparta.quokkatravel.domain.common.timestamped.Timestamped;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "room")
public class Room extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private int capacity;

    @Column
    private int additionalPricePerOverCapacity;

    @Column
    private int pricePerNight;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accommodation_id")
    private Accommodation accommodation;


    public Room() {}

    public Room(String name, String description, int capacity, int additionalPricePerOverCapacity, int pricePerNight) {
        this.name = name;
        this.description = description;
        this.capacity = capacity;
        this.additionalPricePerOverCapacity = additionalPricePerOverCapacity;
        this.pricePerNight = pricePerNight;
    }

    public void update(String name, String description, int capacity, int additionalPricePerOverCapacity, int pricePerNight) {
        this.name = name;
        this.description = description;
        this.capacity = capacity;
        this.additionalPricePerOverCapacity = additionalPricePerOverCapacity;
        this.pricePerNight = pricePerNight;
    }
}
