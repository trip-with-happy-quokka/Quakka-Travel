package com.sparta.quokkatravel.domain.room.repository;

import aj.org.objectweb.asm.commons.Remapper;
import com.sparta.quokkatravel.domain.accommodation.entity.Accommodation;
import com.sparta.quokkatravel.domain.room.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
    Page<Room> findByAccommodation(Accommodation accommodation, Pageable pageable);
}
