package com.dinehub.paymentservice.repository;

import com.dinehub.paymentservice.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long> {


    Optional<Payment> findPaymentByOrderId(Long orderId);
}
