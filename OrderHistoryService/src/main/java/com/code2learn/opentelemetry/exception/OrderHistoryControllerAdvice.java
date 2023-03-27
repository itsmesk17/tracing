package com.code2learn.opentelemetry.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class OrderHistoryControllerAdvice {

    @ExceptionHandler(OrderHistoryNotFoundException.class)
    public ResponseEntity<Object> handlePriceNotFoundException(OrderHistoryNotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

}
