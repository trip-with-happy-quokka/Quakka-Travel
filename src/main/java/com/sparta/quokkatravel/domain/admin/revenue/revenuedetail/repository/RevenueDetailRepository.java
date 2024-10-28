package com.sparta.quokkatravel.domain.admin.revenue.revenuedetail.repository;

import com.sparta.quokkatravel.domain.admin.revenue.revenuedetail.entity.RevenueDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RevenueDetailRepository extends JpaRepository<RevenueDetail, Long> {
    List<RevenueDetail> findBySettlementInfoSettlementId(Long settlementId);
}