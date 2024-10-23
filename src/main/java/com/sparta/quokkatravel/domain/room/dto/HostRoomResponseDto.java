package com.sparta.quokkatravel.domain.room.dto;

import com.sparta.quokkatravel.domain.room.entity.Room;
import lombok.Getter;

@Getter
public class HostRoomResponseDto {

    private Long id;
    private String name;
    private String description;
    private int capacity;
    private int pricePerOverCapacity;
    private int pricePerNight;
    private String accommodationName;

    public HostRoomResponseDto() {}

    public HostRoomResponseDto(Room room) {
        this.id = room.getId();
        this.name = room.getName();
        this.description = room.getDescription();
        this.capacity = room.getCapacity();
        this.pricePerOverCapacity = room.getPricePerOverCapacity();
        this.pricePerNight = room.getPricePerNight();
        this.accommodationName = room.getAccommodation().getName();
    }
}
