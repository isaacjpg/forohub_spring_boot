package com.forohub.infra.errores;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExeptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> userAlreadyExists(UserAlreadyExistsException e){
        return ResponseEntity.badRequest().body(new ExceptionResponse(e.getMessage(), 400, System.currentTimeMillis()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionResponse> runtimeException(RuntimeException e){
        return ResponseEntity.badRequest().body(new ExceptionResponse(e.getMessage(), 400, System.currentTimeMillis()));
    }


}
