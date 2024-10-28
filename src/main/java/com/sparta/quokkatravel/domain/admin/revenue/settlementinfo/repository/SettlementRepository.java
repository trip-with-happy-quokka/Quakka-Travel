package com.sparta.quokkatravel.domain.admin.revenue.settlementinfo.repository;

import com.sparta.quokkatravel.domain.admin.revenue.settlementinfo.entity.SettlementInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface SettlementRepository extends JpaRepository<SettlementInfo, Long> {
    List<SettlementInfo> findBySettlementPeriodStartBetween(LocalDate startDate, LocalDate endDate);
}