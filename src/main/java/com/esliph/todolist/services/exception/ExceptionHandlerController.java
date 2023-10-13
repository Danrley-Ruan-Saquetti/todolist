package com.esliph.todolist.services.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleHTTPMessageNotReadableException(HttpMessageNotReadableException err) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err.getMostSpecificCause().getMessage());
    }
}