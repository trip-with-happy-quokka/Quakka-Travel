package com.sparta.quokkatravel.domain.event.dto.response;

import lombok.Getter;

@Getter
public class EventResponseDto {

    private final Long eventId;
    private final String eventName;
    private final String eventContent;


    public EventResponseDto(Long eventId, String eventName, String eventContent) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventContent = eventContent;
    }
}
