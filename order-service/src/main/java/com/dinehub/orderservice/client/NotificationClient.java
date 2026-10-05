package com.dinehub.orderservice.client;

import com.dinehub.orderservice.dto.Notification;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "notification-service")
public interface NotificationClient {
    @PostMapping("api/notifications/createNotification")
    public ResponseEntity<Notification> createNotification(Notification notification);
}
