package com.dinehub.paymentservice.services.impl;

import com.dinehub.paymentservice.client.UserServiceClient;
import com.dinehub.paymentservice.entity.Payment;
import com.dinehub.paymentservice.entity.PaymentStatus;
import com.dinehub.paymentservice.repository.PaymentRepository;
import com.dinehub.paymentservice.services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

  private final  PaymentRepository paymentRepository;
  private final UserServiceClient userServiceClient;


    @Override
    public Payment createPayment(Payment payment) {

        Boolean userExists= userServiceClient.existByUserId(payment.getUserId());
        if(!userExists)
        {
            throw new RuntimeException("User Not Found");
        }
        Payment response =paymentRepository.save(payment);

        return response;
    }

    @Override
    public Payment getPaymentById(Long paymentId) {

            Optional<Payment> payment = paymentRepository.findById(paymentId);
            if(payment.isEmpty())
            {
                throw  new RuntimeException("Payment Id not found");
            }
            return  payment.get();

    }

    @Override
    public Payment getPaymentByOrderId(Long orderId) {

        Optional<Payment>payment=paymentRepository.findPaymentByOrderId(orderId);

        if(payment.isEmpty())
        {
            throw new RuntimeException("Order Id is not Found");
        }
        return payment.get();
    }

    @Override
    public Payment updatePaymentStatus(Long orderId, PaymentStatus paymentStatus) {

        Optional<Payment>payment=paymentRepository.findPaymentByOrderId(orderId);

        if(payment.isEmpty())
        {
            throw new RuntimeException("Order Id is not Found");
        }
      Payment actualPayment= payment.get();
        actualPayment.setPaymentStatus(paymentStatus);

        return paymentRepository.save(actualPayment);

    }
}
