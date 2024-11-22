package com.sparta.quokkatravel.domain.accommodation.repository;

import com.sparta.quokkatravel.domain.accommodation.entity.Accommodation;
import com.sparta.quokkatravel.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com/sparta/quokkatravel/domain/accommodation/repository")
public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {

    Page<Accommodation> findByUser(User user, Pageable pageable);

}
