package com.sparta.quokkatravel.domain.reservation.entity;

public enum ReservationStatus {
    PENDING, // 예약 대기 (결제 진행 전 임시상태)
    CONFIRMED, // 예약 확정 (결제 완료)
    REJECTED, // 예약 거절
    CANCELED, // 예약 취소
    COMPLETED, // 이용 완료
    NO_SHOW, // 당일인데 방문 안함(예약은 한 상태)
    REFUNDED // 환불 완료 상태
}
