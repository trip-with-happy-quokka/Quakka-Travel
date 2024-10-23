package com.sparta.quokkatravel.domain.accommodation.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.quokkatravel.domain.accommodation.entity.Accommodation;
import com.sparta.quokkatravel.domain.accommodation.entity.QAccommodation;
import com.sparta.quokkatravel.domain.user.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AccommodationRepositorySupport extends QuerydslRepositorySupport {

    private final JPAQueryFactory queryFactory;

    public AccommodationRepositorySupport(JPAQueryFactory queryFactory) {
        super(Accommodation.class);
        this.queryFactory = queryFactory;
    }



}
