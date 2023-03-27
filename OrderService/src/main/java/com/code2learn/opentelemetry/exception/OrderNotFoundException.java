package com.code2learn.opentelemetry.exception;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(String orderNotFound) {
        super(orderNotFound);
    }
}
