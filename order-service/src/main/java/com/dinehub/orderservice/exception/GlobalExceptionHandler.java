package com.dinehub.orderservice.exception;

import com.dinehub.orderservice.dto.ErrorDetail;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(FoodNotAvailableException.class)
    public ResponseEntity<String> handleFoodNotFound(
            FoodNotAvailableException exception) {

        return new ResponseEntity<>(
                exception.getMessage(),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<String> handleFoodNotFound(
            OrderNotFoundException exception) {

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
