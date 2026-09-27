package com.dinehub.user.exception;


import com.dinehub.user.entity.ErrorDetail;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler  {


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetail>handleAllException(Exception ex, WebRequest request)
    {
        ErrorDetail error=new ErrorDetail(LocalDateTime.now(),ex.getMessage(),request.getDescription(false));
        return ResponseEntity.internalServerError().body(error);
    }

    //Handle User not found exception
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDetail>handleUserNotFoundException(UserNotFoundException ex,WebRequest request)
    {
        ErrorDetail error=new ErrorDetail(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    //handle user already exist exception
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorDetail>handleUserAlreadyExistException(UserAlreadyExistsException ex,WebRequest request)
    {
        ErrorDetail error=new ErrorDetail(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }
}
