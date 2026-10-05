package com.dinehub.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class ErrorDetail {
    private LocalDateTime timeStamp;
    private String message;
    private String Description;
}