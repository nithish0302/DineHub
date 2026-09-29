package com.dinehub.paymentservice.controller;

import com.dinehub.paymentservice.entity.Payment;
import com.dinehub.paymentservice.entity.PaymentStatus;
import com.dinehub.paymentservice.services.PaymentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
@Validated
public class PaymentController {

    private final PaymentService paymentService;

    //POST METHOD
    @PostMapping("/create")
    ResponseEntity<Payment>createPayment(@Valid @RequestBody Payment payment){

        Payment response=paymentService.createPayment(payment);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    //GET METHOD
    @GetMapping("/getById")
    ResponseEntity<Payment>getPaymentById(@NotNull(message = "Payment Id is needed") @RequestParam Long paymentId)
    {
        Payment response=paymentService.getPaymentById(paymentId);
        return  ResponseEntity.ok(response);
    }
    @GetMapping("/getByOrderId/{orderId}")
    ResponseEntity<Payment>getPaymentByOrderId(@NotNull(message = "Order Id is needed") @PathVariable Long orderId)
    {
        Payment response=paymentService.getPaymentByOrderId(orderId);
        return  ResponseEntity.ok(response);
    }

    //PUT METHOD
    @PutMapping("/updateStatus/{orderId}")
    ResponseEntity<Payment>updatePaymentStatus(@NotNull(message = "Order Id is needed")@PathVariable Long orderId, @RequestParam PaymentStatus paymentStatus)
    {
        Payment response=paymentService.updatePaymentStatus(orderId,paymentStatus);
        return ResponseEntity.ok(response);
    }
}
