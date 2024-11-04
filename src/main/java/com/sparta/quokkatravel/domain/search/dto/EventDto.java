package com.sparta.quokkatravel.domain.search.dto;

import com.sparta.quokkatravel.domain.accommodation.entity.Accommodation;
import com.sparta.quokkatravel.domain.event.entity.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EventDto {

    private String name;

    public EventDto(Event event) {
        this.name = event.getName();
    }
}
