package com.dinehub.notificationService.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name="notification")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId;

    @Column(name = "user_id")
    @NotNull(message = "User ID is required")
    private Long userId;

    @Column(name = "order_id")
    @NotNull(message = "Order ID is required")
    private Long orderId;

    @Column(name = "created_at")
    @PastOrPresent(message = "created time cannot be in the future")
    private LocalDateTime createdAt;

}
