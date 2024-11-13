package com.sparta.quokkatravel.domain.admin.revenue.settlementinfo.controller;

import com.sparta.quokkatravel.domain.admin.revenue.settlementinfo.dto.SettlementRequestDto;
import com.sparta.quokkatravel.domain.admin.revenue.settlementinfo.dto.SettlementResponseDto;
import com.sparta.quokkatravel.domain.admin.revenue.settlementinfo.service.SettlementService;
import com.sparta.quokkatravel.domain.common.shared.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/settlements")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
@Tag(name = "AdminSettlement", description = "Admin 정산 관련 컨트롤러")
public class SettlementController {

    private final SettlementService settlementService;

    // 날짜별 정산 목록 조회 (시작일과 종료일 필터링)
    @GetMapping
    public List<SettlementResponseDto> getSettlementsByDateRange(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        return settlementService.getSettlementsByDateRange(startDate, endDate);
    }

    // 특정 정산 ID로 정산 정보 조회
    @GetMapping("/{id}")
    public SettlementResponseDto getSettlementById(@PathVariable Long id) {
        return settlementService.getSettlementById(id);
    }

    // 새로운 정산 정보 생성
    @PostMapping
    public ResponseEntity<String> createSettlement(@RequestBody SettlementRequestDto requestDto) {
        settlementService.createSettlement(requestDto);
        return ResponseEntity.status(201).body("정산 정보가 성공적으로 생성되었습니다.");
    }

    // 특정 정산 정보 수정
    @PutMapping("/{id}")
    public ResponseEntity<String> updateSettlement(@PathVariable Long id, @RequestBody SettlementRequestDto requestDto) {
        settlementService.updateSettlement(id, requestDto);
        return ResponseEntity.ok("정산 정보가 성공적으로 수정되었습니다.");
    }

    // 특정 정산 정보 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSettlement(@PathVariable Long id) {
        settlementService.deleteSettlement(id);
        return ResponseEntity.ok("정산 정보가 성공적으로 삭제되었습니다.");
    }

    // 월별 통계 조회 (예시: 2023-10) - YearMonth
    @GetMapping("/monthly-statistics")
    public ResponseEntity<?> getMonthlyStatistics(@RequestParam String yearMonth) {

        YearMonth ym = YearMonth.parse(yearMonth);  // YearMonth 형식으로 변환
        List<SettlementResponseDto> settlements = settlementService.getMonthlyStatistics(ym);

        return ResponseEntity.ok(ApiResponse.success("월별통계 조회 성공", settlements));
    }
}