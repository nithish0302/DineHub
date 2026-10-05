package com.dinehub.orderservice.dto;

import com.dinehub.orderservice.enums.NotificationChannel;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {

    private Long userId;

    private Long orderId;

    private NotificationChannel notificationChannel;

    private String title;
}
