package com.sparta.quokkatravel.domain.event.service;


import com.sparta.quokkatravel.domain.event.dto.request.EventRequestDto;
import com.sparta.quokkatravel.domain.event.dto.response.EventResponseDto;

import java.util.List;

public interface EventService {

    EventResponseDto createEvent(String email, EventRequestDto eventRequestDto);
    List<EventResponseDto> getAllEvents(String email);
    EventResponseDto updateEvent(String email, Long eventId, EventRequestDto eventRequestDto);
    String deleteEvent(String email, Long eventId);
}
