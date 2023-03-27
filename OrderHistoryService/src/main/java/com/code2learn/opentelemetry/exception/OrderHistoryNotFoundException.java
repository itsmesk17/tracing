package com.code2learn.opentelemetry.exception;

public class OrderHistoryNotFoundException extends RuntimeException {
    public OrderHistoryNotFoundException(String msg) {
        super(msg);
    }
}
