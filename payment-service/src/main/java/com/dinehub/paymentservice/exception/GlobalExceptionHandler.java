package com.dinehub.paymentservice.exception;

import com.dinehub.paymentservice.entity.ErrorDetail;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler  {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>>handleMethodArgumentNotValidException(MethodArgumentNotValidException ex)
    {
        Map<String,String>error=new HashMap<>();
        for(FieldError fieldError:ex.getBindingResult().getFieldErrors())
        {
            error.put(fieldError.getField(),fieldError.getDefaultMessage());
        }
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<Map<String,String>>handleHandlerMethodValidationException(HandlerMethodValidationException ex)
    {
        Map<String,String>errors=new HashMap<>();
        for (var result:ex.getParameterValidationResults())
        {
            for(var error:result.getResolvableErrors()) {
                errors.put(
                        result.getMethodParameter().getParameterName(),
                        error.getDefaultMessage()
                );
            }
        }
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetail>handleAllException(Exception ex, WebRequest request)
    {
        ErrorDetail errorDetail=new ErrorDetail(LocalDateTime.now(), ex.getMessage() ,request.getDescription(false));
        return  ResponseEntity.internalServerError().body(errorDetail);
    }
    @ExceptionHandler(PaymentNotFoundException.class)
    public ResponseEntity<ErrorDetail>handlePaymentNotFound(PaymentNotFoundException ex,WebRequest request)
    {
        ErrorDetail errorDetail=new ErrorDetail(LocalDateTime.now(),ex.getMessage(),request.getDescription(false));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetail);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDetail>handleUserNotFoundException(UserNotFoundException ex,WebRequest request)
    {
        ErrorDetail errorDetail=new ErrorDetail(LocalDateTime.now(),ex.getMessage(),request.getDescription(false));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetail);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorDetail>handleOrderNotFound(OrderNotFoundException ex,WebRequest request)
    {
        ErrorDetail errorDetail=new ErrorDetail(LocalDateTime.now(),ex.getMessage(),request.getDescription(false));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetail);
    }

}
