package com.dinehub.user.exception;


import com.dinehub.user.dto.ErrorDetail;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class    GlobalExceptionHandler  {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>>handleValidException(MethodArgumentNotValidException ex)
    {
        Map<String,String>errors=new HashMap<>();
        for(FieldError error:ex.getBindingResult().getFieldErrors()){
            errors.put(error.getField(),error.getDefaultMessage());
        }
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<Map<String,String>>handleHandlerMethodValidationException(HandlerMethodValidationException ex)
    {
        Map<String,String>error=new HashMap<>();
        for(var response:ex.getParameterValidationResults())
        {
            for(var err:response.getResolvableErrors())
            {
                error.put(response.getMethodParameter().getParameterName(),err.getDefaultMessage());
            }
        }
            return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetail>handleAllException(Exception ex, WebRequest request)
    {
        ErrorDetail error=new ErrorDetail(LocalDateTime.now(),ex.getMessage(),request.getDescription(false));
        return ResponseEntity.internalServerError().body(error);
    }
    @ExceptionHandler(InvalidRefreshTokenException.class)
    public ResponseEntity<ErrorDetail> handleInvalidRefreshToken(
            InvalidRefreshTokenException ex,
            WebRequest request) {

        ErrorDetail error = new ErrorDetail(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false)
        );

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(error);
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
