package com.sparta.quokkatravel.domain.room.dto;

import lombok.Getter;

@Getter
public class RoomRequestDto {
    private String name;
    private String description;
    private Long capacity;
    private Long pricePerOverCapacity;
    private Long pricePerNight;

    public RoomRequestDto() {}

    public RoomRequestDto(String name, String description, Long capacity, Long pricePerOverCapacity, Long pricePerNight) {
        this.name = name;
        this.description = description;
        this.capacity = capacity;
        this.pricePerOverCapacity = pricePerOverCapacity;
        this.pricePerNight = pricePerNight;
    }
}
