package com.dinehub.notificationService.service;


import com.dinehub.notificationService.dto.NotificationResponse;
import com.dinehub.notificationService.entity.Notification;

import java.util.List;

public interface NotificationService {

    public Notification createNotification(Notification notification);

    public Notification getId(Long notificationId);

    public NotificationResponse getNotificationByUserId(Long userId);
}
