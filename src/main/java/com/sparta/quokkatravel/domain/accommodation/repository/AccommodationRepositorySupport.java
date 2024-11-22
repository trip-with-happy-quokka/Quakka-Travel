package com.sparta.quokkatravel.domain.accommodation.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.quokkatravel.domain.accommodation.entity.Accommodation;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

@Repository
public class AccommodationRepositorySupport extends QuerydslRepositorySupport {

    private final JPAQueryFactory queryFactory;

    public AccommodationRepositorySupport(JPAQueryFactory queryFactory) {
        super(Accommodation.class);
        this.queryFactory = queryFactory;
    }
}
