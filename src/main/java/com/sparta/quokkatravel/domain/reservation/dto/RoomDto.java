package com.sparta.quokkatravel.domain.reservation.dto;

import com.sparta.quokkatravel.domain.room.entity.Room;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RoomDto {
    private Long id;
    private String name;

    public RoomDto(Room room) {
        this.id = room.getId();
        this.name = room.getName();
    }
}
