package com.sparta.quokkatravel.domain.admin.revenue.revenuedetail.controller;

import com.sparta.quokkatravel.domain.admin.revenue.revenuedetail.dto.RevenueDetailRequestDto;
import com.sparta.quokkatravel.domain.admin.revenue.revenuedetail.service.RevenueDetailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/revenues")
public class RevenueDetailController {

    private final RevenueDetailService revenueDetailService;

    public RevenueDetailController(RevenueDetailService revenueDetailService) {
        this.revenueDetailService = revenueDetailService;
    }

    // 새로운 수익 내역 생성
    @PostMapping
    public ResponseEntity<String> createRevenueDetail(@RequestBody RevenueDetailRequestDto requestDto) {
        revenueDetailService.createRevenueDetail(requestDto);
        return ResponseEntity.status(201).body("수익 내역이 성공적으로 생성되었습니다.");
    }

    // 특정 수익 내역 수정
    @PutMapping("/{id}")
    public ResponseEntity<String> updateRevenueDetail(@PathVariable Long id, @RequestBody RevenueDetailRequestDto requestDto) {
        revenueDetailService.updateRevenueDetail(id, requestDto);
        return ResponseEntity.ok("수익 내역이 성공적으로 수정되었습니다.");
    }

    // 특정 수익 내역 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRevenueDetail(@PathVariable Long id) {
        revenueDetailService.deleteRevenueDetail(id);
        return ResponseEntity.ok("수익 내역이 성공적으로 삭제되었습니다.");
    }
}