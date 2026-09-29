package com.dinehub.paymentservice.services;

import com.dinehub.paymentservice.entity.Payment;
import com.dinehub.paymentservice.entity.PaymentStatus;

import java.util.Optional;

public interface PaymentService {

    //POST METHOD
    Payment createPayment(Payment payment);

    //GET METHOD
    Payment getPaymentById(Long paymentId);
    Payment getPaymentByOrderId(Long orderId);

    //PUT METHOD
    Payment updatePaymentStatus(Long orderId, PaymentStatus paymentStatus);

}
