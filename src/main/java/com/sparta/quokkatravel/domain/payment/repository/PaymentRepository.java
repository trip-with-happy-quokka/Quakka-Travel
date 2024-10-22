package com.sparta.quokkatravel.domain.payment.repository;

import com.sparta.quokkatravel.domain.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
