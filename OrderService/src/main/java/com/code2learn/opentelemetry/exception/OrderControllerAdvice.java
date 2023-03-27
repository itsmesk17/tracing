package com.code2learn.opentelemetry.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;


@RestControllerAdvice
public class OrderControllerAdvice {

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<Object> handleProductNotFoundException(OrderNotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpServerErrorException.ServiceUnavailable.class)
    public ResponseEntity<Object> handleException(HttpServerErrorException.ServiceUnavailable exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
