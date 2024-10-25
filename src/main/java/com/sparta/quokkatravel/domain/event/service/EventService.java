package com.sparta.quokkatravel.domain.event.service;

import com.sparta.quokkatravel.domain.event.dto.request.EventRequestDto;
import com.sparta.quokkatravel.domain.event.dto.response.EventResponseDto;
import com.sparta.quokkatravel.domain.event.entity.Event;
import com.sparta.quokkatravel.domain.event.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventService {

    private final EventRepository eventRepository;

    @Transactional
    public EventResponseDto createEvent(EventRequestDto eventRequestDto) {

        // EventRequestDto 데이터를 Event 에 전달
        Event newEvent = new Event(
                eventRequestDto.getEventName(),
                eventRequestDto.getEventContent()
        );

        // EventRepository 에 Event 데이터를 저장 (save)
        Event savedEvent = eventRepository.save(newEvent);

        // Event 를 EventResponseDto 로 반환
        return new EventResponseDto(
                savedEvent.getId(),
                savedEvent.getName(),
                savedEvent.getContent()
        );
    }
}
