package com.dinehub.notificationService.dto;

import com.dinehub.notificationService.entity.Notification;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class NotificationResponse {
    private String message;
    private List<Notification> notificationList;
}
