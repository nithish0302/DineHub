package com.dinehub.notificationService.exception;

import com.dinehub.notificationService.dto.ErrorDetail;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotificationNotFoundException.class)
    public ResponseEntity<String> notificationNotFoundException(Exception exception){
        return new ResponseEntity<>(
                exception.getMessage(),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetail>handleAllException(Exception ex, WebRequest request)
    {
        ErrorDetail error=new ErrorDetail(LocalDateTime.now(),ex.getMessage(),request.getDescription(false));
        return ResponseEntity.internalServerError().body(error);
    }

}
