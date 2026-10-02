package com.dinehub.notificationService.exception;

public class NotificationNotFoundException extends RuntimeException {

    public NotificationNotFoundException(String message){
        super(message);
    }
}
