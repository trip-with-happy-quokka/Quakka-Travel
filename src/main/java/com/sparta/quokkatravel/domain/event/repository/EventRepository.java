package com.sparta.quokkatravel.domain.event.repository;

import com.sparta.quokkatravel.domain.event.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
