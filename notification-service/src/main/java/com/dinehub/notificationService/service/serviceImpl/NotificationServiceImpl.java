package com.dinehub.notificationService.service.serviceImpl;

import com.dinehub.notificationService.dto.NotificationResponse;
import com.dinehub.notificationService.entity.Notification;
import com.dinehub.notificationService.exception.NotificationNotFoundException;
import com.dinehub.notificationService.repository.NotificationRepository;
import com.dinehub.notificationService.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Override
    public Notification createNotification(Notification notification) {
        return notificationRepository.save(notification);
    }

    @Override
    public Notification getId(Long notificationId) {
        return notificationRepository.findById(notificationId)
                .orElseThrow(()->
                        new NotificationNotFoundException("Notification not found with id : "+notificationId)
                );

    }

    @Override
    public NotificationResponse getNotificationByUserId(Long userId) {
        List<Notification> notifications = notificationRepository.findByUserIdOrderByCreatedAtDesc(userId);
        if(notifications.isEmpty()){
            return new NotificationResponse(
              "No notifications found",
                    notifications
            );
        }
        return new NotificationResponse(
          "Notifications found",
                notifications
        );
    }

}
