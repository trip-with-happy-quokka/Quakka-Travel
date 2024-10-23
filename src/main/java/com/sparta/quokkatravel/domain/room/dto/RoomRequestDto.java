package com.sparta.quokkatravel.domain.room.dto;

import lombok.Getter;

@Getter
public class RoomRequestDto {
    private String name;
    private String description;
    private int capacity;
    private int pricePerOverCapacity;
    private int pricePerNight;

    public RoomRequestDto() {}

    public RoomRequestDto(String name, String description, int capacity, int pricePerOverCapacity, int pricePerNight) {
        this.name = name;
        this.description = description;
        this.capacity = capacity;
        this.pricePerOverCapacity = pricePerOverCapacity;
        this.pricePerNight = pricePerNight;
    }
}
