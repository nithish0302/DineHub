package com.dinehub.notificationService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ErrorDetail {
    private LocalDateTime timeStamp;
    private String message;
    private String Description;
}
