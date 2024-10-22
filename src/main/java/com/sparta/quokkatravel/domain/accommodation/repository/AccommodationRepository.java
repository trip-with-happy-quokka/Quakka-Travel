package com.sparta.quokkatravel.domain.accommodation.repository;

import com.sparta.quokkatravel.domain.accommodation.entity.Accommodation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {
}
