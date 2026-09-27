package com.dinehub.foodService.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "food")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long foodId;

    @NotBlank(message="Food name is required")
    @Size(max=100,message = "Food name must not exceed 100 characters")
    @Column(nullable = false)
    private String name;

    @Column(length = 1000)
    @Size(max=1000,message = "Description must not exceed 1000 characters")
    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than zero")
    private BigDecimal price;

    @Column(nullable = false)
    @NotNull(message = "Quantity is required")
    @PositiveOrZero(message = "Quantity cannot be negative")
    private Integer quantity;

    private String imageUrl;

    @Column(nullable = false)
    @NotNull(message = "Availability is required")
    private Boolean isAvailable;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist //runs before any insert
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        createdAt = now;
        updatedAt = now;

        if (isAvailable == null) {
            isAvailable = true;
        }
    }

    @PreUpdate //runs before update
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}