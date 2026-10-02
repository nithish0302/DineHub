package com.dinehub.notificationService.controller;

import com.dinehub.notificationService.dto.NotificationResponse;
import com.dinehub.notificationService.entity.Notification;
import com.dinehub.notificationService.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping("/createNotification")
    public ResponseEntity<Notification> createNotification(@RequestBody Notification notification){

        return new ResponseEntity<>(
                notificationService.createNotification(notification),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/get/{notificationId}")
    public ResponseEntity<Notification> getNotification(@PathVariable Long notificationId){
        return ResponseEntity.ok(
                notificationService.getId(notificationId)
        );
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<NotificationResponse> getNotificationByUserId(@PathVariable Long userId){
        return ResponseEntity.ok(
                notificationService.getNotificationByUserId(userId)
        );
    }

}
