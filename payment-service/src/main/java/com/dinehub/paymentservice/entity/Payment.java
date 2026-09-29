package com.dinehub.paymentservice.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name="payment")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long paymentId;

    @NotNull(message = "User Id needed")
    @Column(nullable = false)
    private Long userId;

    @NotNull(message = "order Id needed")
    @Column(nullable = false)
    private Long orderId;


    @NotNull(message = "Amount is needed")
    @Positive(message = "Amount should be greater than zero ")
    @Column(nullable = false, precision = 10,scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Payment method is required")
    private PaymentMethod paymentMethod;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus=PaymentStatus.pending;


    private String transactionId;


    private LocalDateTime paidAt;

    @CreationTimestamp
    @Column(nullable = false,updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}
