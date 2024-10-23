package com.sparta.quokkatravel.domain.room.entity;

import com.sparta.quokkatravel.domain.accommodation.entity.Accommodation;
import com.sparta.quokkatravel.domain.common.timestamped.Timestamped;
import com.sparta.quokkatravel.domain.room.dto.RoomRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
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
    private int pricePerOverCapacity;

    @Column
    private int pricePerNight;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accommodation_id")
    private Accommodation accommodation;


    public Room(RoomRequestDto roomRequestDto, Accommodation accommodation) {
        this.name = roomRequestDto.getName();
        this.description = roomRequestDto.getDescription();
        this.capacity = roomRequestDto.getCapacity();
        this.pricePerOverCapacity = roomRequestDto.getPricePerOverCapacity();
        this.pricePerNight = roomRequestDto.getPricePerNight();
        this.accommodation = accommodation;
    }

    public void update(String name, String description, int capacity, int pricePerOverCapacity, int pricePerNight) {
        this.name = name;
        this.description = description;
        this.capacity = capacity;
        this.pricePerOverCapacity = pricePerOverCapacity;
        this.pricePerNight = pricePerNight;
    }
}
