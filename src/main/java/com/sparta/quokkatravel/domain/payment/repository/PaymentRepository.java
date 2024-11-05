package com.sparta.quokkatravel.domain.payment.repository;

import com.sparta.quokkatravel.domain.payment.entity.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByOrderId(String orderId);
    Page<Payment> findAllByUser_Email(String email, Pageable pageable);
    Optional<Payment> findByPaymentKey(String paymentKey);
}
