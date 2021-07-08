package com.cms.controllers.global_exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
    

    // For Spesific Exceptions
    // @ExceptionHandler(BlablaException.class)
    // public ResponseEntity<?> handlerBlablaException(Exception e , WebRequest request){

    // }


    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<?> handleAuthenticationException(AuthenticationException exception,WebRequest request){
        ExceptionDetails details = new ExceptionDetails(LocalDateTime.now(), exception.getMessage(),request.getUserPrincipal().toString(), request.getDescription(false), HttpStatus.UNAUTHORIZED.toString());
        return new ResponseEntity<>(details,HttpStatus.UNAUTHORIZED);

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception exception,WebRequest request){
        ExceptionDetails details = new ExceptionDetails(LocalDateTime.now(), exception.getMessage(),request.getUserPrincipal().toString(), request.getDescription(false), HttpStatus.INTERNAL_SERVER_ERROR.toString());
        return new ResponseEntity<>(details,HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
}