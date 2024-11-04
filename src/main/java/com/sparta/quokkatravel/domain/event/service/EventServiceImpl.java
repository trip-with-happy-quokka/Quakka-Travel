package com.sparta.quokkatravel.domain.event.service;

import com.sparta.quokkatravel.domain.common.exception.NotFoundException;
import com.sparta.quokkatravel.domain.event.dto.request.EventRequestDto;
import com.sparta.quokkatravel.domain.event.dto.response.EventResponseDto;
import com.sparta.quokkatravel.domain.event.entity.Event;
import com.sparta.quokkatravel.domain.event.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    @Override
    @Transactional
    public EventResponseDto createEvent(String Email, EventRequestDto eventRequestDto) {

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

    @Override
    public List<EventResponseDto> getAllEvents(String email) {

        List<Event> eventList = eventRepository.findAll();

        return eventList.stream().map(event -> new EventResponseDto(
                event.getId(),
                event.getName(),
                event.getContent()
        )).toList();
    }

    @Override
    @Transactional
    public EventResponseDto updateEvent(String email, Long eventId, EventRequestDto eventRequestDto) {

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("해당 이벤트 조회 불가"));

        event.updateEvent(eventRequestDto);

        return new EventResponseDto(
                event.getId(),
                event.getName(),
                event.getContent()
        );
    }

    @Override
    @Transactional
    public String deleteEvent(String email, Long eventId) {

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("해당 이벤트 조회 불가"));

        eventRepository.delete(event);

        return "Event Deleted";
    }
}
