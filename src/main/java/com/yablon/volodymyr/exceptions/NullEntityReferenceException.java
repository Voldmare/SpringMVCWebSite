package com.yablon.volodymyr.exceptions;

public class NullEntityReferenceException extends RuntimeException {
    public NullEntityReferenceException(String message) {
        super(message);
    }

    public NullEntityReferenceException() {

    }
}
